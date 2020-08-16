package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.UserDao;
import com.ruppyrup.springcert.exceptions.ExistingUserException;
import com.ruppyrup.springcert.model.DAOUser;
import com.ruppyrup.springcert.model.UserDTO;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

// this is used by authenticate method to authenticate the user
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private Counter userCount;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public JwtUserDetailsService(MeterRegistry meterRegistry) {
        userCount = meterRegistry.counter("storage.user.count", "type", "user");
    }

    @PostConstruct
    public void countUsers() {
        userDao.findAll().forEach(cred -> userCount.increment());
    }

    @Override //this method is used my authenticate in the
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public DAOUser save(UserDTO user) throws ExistingUserException {
        DAOUser existingUser = userDao.findByUsername(user.getUsername());
        if (existingUser != null) throw new ExistingUserException();

        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        userCount.increment();
        return userDao.save(newUser);
    }

    public void deleteUser(UserDTO userdto) {
        DAOUser existingUser = userDao.findByUsername(userdto.getUsername());
        userDao.delete(existingUser);
        userCount.increment(-1);
    }

    public DAOUser getUser(String userName) {
        return userDao.findByUsername(userName);
    }
}
