package com.ruppyrup.springcert.service;

import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.exceptions.RequestMadeByNonOwner;
import com.ruppyrup.springcert.model.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> getAllCredentials();

    Credential getCredential(String uuid) throws CredentialNotFoundException, RequestMadeByNonOwner;

    Credential createCredential(Credential credential);

    Credential updateCredential(Credential credential) throws CredentialNotFoundException, RequestMadeByNonOwner;

    Credential deleteCredential(String uuid) throws CredentialNotFoundException, RequestMadeByNonOwner;
}
