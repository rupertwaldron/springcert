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
    private Credential credential7;
    private Credential credential8;
    private Credential credential9;
    private Credential credential10;
    private String user1;
    private String user2;

    @Autowired
    CredentialService credentialService;

    @BeforeEach
    void setUp() {
        user1 = "javainuse";
        user2 = "ruppyrup";
        credential1 = new Credential("Amazon", "www.amazon.com", "pete", "football", user1);
        credential2 = new Credential("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", user1);
        credential3 = new Credential("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", user1);
        credential4 = new Credential("Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly", user1);
        credential5 = new Credential("PondPlanet", "www.pondplanet2.com", "Lee", "monster", user1);
        credential6 = new Credential("Pratts Pods", "www.pp.com", "Simon", "gobsmack", user1);
        credential7 = new Credential("Amazon", "www.amazon.com", "rupert", "sweetpea", user2);
        credential8 = new Credential("John Lewis", "www.johnlewis.com", "ruppyruyp@yahoo.co.uk", "deadsea", user2);
        credential9 = new Credential("John Lewis2", "www.johnlewis2.com", "ruppyruyp2@yahoo.co.uk", "deadsea2", user2);
        credential10 = new Credential("John Lewis", "www.HouseofFraser.com", "ruppyruyp@yahoo.co.uk", "deadsea", user2);
    }

    @Test
    void getAllCredentialsforUser() {
        assertThat(credentialService.getAllCredentials(user1)).containsExactlyInAnyOrder(credential3, credential2, credential1);
        assertThat(credentialService.getAllCredentials(user2)).containsExactlyInAnyOrder(credential7, credential8);
    }

    @Test
    void getCredential() {
        assertThat(credentialService.getCredential("Amazon", user1)).isEqualTo(credential1);
        assertThat(credentialService.getCredential("Amazon", user2)).isEqualTo(credential7);
    }

    @Test
    void createCredential() {
        //when
        credentialService.createCredential(credential4);
        credentialService.createCredential(credential9);

        //then
        assertThat(credentialService.getCredential(credential4.getCredentialId(), credential4.getUser())).isEqualTo(credential4);
        assertThat(credentialService.getCredential(credential9.getCredentialId(), credential9.getUser())).isEqualTo(credential9);
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
        credentialService.updateCredential(credential10);

        //then
        assertThat(credentialService.getCredential(credential5.getCredentialId(), credential5.getUser())).isEqualTo(credential5);
        assertThat(credentialService.getCredential(credential10.getCredentialId(), credential10.getUser())).isEqualTo(credential10);
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
        Credential credential = credentialService.deleteCredential(credential3.getCredentialId(), credential3.getUser());

        //then
        assertThat(credential).isEqualTo(credential3);
        assertThat(credentialService.getCredential(credential3.getCredentialId(), user1)).isNull();
    }
}