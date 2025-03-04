package com.spareparts.store.controller.filter;

import com.spareparts.store.service.AuthenticationService;
import com.spareparts.store.service.AuthorizationService;
import com.spareparts.store.service.model.Role;
import com.spareparts.store.service.model.RoleEnum;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtAuthFilter implements Filter {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final Map<String, List<RoleEnum>> map = new HashMap<>();

    public JwtAuthFilter() {

        this.authenticationService = new AuthenticationService();
        this.authorizationService = new AuthorizationService();
        map.put("/admin/*", List.of(RoleEnum.ADMIN));
        map.put("/client/*", List.of(RoleEnum.OWNER));
        map.put("/stats/attendance", List.of(RoleEnum.OWNER, RoleEnum.TRAINER));

    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String url = req.getPathInfo();

        if (requireRole(url)) {

            // Extract token from the Authorization header
            String token = req.getHeader("Authorization");

            System.out.println("JwtAuth HELLO!)");

            // If no token, deny access
            if (
                    token == null ||
                    !token.startsWith("Bearer ") ||
                    authenticationService.isValidToken(token) ||
                    !authorizationService.isAuthorized(token, map.get(url)) ) {

                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");

                return;

            }

        }

        // Proceed with the request
        filterChain.doFilter(servletRequest, servletResponse);

    }


    private boolean requireRole(String url) {

        return map.containsKey(url);

    }

}
