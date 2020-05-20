package com.ruppyrup.springcert.service.impl;

import com.ruppyrup.springcert.dao.UserDao;
import com.ruppyrup.springcert.exceptions.ExistingUserException;
import com.ruppyrup.springcert.model.DAOUser;
import com.ruppyrup.springcert.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
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
        return userDao.save(newUser);
    }

    public void deleteUser(UserDTO userdto) {
        DAOUser existingUser = userDao.findByUsername(userdto.getUsername());
        userDao.delete(existingUser);
    }

    public DAOUser getUser(String userName) {
        return userDao.findByUsername(userName);
    }
}
