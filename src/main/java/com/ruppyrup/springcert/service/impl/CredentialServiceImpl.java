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
        return credentialDao.getAllCredentials(jwtContextManager.getAuthorizedUser());
    }

    @Override
    public Credential getCredential(String credentialId) {
        return credentialDao.getCredential(credentialId, jwtContextManager.getAuthorizedUser());
    }

    @Override
    public Credential createCredential(Credential credential) {
        return credentialDao.create(credential);
    }

    @Override
    public Credential updateCredential(Credential credential) {
        return credentialDao.update(credential);
    }

    @Override
    public Credential deleteCredential(String credentialId) {
        return credentialDao.delete(credentialId, jwtContextManager.getAuthorizedUser());
    }
}
