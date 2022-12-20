package com.ural.security.firstsecurity.config;

import javax.servlet.*;
import java.io.IOException;

public class MySecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("custom filter");
    }
}
