package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.exceptions.RequestMadeByNonOwner;
import com.ruppyrup.springcert.jwt.JwtContextManager;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    CredentialDao credentialDao;

    @Autowired
    JwtContextManager jwtContextManager;

    @Override
    public List<Credential> getAllCredentials() {
        return credentialDao.findAllByUser(jwtContextManager.getAuthorizedUser());
    }

    @Override
    public Credential getCredential(Long id) throws CredentialNotFoundException, RequestMadeByNonOwner {
        Credential foundCredential = credentialDao.findById(id).orElseThrow(CredentialNotFoundException::new);
        if (!foundCredential.getUser().equals(jwtContextManager.getAuthorizedUser())) throw new RequestMadeByNonOwner();
        return foundCredential;
    }

    @Override
    public Credential createCredential(Credential credential) {
        credential.setUser(jwtContextManager.getAuthorizedUser());
        return credentialDao.save(credential);
    }

    @Override
    public Credential updateCredential(Credential credential) throws CredentialNotFoundException {
        Credential credentialToUpdate = credentialDao.findById(credential.getId()).orElseThrow(CredentialNotFoundException::new);
        credentialToUpdate.setCredentialName(credential.getCredentialName());
        credentialToUpdate.setPassword(credential.getPassword());
        credentialToUpdate.setUrl(credential.getUrl());
        credentialToUpdate.setLogin(credential.getLogin());
        return credentialDao.save(credentialToUpdate);
    }

    @Override
    public Credential deleteCredential(Long id) throws CredentialNotFoundException {
        Credential credentialToDelete = credentialDao.findById(id).orElseThrow(CredentialNotFoundException::new);;
        if (credentialToDelete == null) return null;
        credentialDao.delete(credentialToDelete);
        return credentialToDelete;
    }
}
