package com.ruppyrup.springcert.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.exceptions.ExistingUserException;
import com.ruppyrup.springcert.jwt.JwtTokenUtil;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.model.UserDTO;
import com.ruppyrup.springcert.service.impl.JwtUserDetailsService;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-integration.properties")
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

    @Autowired
    CredentialDao credentialDao;

    @Value("${jwt.username}")
    private String username;

    @Value("${jwt.pwd}")
    private String password;

    private String token;

    private HttpHeaders headers;

    private Credential amazonUser1;
    private Credential pondUser1;
    private Credential jlUser1;
    private Credential tt;

    @BeforeEach
    void getToken() throws ExistingUserException {
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
        credentialDao.findAll().forEach(System.out::println);
        amazonUser1 = new Credential(2L, "b","Amazon", "www.amazon.com", "pete", "football", username);
        pondUser1 = new Credential(1L, "a","PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", username);
        jlUser1 = new Credential(3L, "c","John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", username);
        tt = new Credential(4L, "d","Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly", username);
    }

    @AfterEach
    void deleteUser() {
        userDetailsService.deleteUser(userDetailsService.getUser(username));
        credentialDao.deleteAll();
    }

    @Test
    void getCredential() {
        //given database is loaded and jwt token fetched
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        //when
        ResponseEntity<Credential> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials/a", HttpMethod.GET, entity, Credential.class);

        //then
        assertThat(exchange.getBody().getCredentialName()).isEqualTo("PondPlanet");
    }

    @Test
    void getAllCredentials() {
        //given database is loaded
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        //when
        ResponseEntity<Credential[]> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials", HttpMethod.GET, entity, Credential[].class);
        //List<String> CredentialName = JsonPath.parse(exchange.getBody()).read("$[*].CredentialName");

        //then
        assertThat(exchange.getBody()).containsExactlyInAnyOrder(amazonUser1, pondUser1, jlUser1);
    }

    @Test
    void createCredential() throws JsonProcessingException {
        //given database is loaded
        Credential credential = new Credential(5L, "e","Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly", "javainuse");
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
        ResponseEntity<Credential> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials/b", HttpMethod.PUT, entity, Credential.class);

        //then
        Credential body = exchange.getBody();
        assertThat(body.getUrl()).isEqualTo("amazon.co.uk");
    }

    @Test
    void deleteCredential() {
        //given database is loaded
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        //when
        ResponseEntity<Credential> exchange = restTemplate.exchange("http://localhost:" + port + "/credentials/c", HttpMethod.DELETE, entity, Credential.class);

        //then
        assertThat(exchange.getBody()).isEqualTo(jlUser1);
    }
}