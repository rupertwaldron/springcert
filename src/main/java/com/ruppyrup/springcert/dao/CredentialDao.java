package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.model.Credential;

import java.util.List;

public interface CredentialDao {
    List<Credential> getAllCredentials();
    List<Credential> getCredential(String credentialId);
    boolean create(Credential credential);
    boolean delete(Credential credential);
    boolean update(Credential credential);
}
