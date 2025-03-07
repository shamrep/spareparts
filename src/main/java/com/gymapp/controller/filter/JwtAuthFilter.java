package com.gymapp.controller.filter;

import com.gymapp.service.ClientAuthenticationService;
import com.gymapp.service.ClientAuthorizationService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthFilter implements Filter {

    private final ClientAuthenticationService clientAuthenticationService;
    private final ClientAuthorizationService clientAuthorizationService;

    // Constructor with dependency injection
    public JwtAuthFilter(
            ClientAuthenticationService clientAuthenticationService,
            ClientAuthorizationService clientAuthorizationService
    ) {

        this.clientAuthenticationService = clientAuthenticationService;
        this.clientAuthorizationService = clientAuthorizationService;

    }

    public JwtAuthFilter() {
        this(new ClientAuthenticationService(), new ClientAuthorizationService());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String url = req.getRequestURI(); // More reliable than getPathInfo()

        // Early return for unprotected URLs
        if (!clientAuthorizationService.isProtected(url)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Extract and validate token
        Optional<String> tokenResult = validateToken(req);

        if (tokenResult.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            return;
        }

        // Proceed with the request
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Optional<String> validateToken(HttpServletRequest req) {

        String authHeader = req.getHeader("Authorization");

        // Check if Authorization header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Validate token
        return clientAuthenticationService.isValidToken(token) &&
               clientAuthorizationService.isClientAuthorized(
                       token,
                       clientAuthenticationService.getClientRoles(token)
               )
                ? Optional.of(token)
                : Optional.empty();

    }

}
