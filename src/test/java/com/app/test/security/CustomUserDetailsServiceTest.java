package com.app.test.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.test.entity.Users;
import com.app.test.roles.Roles;
import com.app.test.services.UserRepositoryService;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private Users users;

    @BeforeEach
    void setup() {
        users = new Users();
        users.setUsername("user");
        users.setPassword("user");
        users.setAuthorities(Roles.UPDATE, Roles.SAVE);
        // userRepositoryService.save(users);
    }

    @Test
    void loadUserByUsername() {
        userRepositoryService.save(users);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(users.getUsername());

        assertNotNull(userDetails);
        assertEquals(users.getUsername(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(users.getUsername());
        });
    }
}
