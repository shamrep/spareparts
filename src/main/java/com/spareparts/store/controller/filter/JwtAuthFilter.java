package com.spareparts.store.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // Extract token from the Authorization header
        String token = req.getHeader("Authorization");

        System.out.println("JwtAuth HELLO!)");

        // If no token, deny access
        if (token == null || !token.startsWith("Bearer ")) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            return;
        }


        // Proceed with the request
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
