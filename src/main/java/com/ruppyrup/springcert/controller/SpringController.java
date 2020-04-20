package com.ruppyrup.springcert.controller;

import com.ruppyrup.springcert.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class SpringController {

    @Autowired
    CredentialService credentialService;

    @GetMapping("/credentials")
    @ResponseBody
    public String firstPage() {
        StringBuilder output = new StringBuilder("<h1>This is the credential list</h1><pre>");

        credentialService.getAllCredentials().stream()
                .map(credential -> credential + "\n")
                .forEach(output::append);

        output.append("</pre>");
        return output.toString();
    }
}
