package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.model.Credential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialDao extends CrudRepository<Credential, Long> {
    List<Credential> findAllByUser(String user);

    Credential findByCredentialNameAndUser(String CredentialName, String user);
}
