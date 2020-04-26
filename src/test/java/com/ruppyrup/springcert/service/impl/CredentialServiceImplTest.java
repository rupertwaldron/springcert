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
    private Credential credential5;
    private Credential credential6;

    @Autowired
    CredentialService credentialService;

    @BeforeEach
    void setUp() {
        credential1 = new Credential("Amazon", "www.amazon.com", "pete", "football");
        credential2 = new Credential("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick");
        credential3 = new Credential("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly");
        credential4 = new Credential("Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly");
        credential5 = new Credential("PondPlanet", "www.pondplanet2.com", "Lee", "monster");
        credential6 = new Credential("Pratts Pods", "www.pp.com", "Simon", "gobsmack");
    }

    @Test
    void getAllCredentials() {
        assertThat(credentialService.getAllCredentials()).containsExactlyInAnyOrder(credential3, credential2, credential1);
    }

    @Test
    void getCredential() {
        assertThat(credentialService.getCredential("Amazon")).isEqualTo(credential1);
    }

    @Test
    void createCredential() {
        //when
        credentialService.createCredential(credential4);

        //then
        assertThat(credentialService.getCredential("Tops tiles")).isEqualTo(credential4);
    }

    @Test
    void failureToCreateDuplicateCredentialId() {
        //when
        Credential credential = credentialService.createCredential(credential5);
        Credential rejectedCredential = new Credential(credential5.getCredentialId() + " Already exists");
        //then
        assertThat(credential).isEqualTo(rejectedCredential);
    }


    @Test
    void updateCredential() {
        //when
        credentialService.updateCredential(credential5);

        //then
        assertThat(credentialService.getCredential(credential5.getCredentialId())).isEqualTo(credential5);
    }

    @Test
    void updateNonExistingCredential() {
        //when
        Credential credential = credentialService.updateCredential(credential6);
        Credential rejectedCredential = new Credential(credential6.getCredentialId() + " Does not exist");

        //then
        assertThat(credential).isEqualTo(rejectedCredential);
    }

    @Test
    void deleteCredential() {
        //when
        Credential credential = credentialService.deleteCredential(credential3.getCredentialId());

        //then
        assertThat(credential).isEqualTo(credential3);
        assertThat(credentialService.getCredential(credential3.getCredentialId())).isNull();
    }
}