package com.ruppyrup.springcert.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/test-schema-mysql.sql"})
@ActiveProfiles("test")
class SpringControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getCredentials() {
        //given database is loaded

        //when
        String result = restTemplate.getForObject("http://localhost:" + port + "/credentials/Amazon", String.class);
        String credentialId = JsonPath.parse(result).read("$[0].credentialId");

        //then
        assertThat(credentialId).isEqualTo("Amazon");
    }

    @Test
    void getAllCredentials() {
        //given database is loaded

        //when
        String result = restTemplate.getForObject("http://localhost:" + port + "/credentials", String.class);
        List<String> credentialId = JsonPath.parse(result).read("$[*].credentialId");

        //then
        assertThat(credentialId).contains("Amazon", "PondPlanet", "John Lewis");
    }
}