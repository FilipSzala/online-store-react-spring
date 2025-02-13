package com.example.fullstack_backend.security.jwt;

import com.example.fullstack_backend.security.ShopUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor

public class AuthTokenFilter extends OncePerRequestFilter {

    private final ShopUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            parseJwt(request)
                    .filter(jwtUtils::validateToken)
                    .ifPresent(jwt -> {
                        String username = jwtUtils.getUsernameFromToken(jwt);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
        } catch (Exception e) {
            logger.error("Błąd w filtrowaniu JWT: {}" + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }

    public Optional<String> parseJwt(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> StringUtils.hasText(header) && header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

}
