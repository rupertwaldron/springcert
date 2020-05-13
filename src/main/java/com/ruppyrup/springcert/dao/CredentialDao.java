package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.model.Credential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialDao extends CrudRepository<Credential, Long> {
    List<Credential> findAllByUser(String user);
    Optional<Credential> findByUuid(String uuid);

    Credential findByCredentialNameAndUser(String CredentialName, String user);
}
