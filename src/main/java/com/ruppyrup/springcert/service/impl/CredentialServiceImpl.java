package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.jwt.JwtContextManager;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
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
    public Credential getCredential(String credentialId) {
        return credentialDao.findByCredentialIdAndUser(credentialId, jwtContextManager.getAuthorizedUser());
    }

    @Override
    public Credential createCredential(Credential credential) {
        credential.setUser(jwtContextManager.getAuthorizedUser());
        return credentialDao.save(credential);
    }

    @Override
    public Credential updateCredential(Credential credential) {
        Credential credentialToUpdate = credentialDao.findByCredentialIdAndUser(credential.getCredentialId(), jwtContextManager.getAuthorizedUser());
        if (credentialToUpdate == null) return null;
        credentialToUpdate.setPassword(credential.getPassword());
        credentialToUpdate.setUrl(credential.getUrl());
        credentialToUpdate.setLogin(credential.getLogin());
        return credentialDao.save(credentialToUpdate);
    }

    @Override
    public Credential deleteCredential(String credentialId) {
        Credential credentialToDelete = credentialDao.findByCredentialIdAndUser(credentialId, jwtContextManager.getAuthorizedUser());
        if (credentialToDelete == null) return null;
        credentialDao.delete(credentialToDelete);
        return credentialToDelete;
    }
}
