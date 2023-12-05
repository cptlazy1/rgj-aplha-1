package com.example.rgjalpha1.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;

import java.util.Objects;
import java.util.function.Supplier;

public class UserAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {



    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();

        String path = request.getRequestURI();

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String urlUsername = antPathMatcher.extractUriTemplateVariables("/users/{username}/**", path).get("username");

        // This code prevents NullPointerException when authenticationSupplier.get() returns null
        UserDetails userDetails = (UserDetails) (authenticationSupplier.get() != null ? authenticationSupplier.get().getPrincipal() : null);
        String authenticatedUsername = userDetails != null ? userDetails.getUsername() : null;

        boolean granted = Objects.equals(authenticatedUsername, urlUsername);
        return new AuthorizationDecision(granted);
    }
}

