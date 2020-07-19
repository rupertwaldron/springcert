package com.ruppyrup.springcert.unit_tests;

import com.ruppyrup.springcert.controller.SpringController;
import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.model.DAOUser;
import com.ruppyrup.springcert.service.CredentialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class CredentialControllerUT {

    @Mock
    private CredentialService credentialService;

    @InjectMocks
    private SpringController controller;

    private DAOUser user = new DAOUser();
    private Credential credential = new Credential(1L, "123", "Amazon", "www.amazon.com", "login", "password", user);
    private Credential credential2 = new Credential(2L, "456", "Boots", "www.boots.com", "john", "secrete", user);

    @Test
    public void shouldReturnHelloWhenHelloEndpointIsCalled() {
        Assertions.assertEquals("Hello from credentials", controller.sayHello());
    }

    @Test
    public void shouldReturnCredentialForGetRequest() throws CredentialNotFoundException {
        //given
        ResponseEntity<Credential> getResponse = ResponseEntity.ok(credential);
        //when
        Mockito.when(credentialService.getCredential(anyString())).thenReturn(credential);
        //then
        Assertions.assertEquals(getResponse, controller.findCredential("123"));
    }

    @Test
    public void shouldReturnListOfCredentials() {
        //given
        List<Credential> credentials = List.of(credential, credential2);
        ResponseEntity<List<Credential>> getAllResponse = ResponseEntity.ok(credentials);
        //when
        Mockito.when(credentialService.getAllCredentials()).thenReturn(credentials);
        //then
        Assertions.assertEquals(getAllResponse, controller.getAllCredentials());
    }

}
