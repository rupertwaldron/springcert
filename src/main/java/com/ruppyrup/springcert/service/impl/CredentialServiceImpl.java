package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.jwt.JwtContextManager;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.model.CredentialDTO;
import com.ruppyrup.springcert.model.DAOUser;
import com.ruppyrup.springcert.service.CredentialService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {

    private Counter credentialCount;

    @Autowired
    CredentialDao credentialDao;

    @Autowired
    JwtContextManager jwtContextManager;

    @Autowired
    JwtUserDetailsService userService;

    public CredentialServiceImpl(MeterRegistry meterRegistry) {
        credentialCount = meterRegistry.counter("storage.credential.count", "type", "credential");
    }

    @PostConstruct
    public void countCredentials() {
        credentialDao.findAll().forEach(cred -> credentialCount.increment());
    }

    @Override
    public List<Credential> getAllCredentials() {
        DAOUser authorizedUser = userService.getUser(jwtContextManager.getAuthorizedUser());
        return credentialDao.findAllByUser(authorizedUser);
    }

    @Override
    public Credential getCredential(String uuid) throws CredentialNotFoundException {
        Credential foundCredential = getAuthorizedCredential(uuid);
        return foundCredential;
    }

    @Override
    public Credential createCredential(CredentialDTO credentialDto) {
        DAOUser authorizedUser = userService.getUser(jwtContextManager.getAuthorizedUser());
        Credential credential = new Credential(credentialDto);
        credential.setUser(authorizedUser);
        credentialCount.increment();
        return credentialDao.save(credential);
    }

    @Override
    public Credential updateCredential(String uuid, CredentialDTO credentialDTO) throws CredentialNotFoundException {
        Credential credentialToUpdate = getAuthorizedCredential(uuid);
        credentialToUpdate.setUrl(credentialDTO.getUrl());
        credentialToUpdate.setLogin(credentialDTO.getLogin());
        credentialToUpdate.setCredentialName(credentialDTO.getCredentialName());
        credentialToUpdate.setPassword(credentialDTO.getPassword());
        return credentialDao.save(credentialToUpdate);
    }

    @Override
    public Credential deleteCredential(String uuid) throws CredentialNotFoundException{
        Credential credentialToDelete = getAuthorizedCredential(uuid);
        if (credentialToDelete == null) return null;
        credentialDao.delete(credentialToDelete);
        credentialCount.increment(-1);
        return credentialToDelete;
    }

    private Credential getAuthorizedCredential(String uuid) throws CredentialNotFoundException {
        DAOUser authorizedUser = userService.getUser(jwtContextManager.getAuthorizedUser());
        Credential credential = credentialDao.findByUuidAndUser(uuid, authorizedUser).orElseThrow(CredentialNotFoundException::new);
        return credential;
    }
}
