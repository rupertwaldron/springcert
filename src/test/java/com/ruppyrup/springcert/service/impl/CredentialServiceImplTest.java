package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.config.JwtContextManagerTestImpl;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
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

    @Autowired
    JwtContextManagerTestImpl jwtContextManager;

    @BeforeEach
    void setUp() {
        user1 = "javainuse";
        user2 = "ruppyrup";
        credential1 = new Credential("Amazon", "www.amazon.com", "pete", "football", user1);
        credential2 = new Credential("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", user1);
        credential3 = new Credential("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", user1);
        credential4 = new Credential("Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly", "");
        credential5 = new Credential("PondPlanet", "www.pondplanet2.com", "Lee", "monster", "");
        credential6 = new Credential("Pratts Pods", "www.pp.com", "Simon", "gobsmack", user1);
        credential7 = new Credential("Amazon", "www.amazon.com", "rupert", "sweetpea", user2);
        credential8 = new Credential("John Lewis", "www.johnlewis.com", "ruppyruyp@yahoo.co.uk", "deadsea", user2);
        credential9 = new Credential("John Lewis2", "www.johnlewis2.com", "ruppyruyp2@yahoo.co.uk", "deadsea2", "");
        credential10 = new Credential("John Lewis", "www.HouseofFraser.com", "ruppyruyp@yahoo.co.uk", "deadsea", "");
    }

    @Test
    void getAllCredentials() {
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getAllCredentials()).containsExactlyInAnyOrder(credential3, credential2, credential1);

        jwtContextManager.setUser(user2);
        assertThat(credentialService.getAllCredentials()).containsExactlyInAnyOrder(credential7, credential8);
    }

    @Test
    void getCredential() {
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential("Amazon")).isEqualTo(credential1);

        jwtContextManager.setUser(user2);
        assertThat(credentialService.getCredential("Amazon")).isEqualTo(credential7);
    }

    @Test
    void createCredential() {
        //when
        jwtContextManager.setUser(user1);
        credentialService.createCredential(credential4);
        credential4.setUser(jwtContextManager.getAuthorizedUser());

        jwtContextManager.setUser(user2);
        credentialService.createCredential(credential9);
        credential9.setUser(jwtContextManager.getAuthorizedUser());

        //then
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(credential4.getCredentialName())).isEqualTo(credential4);
        jwtContextManager.setUser(user2);
        assertThat(credentialService.getCredential(credential9.getCredentialName())).isEqualTo(credential9);
    }

    @Test
    void failureToCreateDuplicateCredentialName() {
        //when
        jwtContextManager.setUser(user1);
        Credential credential = credentialService.createCredential(credential5);
        //then
        assertThat(credential).isNull();
    }


    @Test
    void updateCredential() {
        //when
        jwtContextManager.setUser(user1);
        credentialService.updateCredential(credential5);
        credential5.setUser(jwtContextManager.getAuthorizedUser());

        jwtContextManager.setUser(user2);
        credentialService.updateCredential(credential10);
        credential10.setUser(jwtContextManager.getAuthorizedUser());

        //then
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(credential5.getCredentialName())).isEqualTo(credential5);
        jwtContextManager.setUser(user2);
        assertThat(credentialService.getCredential(credential10.getCredentialName())).isEqualTo(credential10);
    }

    @Test
    void updateNonExistingCredential() {
        //when
        jwtContextManager.setUser(user1);
        Credential credential = credentialService.updateCredential(credential6);

        //then
        assertThat(credential).isNull();
    }

    @Test
    void deleteCredential() {
        //when
        jwtContextManager.setUser(user1);
        Credential credential = credentialService.deleteCredential(credential3.getCredentialName());

        //then
        assertThat(credential).isEqualTo(credential3);
        assertThat(credentialService.getCredential(credential3.getCredentialName())).isNull();
    }
}