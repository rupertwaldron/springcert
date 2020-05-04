package com.ruppyrup.springcert.jwt;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "integration"})
public class JwtContextManagerImpl implements JwtContextManager {
    @Override
    public String getAuthorizedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
