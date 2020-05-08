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

    //todo can't create a credential that is already there
    @PostMapping("/credentials")
    @ResponseBody
    public Credential createCredential(@RequestBody Credential credential) {
        return credentialService.createCredential(credential);
    }

    //todo need to change so can't update a credential that doesn't exist
    @PutMapping("/credentials/{id}")
    @ResponseBody
    public Credential updateCredential(@RequestBody Credential credential) {
        return credentialService.updateCredential(credential);
    }

    @DeleteMapping("/credentials/{id}")
    @ResponseBody
    public Credential deleteCredential(@PathVariable String id) {
        return credentialService.deleteCredential(id);
    }

}
