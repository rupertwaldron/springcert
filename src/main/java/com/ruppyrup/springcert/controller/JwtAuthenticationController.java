package com.ruppyrup.springcert.controller;

import com.ruppyrup.springcert.exceptions.ExistingUserException;
import com.ruppyrup.springcert.jwt.JwtRequest;
import com.ruppyrup.springcert.jwt.JwtResponse;
import com.ruppyrup.springcert.jwt.JwtTokenUtil;
import com.ruppyrup.springcert.model.DAOUser;
import com.ruppyrup.springcert.model.UserDTO;
import com.ruppyrup.springcert.service.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register")
    public ResponseEntity<DAOUser> saveUser(@RequestBody UserDTO user) {
        DAOUser createdUser = null;
        HttpStatus status = HttpStatus.CREATED;
        try {
            createdUser = userDetailsService.save(user);
        } catch (ExistingUserException e) {
            status = HttpStatus.FORBIDDEN;
        }
       return ResponseEntity
               .status(status)
               .body(createdUser);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "/users")
    public void deleteUser(@RequestBody UserDTO userDTO) {
            userDetailsService.deleteUser(userDTO);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
