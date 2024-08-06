package com.app.test.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    void encode() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            "user",
            "user",
            AuthorityUtils.createAuthorityList("SAVE", "UPDATE")
        );

        String token = tokenService.encode(authentication);
        
        assertNotNull(token);
    }

    // private JwtClaimsSet getClaims() {
    //     Instant instant = Instant.now();
    //     long expire = 15L;

    //     return JwtClaimsSet
    //     .builder()
    //         .issuer("localhost")
    //         .issuedAt(instant)
    //         .expiresAt(instant.plusSeconds(expire))
    //         .subject("user")
    //         .claim("scope", "")
    //     .build();
    // }
}
