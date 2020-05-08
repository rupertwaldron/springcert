package com.ruppyrup.springcert.model;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ",\n password='" + password + '\'' +
                '}';
    }
}
