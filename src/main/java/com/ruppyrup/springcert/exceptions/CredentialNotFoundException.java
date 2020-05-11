package com.ruppyrup.springcert.exceptions;

public class CredentialNotFoundException extends Exception {
    public CredentialNotFoundException() {
        super("Credential not found");
    }
}
