package com.ural.resourceserver.uralresourceserver.controller;

import com.ural.resourceserver.uralresourceserver.util.ContextUtil;
import com.ural.resourceserver.uralresourceserver.util.CustomContext;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {

    @GetMapping("/api/users")
    public CustomContext getUser() {

        CustomContext context = ContextUtil.getContextUser();

        System.out.println(context);



        return context;
    }
}
