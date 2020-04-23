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
    public List<Credential> getAllCredentials() {
        return credentialDao.getAllCredentials();
    }

    @Override
    public List<Credential> getCredential(String credentialId) {
        return credentialDao.getCredential(credentialId);
    }

    @Override
    public boolean createCredential(Credential credential) {
        return credentialDao.create(credential);
    }


}
