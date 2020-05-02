package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.model.Credential;

import java.util.List;

public interface CredentialDao {
    List<Credential> getAllCredentials(String user);

    Credential getCredential(String credentialId, String user);

    Credential create(Credential credential);

    Credential delete(String credentialId, String user);

    Credential update(Credential credential);
}
