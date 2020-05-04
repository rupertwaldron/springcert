package com.ruppyrup.springcert.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ruppyrup.springcert.jwt.JwtTokenUtil;
import com.ruppyrup.springcert.jwt.JwtUserDetailsService;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.model.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/test-schema-mysql.sql"})
@ActiveProfiles("integration")
class SpringControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Value("${jwt.username}")
    private String username;

    @Value("${jwt.pwd}")
    private String password;

    private String token;

    private HttpHeaders headers;

    //todo need to add error paths

    @BeforeEach
    void getToken() {
        UserDTO user = new UserDTO();
        user.setPassword(password);
        user.setUsername(username);
        userDetailsService.save(user);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        token = jwtTokenUtil.generateToken(userDetails);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        //jwtContextManager.setUser(username);
    }

    @AfterEach
    void deleteUser() {
        userDetailsService.deleteUser(userDetailsService.getUser(username));
    }

    @Test
    void getCredential() {
        //given database is loaded and jwt token fetched
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        //when
        ResponseEntity<Credential> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials/Amazon", HttpMethod.GET, entity, Credential.class);

        //then
        assertThat(exchange.getBody().getCredentialId()).isEqualTo("Amazon");
    }

    @Test
    void getAllCredentials() {
        //given database is loaded
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        //when
        ResponseEntity<List> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials", HttpMethod.GET, entity, List.class);
        List<String> credentialId = JsonPath.parse(exchange.getBody()).read("$[*].credentialId");

        //then
        assertThat(credentialId).contains("Amazon", "PondPlanet", "John Lewis");
    }

    @Test
    void createCredential() throws JsonProcessingException {
        //given database is loaded
        Credential credential = new Credential("Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly", "javainuse");
        ObjectMapper mapper = new ObjectMapper();
        String credentialJson = mapper.writeValueAsString(credential);
        HttpEntity<String> entity = new HttpEntity<>(credentialJson, headers);

        //when
        ResponseEntity<Credential> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials", HttpMethod.POST, entity, Credential.class);
        Credential body = exchange.getBody();

        //then
        assertThat(body).isEqualTo(credential);
    }

    @Test
    void updateCredential() throws JsonProcessingException {
        //given database is loaded
        Credential credential = new Credential("Amazon", "amazon.co.uk", "rupert.waldron@ruppyrup.com", "bigjohn", "javainuse");
        ObjectMapper mapper = new ObjectMapper();
        String credentialJson = mapper.writeValueAsString(credential);
        HttpEntity<String> entity = new HttpEntity<>(credentialJson, headers);

        //when
        ResponseEntity<Credential> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials/Amazon", HttpMethod.PUT, entity, Credential.class);

        //then
        Credential body = exchange.getBody();
        assertThat(body.getUrl()).isEqualTo("amazon.co.uk");
    }

    @Test
    void deleteCredential() {
        //given database is loaded
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        //when
        ResponseEntity<Credential> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials/Amazon", HttpMethod.DELETE, entity, Credential.class);

        //then
        Credential body = exchange.getBody();
        assertThat(body.getCredentialId()).isEqualTo("Amazon");
    }
}