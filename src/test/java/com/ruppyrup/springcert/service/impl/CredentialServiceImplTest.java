package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.config.JwtContextManagerTestImpl;
import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.exceptions.RequestMadeByNonOwner;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext
@SpringBootTest
//@Sql({"/test-schema-mysql.sql"})
@ActiveProfiles("test")
class CredentialServiceImplTest {

    private String user1 = "javainuse";
    private String user2 = "ruppyrup";
    private String user3 = "bob";
    private Credential credential1 = new Credential(1L, "Amazon", "www.amazon.com", "pete", "football", user1);
    private Credential credential2 = new Credential(2L,"PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", user1);
    private Credential credential3 = new Credential(3L, "John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", user1);
    private Credential credential4 = new Credential(6L, "Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly", "");
    private Credential credential5 = new Credential(1L, "PondPlanet", "www.pondplanet2.com", "Lee", "monster", user1);
    private Credential credential6 = new Credential(8L, "Pratts Pods", "www.pp.com", "Simon", "gobsmack", user1);
    private Credential credential7 = new Credential(4L, "Amazon", "www.amazon.com", "rupert", "sweetpea", user2);
    private Credential credential8 = new Credential(5L,"John Lewis", "www.johnlewis.com", "ruppyruyp@yahoo.co.uk", "deadsea", user2);
    private Credential credential9 = new Credential(7L, "John Lewis2", "www.johnlewis2.com", "ruppyruyp2@yahoo.co.uk", "deadsea2", "");
    private Credential credential10 = new Credential(3L, "John Lewis", "www.HouseofFraser.com", "ruppyruyp@yahoo.co.uk", "deadsea", user1);

    @Autowired
    CredentialService credentialService;

    @Autowired
    JwtContextManagerTestImpl jwtContextManager;

    @Autowired
    CredentialDao credentialDao;

    @BeforeEach
    void setUp() {
        credentialDao.save(credential1);
        credentialDao.save(credential2);
        credentialDao.save(credential3);
        credentialDao.save(credential7);
        credentialDao.save(credential8);
        credentialDao.findAll().forEach(System.out::println);
    }

    @Test
    void getAllCredentials() {
        jwtContextManager.setUser(user1);
        List<String> credentialNames1 = credentialService.getAllCredentials().stream().map(Credential::getCredentialName).collect(Collectors.toList());
        assertThat(credentialNames1).containsExactlyInAnyOrder(credential3.getCredentialName(), credential2.getCredentialName(), credential1.getCredentialName());

        jwtContextManager.setUser(user2);
        List<String> credentialNames2 = credentialService.getAllCredentials().stream().map(Credential::getCredentialName).collect(Collectors.toList());
        assertThat(credentialNames2).containsExactlyInAnyOrder(credential7.getCredentialName(), credential8.getCredentialName());
    }

    @Test
    void getCredential() throws Exception {
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(credential1.getId())).isEqualTo(credential1);

        jwtContextManager.setUser(user2);
        assertThat(credentialService.getCredential(credential7.getId())).isEqualTo(credential7);
    }

    @Test
    void createCredential() throws Exception {
        //when
        jwtContextManager.setUser(user1);
        Credential created1 = credentialService.createCredential(credential4);

        jwtContextManager.setUser(user2);
        Credential created2 = credentialService.createCredential(credential9);

        //then
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(created1.getId())).isEqualTo(credential4);
        jwtContextManager.setUser(user2);
        assertThat(credentialService.getCredential(created2.getId())).isEqualTo(credential9);
    }

    @Test
    void updateCredential() throws Exception {
        //when
        jwtContextManager.setUser(user1);
        Credential updated1 = credentialService.updateCredential(credential5);

        jwtContextManager.setUser(user1);
        Credential updated2 = credentialService.updateCredential(credential10);

        //then
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(updated1.getId())).isEqualTo(credential5);
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(updated2.getId())).isEqualTo(credential10);
    }

    @Test
    void updateNonExistingCredential(){
        //when
        jwtContextManager.setUser(user1);

        //then
        assertThrows(CredentialNotFoundException.class, () -> credentialService.updateCredential(credential6));
    }

    @Test
    void deleteCredential() throws CredentialNotFoundException, RequestMadeByNonOwner {
        //when
        jwtContextManager.setUser(user1);
        Credential credential = credentialService.deleteCredential(credential3.getId());

        //then
        assertThat(credential).isEqualTo(credential3);
        assertThrows(CredentialNotFoundException.class, () -> credentialService.updateCredential(credential3));
    }

    @Test
    void getCredentialWillFailIfUserNotMatchCredentialUser() {
        jwtContextManager.setUser(user2);
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.getCredential(credential1.getId()));

        jwtContextManager.setUser(user1);
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.getCredential(credential7.getId()));
    }

    @Test
    void getAllCredentials_shouldReturnNothingForInvalidUser() {
        jwtContextManager.setUser(user3);
        List<String> credentialNames1 = credentialService.getAllCredentials().stream().map(Credential::getCredentialName).collect(Collectors.toList());
        assertThat(credentialNames1).isEmpty();
    }

    @Test
    void updateCredential_shouldFailIfUserNotMatchCredentialUser() {
        //when
        jwtContextManager.setUser(user1);

        //then
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.updateCredential(credential7));
    }

    @Test
    void deleteCredential_shouldFailIfUserNotMatchCredentialUser() {
        //when
        jwtContextManager.setUser(user1);

        //then
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.deleteCredential(credential7.getId()));
    }
}