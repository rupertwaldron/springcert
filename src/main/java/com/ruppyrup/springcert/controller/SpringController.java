package com.ruppyrup.springcert.controller;

import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpringController {

    @Autowired
    CredentialService credentialService;

    @GetMapping("/credentials")
    public ResponseEntity<List<Credential>> getAllCredentials() {
        return new ResponseEntity<>(credentialService.getAllCredentials(), HttpStatus.OK);
    }

    @GetMapping("/credentials/{id}")
    public ResponseEntity<Credential> findCredential(@PathVariable String id) {
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
    public ResponseEntity<Credential> updateCredential(@RequestBody Credential credential) {
        Credential updatedCredential = credentialService.updateCredential(credential);
        HttpStatus status = HttpStatus.OK;
        if (updatedCredential == null) status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(updatedCredential);
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<Credential>  deleteCredential(@PathVariable String id) {
        Credential deletedCredential = credentialService.deleteCredential(id);
        HttpStatus status = HttpStatus.OK;
        if (deletedCredential == null) status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(deletedCredential);
    }

}
