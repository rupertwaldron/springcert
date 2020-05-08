package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.dao.impl.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Long> {
    DAOUser findByUsername(String username);
    void deleteByUsername(String username);
}
