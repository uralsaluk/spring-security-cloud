package com.ural.security.firstsecurity.config;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (StringUtils.equalsAnyIgnoreCase(userName, "ural")
                && StringUtils.equalsAnyIgnoreCase(password, "saluk")) {

            return new UsernamePasswordAuthenticationToken(userName,password, Arrays.asList());


        }else {

            throw new BadCredentialsException("Invalid user or pass");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
