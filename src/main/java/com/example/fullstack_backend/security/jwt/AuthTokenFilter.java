package com.example.fullstack_backend.security.jwt;

import com.example.fullstack_backend.security.ShopUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.example.fullstack_backend.response.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Component

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private ShopUserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

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
            logger.error("Error JWT: {}" + e.getMessage(), e);
            sendErrorResponse(response);
            return;
        }
        filterChain.doFilter(request, response);

    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse("Invalid access token, please log in and try again");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRespone = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonRespone);
    }

    public Optional<String> parseJwt(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> StringUtils.hasText(header) && header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

}
