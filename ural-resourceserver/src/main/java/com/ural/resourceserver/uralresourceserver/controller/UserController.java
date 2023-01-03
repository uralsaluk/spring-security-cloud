package com.ural.resourceserver.uralresourceserver.controller;

import com.ural.resourceserver.uralresourceserver.config.DelegatedThreadPoolExecutor;
import com.ural.resourceserver.uralresourceserver.util.ContextUtil;
import com.ural.resourceserver.uralresourceserver.util.CustomContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {


    private DelegatedThreadPoolExecutor threadPoolExecutor;

    @Autowired
    public UserController(DelegatedThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @GetMapping("/api/users")
    public CustomContext getUser() {

        CustomContext context = ContextUtil.getContextUser();

        System.out.println(context);



        return context;
    }

    @GetMapping("/api/users/testContext")
    public  ResponseEntity<Object> testContext() {

        CustomContext context = ContextUtil.getContextUser();

        System.out.println(context);


        CompletableFuture.runAsync(()->testMethod(),threadPoolExecutor).join();



        return new ResponseEntity<>(context,HttpStatus.OK);
    }

    @Async
    @PostMapping("/api/users/testContextAsync")
    public  ResponseEntity<Object> testContextAsync() {

        CustomContext context = ContextUtil.getContextUser();

        System.out.println(context);


        CompletableFuture.runAsync(()->testMethod(),threadPoolExecutor).join();



        return new ResponseEntity<>(context,HttpStatus.OK);
    }



    private void testMethod(){
        CustomContext context = ContextUtil.getContextUser();

        System.out.println(context);
    }
}
