package com.ruppyrup.springcert.service;

import com.ruppyrup.springcert.model.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> getAllCredentials();
    List<Credential> getCredential(String credentialId);
    boolean createCredential(Credential credentialId);
}
