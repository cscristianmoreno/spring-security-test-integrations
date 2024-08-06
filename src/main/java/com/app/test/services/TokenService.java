package com.app.test.services;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;

@Service
public class TokenService {
    
    private final JwtEncoder jwtEncoder;

    public TokenService(final JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String encode(Authentication authentication) {
        String username = authentication.getPrincipal().toString();

        Collection<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Instant now = Instant.now();
        long expire = 15L; 

        JwtClaimsSet claims = JwtClaimsSet
        .builder()
            .issuer("localhost")
            // .is
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expire))
            .claim("scope", authorities)
            .subject(username)
        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
