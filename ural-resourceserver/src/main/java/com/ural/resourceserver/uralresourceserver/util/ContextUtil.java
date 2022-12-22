package com.ural.resourceserver.uralresourceserver.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContextUtil {

    public static String getUserName(){

        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return (String) token.getClaims().get("firstname");

    }


    public static CustomContext getContextUser(){
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> claims = token.getClaims();

            CustomContext context = new ContextUser(
                (String) claims.get("sub"),
                (String) claims.get("firstName"),
                (String) claims.get("lastName"),
             token.getClaimAsStringList("roles"),
          token.getClaimAsStringList("scope"))
                ;






        return context;

    }

}
