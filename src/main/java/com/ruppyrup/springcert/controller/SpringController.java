package com.ruppyrup.springcert.controller;

import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpringController {

    @Autowired
    CredentialService credentialService;

    @GetMapping("/credentials")
    @ResponseBody
    public List<Credential> getAllCredentials() {
        return credentialService.getAllCredentials();
    }

    @GetMapping("/credentials/{id}")
    @ResponseBody
    public Credential findCredential(@PathVariable String id) {
        return credentialService.getCredential(id);
    }

    @PostMapping("/credentials")
    @ResponseBody
    public Credential createCredential(@RequestBody Credential credential) {
        return credentialService.createCredential(credential);
    }

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
