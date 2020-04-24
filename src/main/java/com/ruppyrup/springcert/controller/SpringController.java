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
    public List<Credential> firstPage() {
        return credentialService.getAllCredentials();
    }

    @GetMapping("/credentials/{id}")
    @ResponseBody
    public List<Credential> findCredential(@PathVariable String id) {
        return credentialService.getCredential(id);
    }


    @PostMapping("/credentials")
    @ResponseBody
    public Credential createCredential(@RequestBody Credential credential) {
        return credentialService.createCredential(credential);
    }

}
