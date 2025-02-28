package com.spareparts.store.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class AuthFilter implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter is called.");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
