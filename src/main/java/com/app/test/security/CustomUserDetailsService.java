package com.app.test.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.test.entity.Users;
import com.app.test.services.UserRepositoryService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryService userRepositoryService;

    public CustomUserDetailsService(final UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> result = userRepositoryService.findByUsername(username);
        
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new CustomUserDetails(result.get());
    }
    
}
