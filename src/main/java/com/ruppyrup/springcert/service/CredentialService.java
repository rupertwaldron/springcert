package com.ruppyrup.springcert.service;

import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.exceptions.RequestMadeByNonOwner;
import com.ruppyrup.springcert.model.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> getAllCredentials();

    Credential getCredential(Long id) throws CredentialNotFoundException, RequestMadeByNonOwner;

    Credential createCredential(Credential credential);

    Credential updateCredential(Credential credential) throws CredentialNotFoundException;

    Credential deleteCredential(Long id) throws CredentialNotFoundException;
}
