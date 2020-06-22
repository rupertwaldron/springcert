package com.ruppyrup.springcert.service;

import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.model.CredentialDTO;

import java.util.List;

public interface CredentialService {
    List<Credential> getAllCredentials();

    Credential getCredential(String uuid) throws CredentialNotFoundException;

    Credential createCredential(CredentialDTO credential);

    Credential updateCredential(String uuid, CredentialDTO credential) throws CredentialNotFoundException;

    Credential deleteCredential(String uuid) throws CredentialNotFoundException;
}
