package com.ruppyrup.springcert.unit_tests;

import com.ruppyrup.springcert.controller.SpringController;
import com.ruppyrup.springcert.service.CredentialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CredentialControllerUT {

    @Mock
    private CredentialService credentialService;

    @InjectMocks
    private SpringController controller;

    @Test
    public void shouldReturnHelloWhenHelloEndpointIsCalled() {
        Assertions.assertEquals("Hello from credentials", controller.sayHello());
    }
}
