package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    CredentialDao credentialDao;

    @Override
    public List<Credential> getAllCredentials(String user) {
        return credentialDao.getAllCredentials(user);
    }

    @Override
    public Credential getCredential(String credentialId, String user) {
        return credentialDao.getCredential(credentialId, user);
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
    public Credential deleteCredential(String credentialId, String user) {
        return credentialDao.delete(credentialId, user);
    }


}
