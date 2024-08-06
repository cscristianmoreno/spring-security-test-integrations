package com.app.test.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.test.entity.Users;
import com.app.test.repository.UserRepository;
import com.app.test.roles.Roles;
import com.app.test.services.UserRepositoryService;

@SpringBootTest
public class CustomAuthenticationProviderTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepositoryService userRepositoryService;

    private Users users;

    @BeforeEach
    void setup() {
        users = new Users();
        users.setUsername("user");
        users.setPassword("user");
        users.setAuthorities(Roles.values());
        userRepositoryService.save(users);
    }

    @Test
    void authenticate() {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(users.getUsername());
        boolean passwordMatched = passwordEncoder.matches("user", userDetails.getPassword());

        assertNotNull(userDetails.getPassword().toString());
        assertTrue(passwordMatched);
    }
}
