package com.ruppyrup.springcert.controller;

import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SpringController {

    @Autowired
    CredentialService credentialService;

    private static final Logger LOGGER= LoggerFactory.getLogger(SpringController.class);

    @GetMapping("/credentials")
    public ResponseEntity<List<Credential>> getAllCredentials() {
        return new ResponseEntity<>(credentialService.getAllCredentials(), HttpStatus.OK);
    }

    @GetMapping("/credentials/{id}")
    public ResponseEntity<Credential> findCredential(@PathVariable Long id) {
        Credential credential = credentialService.getCredential(id);
        HttpStatus status = HttpStatus.OK;
        if (credential == null) status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(credential);
    }

    @PostMapping("/credentials")
    public ResponseEntity<Credential> createCredential(@RequestBody Credential credential) {
        Credential createdCredential = credentialService.createCredential(credential);
        HttpStatus status = HttpStatus.OK;
        if (createdCredential == null) status = HttpStatus.CONFLICT;
        return ResponseEntity
                .status(status)
                .body(credential);
    }

    @PutMapping("/credentials/{id}")
    public ResponseEntity<Credential> updateCredential(@PathVariable Long id, @RequestBody Credential credential) {
        Credential credentialToUpdate = credentialService.getCredential(id);
        credentialToUpdate.setLogin(credential.getLogin());
        credentialToUpdate.setUrl(credential.getUrl());
        credentialToUpdate.setPassword(credential.getPassword());
        credentialToUpdate.setCredentialName(credential.getCredentialName());
        Credential updatedCredential = credentialService.updateCredential(credentialToUpdate);
        HttpStatus status = HttpStatus.OK;
        log.info("Update class has found {}", updatedCredential);
        if (updatedCredential == null) status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(updatedCredential);
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<Credential>  deleteCredential(@PathVariable Long id) {
        Credential deletedCredential = credentialService.deleteCredential(id);
        HttpStatus status = HttpStatus.OK;
        if (deletedCredential == null) status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(deletedCredential);
    }

}
