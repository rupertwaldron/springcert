package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    CredentialDao credentialDao;

    @Override
    public List<Credential> getAllCredentials() {
        return credentialDao.getAllCredentials(getAuthorizedUser());
    }

    @Override
    public Credential getCredential(String credentialId) {
        return credentialDao.getCredential(credentialId, getAuthorizedUser());
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
        return credentialDao.delete(credentialId, getAuthorizedUser());
    }

    private String getAuthorizedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
