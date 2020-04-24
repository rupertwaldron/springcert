package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql({"/test-schema-mysql.sql"})
@ActiveProfiles("test")
class CredentialServiceImplTest {

    private Credential credential1;
    private Credential credential2;
    private Credential credential3;
    private Credential credential4;

    @Autowired
    CredentialService credentialService;

    @BeforeEach
    void setUp() {
        credential1 = new Credential("Amazon", "www.amazon.com", "pete", "football");
        credential2 = new Credential("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick");
        credential3 = new Credential("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly");
        credential4 = new Credential("Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly");
    }

    @Test
    void getAllCredentials() {
        assertThat(credentialService.getAllCredentials()).containsExactlyInAnyOrder(credential3, credential2, credential1);
    }

    @Test
    void getCredential() {
        assertThat(credentialService.getCredential("Amazon")).containsExactly(credential1);
    }

    @Test
    void createCredential() {
        //given
        Credential isCreated = credentialService.createCredential(credential4);

        //then
        assertThat(credentialService.getCredential("Tops tiles")).containsExactly(credential4);
    }
}