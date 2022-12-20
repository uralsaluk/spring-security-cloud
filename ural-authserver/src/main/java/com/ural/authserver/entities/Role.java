package com.ural.authserver.entities;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;


    @Override
    public String getAuthority() {
        return this.name;
    }
}
