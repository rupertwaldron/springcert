package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.config.JwtContextManagerTestImpl;
import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.exceptions.RequestMadeByNonOwner;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.junit.jupiter.api.AfterEach;
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
@ActiveProfiles("test")
class CredentialServiceImplTest {

    private String user1 = "javainuse";
    private String user2 = "ruppyrup";
    private String user3 = "bob";
    private Credential amazonUser1 = new Credential( "Amazon", "www.amazon.com", "pete", "football", user1);
    private Credential pondUser1 = new Credential("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", user1);
    private Credential jlUser1 = new Credential("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", user1);
    private Credential tt = new Credential("Tops tiles", "www.topstiles.com", "rupert.waldron@yahoo.co.uk", "tilly", "");
    private Credential pp2User1 = new Credential("PondPlanet", "www.pondplanet2.com", "Lee", "monster", user1);
    private Credential prattUser1 = new Credential("Pratts Pods", "www.pp.com", "Simon", "gobsmack", user1);
    private Credential amazonUser2 = new Credential("Amazon", "www.amazon.com", "rupert", "sweetpea", user2);
    private Credential jlUser2 = new Credential("John Lewis", "www.johnlewis.com", "ruppyruyp@yahoo.co.uk", "deadsea", user2);
    private Credential jl = new Credential("John Lewis2", "www.johnlewis2.com", "ruppyruyp2@yahoo.co.uk", "deadsea2", "");

    @Autowired
    CredentialService credentialService;

    @Autowired
    JwtContextManagerTestImpl jwtContextManager;

    @Autowired
    CredentialDao credentialDao;

    @BeforeEach
    void setUp() {
        credentialDao.save(amazonUser1);
        credentialDao.save(pondUser1);
        credentialDao.save(jlUser1);
        credentialDao.save(amazonUser2);
        credentialDao.save(jlUser2);
        credentialDao.findAll().forEach(System.out::println);
    }

    @AfterEach
    void cleanUp() {
        credentialDao.deleteAll();
    }

    @Test
    void getAllCredentials() {
        jwtContextManager.setUser(user1);
        List<String> credentialNames1 = credentialService.getAllCredentials().stream().map(Credential::getCredentialName).collect(Collectors.toList());
        assertThat(credentialNames1).containsExactlyInAnyOrder(jlUser1.getCredentialName(), pondUser1.getCredentialName(), amazonUser1.getCredentialName());

        jwtContextManager.setUser(user2);
        List<String> credentialNames2 = credentialService.getAllCredentials().stream().map(Credential::getCredentialName).collect(Collectors.toList());
        assertThat(credentialNames2).containsExactlyInAnyOrder(amazonUser2.getCredentialName(), jlUser2.getCredentialName());
    }

    @Test
    void getCredential() throws Exception {
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(amazonUser1.getUuid())).isEqualTo(amazonUser1);

        jwtContextManager.setUser(user2);
        assertThat(credentialService.getCredential(amazonUser2.getUuid())).isEqualTo(amazonUser2);
    }

    @Test
    void createCredential() throws Exception {
        //when
        jwtContextManager.setUser(user1);
        Credential created1 = credentialService.createCredential(tt);

        jwtContextManager.setUser(user2);
        Credential created2 = credentialService.createCredential(jl);

        //then
        jwtContextManager.setUser(user1);
        assertThat(credentialService.getCredential(created1.getUuid())).isEqualTo(tt);
        jwtContextManager.setUser(user2);
        assertThat(credentialService.getCredential(created2.getUuid())).isEqualTo(jl);
    }

    @Test
    void updateCredential() throws Exception {
        //when
        jwtContextManager.setUser(user1);
        pp2User1.setUuid(pondUser1.getUuid());
        pp2User1.setId(pondUser1.getId());
        Credential updated1 = credentialService.updateCredential(pp2User1);

        //then
        assertThat(updated1).isEqualTo(pp2User1);
    }

    @Test
    void updateNonExistingCredential(){
        //when
        jwtContextManager.setUser(user1);

        //then
        assertThrows(CredentialNotFoundException.class, () -> credentialService.updateCredential(prattUser1));
    }

    @Test
    void deleteCredential() throws CredentialNotFoundException, RequestMadeByNonOwner {
        //when
        jwtContextManager.setUser(user1);
        Credential credential = credentialService.deleteCredential(jlUser1.getUuid());

        //then
        assertThat(credential).isEqualTo(jlUser1);
        assertThrows(CredentialNotFoundException.class, () -> credentialService.updateCredential(jlUser1));
    }

    @Test
    void getCredentialWillFailIfUserNotMatchCredentialUser() {
        jwtContextManager.setUser(user2);
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.getCredential(amazonUser1.getUuid()));

        jwtContextManager.setUser(user1);
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.getCredential(amazonUser2.getUuid()));
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
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.updateCredential(amazonUser2));
    }

    @Test
    void deleteCredential_shouldFailIfUserNotMatchCredentialUser() {
        //when
        jwtContextManager.setUser(user1);

        //then
        assertThrows(RequestMadeByNonOwner.class, () -> credentialService.deleteCredential(amazonUser2.getUuid()));
    }
}