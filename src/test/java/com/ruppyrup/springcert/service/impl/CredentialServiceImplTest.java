package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CredentialServiceImplTest {

    private Credential credential1;
    private Credential credential2;
    private Credential credential3;

    @Autowired
    CredentialService credentialService;

    @BeforeEach
    void setUp() {
        credential1 = new Credential("Amazon", "www.amazon.com", "pete", "football");
        credential2 = new Credential("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick");
        credential3 = new Credential("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly");
    }


    @Test
    void getAllCredentials() {
        assertThat(credentialService.getAllCredentials()).hasSize(3);
    }

    @Test
    void getCredential() {
        assertThat(credentialService.getCredential("Amazon")).isEqualToComparingFieldByField(credential1);
    }
}