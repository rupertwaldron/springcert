package com.ruppyrup.springcert.service;

import com.ruppyrup.springcert.model.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> getAllCredentials();

    Credential getCredential(String credentialId);

    Credential createCredential(Credential credential);

    Credential updateCredential(Credential credential);

    Credential deleteCredential(String credentialId);
}
