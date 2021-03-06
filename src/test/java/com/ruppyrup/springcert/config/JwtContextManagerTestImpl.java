package com.ruppyrup.springcert.config;

import com.ruppyrup.springcert.jwt.JwtContextManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class JwtContextManagerTestImpl implements JwtContextManager {

    private String userName;

    public void setUser(String userName) {
        this.userName = userName;
    }

    @Override
    public String getAuthorizedUser() {
        return userName;
    }
}
