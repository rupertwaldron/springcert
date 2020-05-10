package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
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
    public Credential getCredential(String CredentialName) {
        return credentialDao.findByCredentialNameAndUser(CredentialName, jwtContextManager.getAuthorizedUser());
    }

    @Override
    public Credential createCredential(Credential credential) {
        credential.setUser(jwtContextManager.getAuthorizedUser());
        return credentialDao.save(credential);
    }

    @Override
    public Credential updateCredential(Credential credential) {
        Credential credentialToUpdate = credentialDao.findByCredentialNameAndUser(credential.getCredentialName(), jwtContextManager.getAuthorizedUser());
        if (credentialToUpdate == null) return null;
        credentialToUpdate.setPassword(credential.getPassword());
        credentialToUpdate.setUrl(credential.getUrl());
        credentialToUpdate.setLogin(credential.getLogin());
        return credentialDao.save(credentialToUpdate);
    }

    @Override
    public Credential deleteCredential(String CredentialName) {
        Credential credentialToDelete = credentialDao.findByCredentialNameAndUser(CredentialName, jwtContextManager.getAuthorizedUser());
        if (credentialToDelete == null) return null;
        credentialDao.delete(credentialToDelete);
        return credentialToDelete;
    }
}
