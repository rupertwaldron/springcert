package com.ruppyrup.springcert.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ruppyrup.springcert.exceptions.ExistingUserException;
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

    @BeforeEach
    void getToken() {
        user.setUsername(username);
        user.setPassword(password);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
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

        //then
        assertThat(exchange.getBody()).isEqualTo("User created");
    }

    @Test
    void authenticateUser() throws JsonProcessingException, ExistingUserException, InterruptedException {
        //given
        userDetailsService.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String userJson = mapper.writeValueAsString(user);
        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);

        //when
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:" + port + "/authenticate", HttpMethod.POST, entity, String.class);
        String jwtToken = JsonPath.parse(exchange.getBody()).read("$.token");

        //then
        assertThat(jwtTokenUtil.validateToken(jwtToken, userDetails)).isTrue();
    }

    @Test
    void registerSameUserTwice_shouldReturnError() throws Exception {
        //given database is empty
        userDetailsService.save(user);
        String userJson = mapper.writeValueAsString(user);
        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);

        //when
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:" + port + "/register", HttpMethod.POST, entity, String.class);

        //then
        assertThat(exchange.getBody()).isEqualTo("User already exists");
    }
}