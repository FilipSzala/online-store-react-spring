package com.example.fullstack_backend.config;

import com.example.fullstack_backend.security.ShopUserDetailsService;
import com.example.fullstack_backend.security.jwt.JwtEntryPoint;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ShopConfig {
    private final ShopUserDetailsService userDetailsService;
    private final JwtEntryPoint authEntryPoint;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
     return authConfig.getAuthenticationManager();
    }
    

}
