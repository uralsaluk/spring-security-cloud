package com.ural.security.firstsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class MySecurityConfig  extends WebSecurityConfigurerAdapter {


    private MyAuthenticationProvider authenticationProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MySecurityConfig(MyAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
       this.passwordEncoder = passwordEncoder;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                });
    //    http.addFilterBefore(new MySecurityFilter(), BasicAuthenticationFilter.class);
    }

   /* @Bean
        public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder,
                                                 UserDetailsService userDetailService) throws Exception {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                    .authenticationProvider(authenticationProvider)
                    .build();
        }*/




     /*   @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.httpBasic();
            http.authorizeRequests().anyRequest().authenticated();
            return http.build();

        }*/

        @Bean
        public BCryptPasswordEncoder passwordEncoder(){

            return new BCryptPasswordEncoder();
        }






}
