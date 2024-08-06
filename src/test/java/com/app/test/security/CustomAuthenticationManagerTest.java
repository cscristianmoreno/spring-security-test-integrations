package com.app.test.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.app.test.entity.Users;
import com.app.test.roles.Roles;
import com.app.test.services.UserRepositoryService;

@SpringBootTest
public class CustomAuthenticationManagerTest {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired 
    private UserRepositoryService userRepositoryService;
    
    private Users users;

    @BeforeEach
    void setup() {
        users = new Users();
        users.setUsername("user");
        users.setPassword("user");
        users.setAuthorities(Roles.SAVE, Roles.UPDATE);
        
        userRepositoryService.save(users);
    }

    @Test
    void authenticate() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "user",
            "user"
        );

        Authentication authentication = customAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
        assertNotNull(authentication);
    }
}
