package com.app.test.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.test.dto.LoginDTO;
import com.app.test.interfaces.IUserAuthenticationService;
import com.app.test.security.CustomAuthenticationManager;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final TokenService tokenService;

    public UserAuthenticationService(final CustomAuthenticationManager customAuthenticationManager, final TokenService tokenService) {
        this.customAuthenticationManager = customAuthenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            username, 
            password
        );

        Authentication authentication = customAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.encode(authentication);
        return token;
    }
    
}
