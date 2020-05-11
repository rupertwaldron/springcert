package com.ruppyrup.springcert.controller;

import com.ruppyrup.springcert.exceptions.CredentialNotFoundException;
import com.ruppyrup.springcert.exceptions.RequestMadeByNonOwner;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/credentials")
    public ResponseEntity<List<Credential>> getAllCredentials() {
        return new ResponseEntity<>(credentialService.getAllCredentials(), HttpStatus.OK);
    }

    @GetMapping("/credentials/{id}")
    public ResponseEntity<Credential> findCredential(@PathVariable Long id) {
        HttpStatus status = HttpStatus.OK;
        Credential credential = null;
        try {
            credential = credentialService.getCredential(id);
        } catch (CredentialNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
        } catch (RequestMadeByNonOwner re) {
            status = HttpStatus.UNAUTHORIZED;
        }
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
        credential.setId(id);
        HttpStatus status = HttpStatus.OK;
        Credential updatedCredential = null;
        try {
            updatedCredential = credentialService.updateCredential(credential);
        } catch (CredentialNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(updatedCredential);
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<Credential>  deleteCredential(@PathVariable Long id) {
        HttpStatus status = HttpStatus.OK;
        Credential deletedCredential = null;
        try {
            deletedCredential = credentialService.deleteCredential(id);
        } catch (CredentialNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(deletedCredential);
    }

}
