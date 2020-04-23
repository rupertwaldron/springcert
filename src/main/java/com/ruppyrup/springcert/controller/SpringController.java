package com.ruppyrup.springcert.controller;

import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpringController {

    @Autowired
    CredentialService credentialService;

    @GetMapping("/credentials")
    @ResponseBody
    public String firstPage() {
        StringBuilder output = new StringBuilder("<h1>This is the credential list</h1><pre>");

        credentialService.getAllCredentials().stream()
                .forEach(output::append);

        output.append("</pre>");
        return output.toString();
    }

    @GetMapping("/credentials/{id}")
    @ResponseBody
    public String findCredential(@PathVariable String id) {
        return credentialService.getCredential(id).toString();
    }


    @PostMapping("/credentials")
    @ResponseBody
    public boolean createCredential(@RequestBody Credential credential) {
        return credentialService.createCredential(credential);
    }

}
