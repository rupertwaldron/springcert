package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.model.Credential;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CredentialDao extends CrudRepository<Credential, Long> {
    List<Credential> findAllByUser(String user);

    Credential findByCredentialIdAndUser(String credentialId, String user);
}
