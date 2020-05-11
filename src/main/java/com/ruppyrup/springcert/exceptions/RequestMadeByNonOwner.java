package com.ruppyrup.springcert.exceptions;

public class RequestMadeByNonOwner extends Exception {
    public RequestMadeByNonOwner() {
        super("Credential request on credential made by non-owner");
    }
}
