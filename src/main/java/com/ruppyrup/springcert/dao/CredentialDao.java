package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.model.Credential;

import java.util.List;

public interface CredentialDao {
    List<Credential> getAllCredentials();

    Credential getCredential(String credentialId);

    Credential create(Credential credential);

    Credential delete(String credentialId);

    Credential update(Credential credential);
}
