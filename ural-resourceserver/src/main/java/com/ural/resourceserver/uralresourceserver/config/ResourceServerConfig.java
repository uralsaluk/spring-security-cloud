package com.ural.resourceserver.uralresourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableWebSecurity
public class ResourceServerConfig {


    private static final String ROLES_CLAIM = "roles";
    private static final String SCOPES_CLAIM = "scope";

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtToAuthorityConverter() {
        return new Converter<Jwt, Collection<GrantedAuthority>>() {

            @Override
            public Collection<GrantedAuthority> convert(Jwt jwt) {
                List<String> roles = jwt.getClaimAsStringList(ROLES_CLAIM);
                List<String> scope = jwt.getClaimAsStringList(SCOPES_CLAIM);
                if (roles != null) {

                return     Stream.concat(roles.stream().map(eachRole->new SimpleGrantedAuthority(eachRole)),
                                            scope.stream() .map(eachScope->new SimpleGrantedAuthority(eachScope)))
                        .collect(Collectors.toList());


               /*     return roles.stream().map(eachRole -> new SimpleGrantedAuthority(eachRole)).
                            .collect(Collectors.toList());*/
                }
                return Collections.emptyList();
            }

        };

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtToAuthorityConverter());

        http
                .authorizeRequests()
                .mvcMatchers("/api/**")
                .access("  hasAuthority('read') and  hasRole('USER') ")
                //.hasAnyRole("USER")
            //

                .and()
                .oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);
        return http.build();
    }
}
