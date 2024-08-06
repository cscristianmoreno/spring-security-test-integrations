package com.app.test.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomOncePerRequestFilter customOncePerRequestFilter;

    public SecurityConfig(final CustomUserDetailsService customUserDetailsService, final CustomAuthenticationManager customAuthenticationManager,
    final CustomAuthenticationProvider customAuthenticationProvider, final CustomOncePerRequestFilter customOncePerRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationManager = customAuthenticationManager;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.customOncePerRequestFilter = customOncePerRequestFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (
            httpSecurity
                .authorizeHttpRequests((req) -> {
                    req.requestMatchers("/auth/login").permitAll();
                    req.requestMatchers("/auth/register").permitAll();
                    req.anyRequest().authenticated();
                })
                .userDetailsService(customUserDetailsService)
                .authenticationManager(customAuthenticationManager)
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(customOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf((csrf) -> {
                    csrf.disable();
                })
                .httpBasic(Customizer.withDefaults())
            .build()
        );
    }
}
