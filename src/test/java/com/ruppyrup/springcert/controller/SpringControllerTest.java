package com.ruppyrup.springcert.controller;

import com.jayway.jsonpath.JsonPath;
import com.ruppyrup.springcert.model.Credential;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String credentialId = JsonPath.parse(result).read("$.credentialId");

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

    @Test
    void createCredential() {
        //given database is loaded
        Credential credential = new Credential("Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly");

        //when
        ResponseEntity<Credential> credentialResponse = restTemplate.postForEntity("http://localhost:" + port + "/credentials", credential, Credential.class);
        Credential body = credentialResponse.getBody();

        //then
        assertThat(body).isEqualTo(credential);
    }

    @Test
    void updateCredential() {
        //given database is loaded
        Credential credential = new Credential("Amazon", "amazon.co.uk", "rupert.waldron@ruppyrup.com", "bigjohn");
        Map<String, String> params = new HashMap<>();
        params.put("credentialId", "Amazon");

        //when
        restTemplate.put("http://localhost:" + port + "/credentials/Amazon", credential, params);

        //then
        String result = restTemplate.getForObject("http://localhost:" + port + "/credentials/Amazon", String.class);
        String url = JsonPath.parse(result).read("$.url");
        assertThat(url).isEqualTo("amazon.co.uk");
    }

    @Test
    void deleteCredential() {
        //given database is loaded

        //when
        restTemplate.delete("http://localhost:" + port + "/credentials/Amazon");

        //then
        String result = restTemplate.getForObject("http://localhost:" + port + "/credentials/Amazon", String.class);
        assertThat(result).isNull();
    }
}