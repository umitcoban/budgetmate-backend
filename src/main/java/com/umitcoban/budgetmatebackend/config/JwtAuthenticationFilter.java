// src/main/java/com/umitcoban/budgetmatebackend/config/JwtAuthenticationFilter.java
package com.umitcoban.budgetmatebackend.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.umitcoban.budgetmatebackend.modules.auth.application.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                SignedJWT jwt = jwtTokenService.parseAndValidate(token);

                String userId = jwt.getJWTClaimsSet().getSubject();
                String email  = (String) jwt.getJWTClaimsSet().getClaim("email");
                String rolesCsv = (String) jwt.getJWTClaimsSet().getClaim("roles");

                var authorities = Arrays.stream(rolesCsv.split(","))
                        .filter(s -> !s.isBlank())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

                var auth = new UsernamePasswordAuthenticationToken(
                        email,
                        token,
                        authorities
                );
                auth.setDetails(userId);

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (ParseException | JOSEException e) {

            }
        }

        filterChain.doFilter(request, response);
    }
}
