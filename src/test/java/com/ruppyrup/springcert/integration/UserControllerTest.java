package com.ruppyrup.springcert.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ruppyrup.springcert.jwt.JwtTokenUtil;
import com.ruppyrup.springcert.jwt.JwtUserDetailsService;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/test-schema-mysql.sql"})
@ActiveProfiles("integration")
class UserControllerTest {

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

    private HttpHeaders headers;
    private UserDTO user = new UserDTO();
    private ObjectMapper mapper = new ObjectMapper();


    //todo need to add error paths

    @BeforeEach
    void getToken() {
        user.setPassword(password);
        user.setUsername(username);
//        userDetailsService.save(user);
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        token = jwtTokenUtil.generateToken(userDetails);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers = new HttpHeaders();
//        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        //jwtContextManager.setUser(username);
    }

    @AfterEach
    void deleteUser() {
        userDetailsService.deleteUser(userDetailsService.getUser(username));
    }

    @Test
    void registerUser() throws JsonProcessingException {
        //given database is empty
        String userJson = mapper.writeValueAsString(user);
        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);

        //when
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:" + port + "/register", HttpMethod.POST, entity, String.class);
        String response = JsonPath.parse(exchange.getBody()).read("$.username");

        //then
        assertThat(response).isEqualTo(username);
    }

    @Test
    void authenticateUser() throws JsonProcessingException {
        //given
        userDetailsService.save(user);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        String userJson = mapper.writeValueAsString(user);
        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);

        //when
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:" + port + "/authenticate", HttpMethod.POST, entity, String.class);
        String responseToken = JsonPath.parse(exchange.getBody()).read("$.token");

        //then
        assertThat(responseToken).isEqualTo(token);
    }

    @Test
    void registerSameUserTwice_shouldReturnError() throws JsonProcessingException {
        //given database is empty
        userDetailsService.save(user);
        String userJson = mapper.writeValueAsString(user);
        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);

        //when
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:" + port + "/register", HttpMethod.POST, entity, String.class);
        String response = JsonPath.parse(exchange.getBody()).read("$.username");

        //then
        assertThat(response).isEqualTo(username);
    }
}