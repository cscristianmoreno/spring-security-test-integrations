package com.app.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.test.dto.LoginDTO;
import com.app.test.entity.Users;
import com.app.test.security.CustomAuthenticationManager;
import com.app.test.services.TokenService;
import com.app.test.services.UserAuthenticationService;
import com.app.test.services.UserRepositoryService;

import jakarta.annotation.security.PermitAll;

@Controller
@ResponseBody
@RequestMapping("/auth")
public class AuthController {
    private final UserRepositoryService userRepositoryService;
    private final UserAuthenticationService userAuthenticationService;

    public AuthController(final UserRepositoryService userRepositoryService, final UserAuthenticationService userAuthenticationService) {
        this.userRepositoryService = userRepositoryService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = userAuthenticationService.login(loginDTO);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(token, HttpStatus.OK);
        return responseEntity;
    }
    
    @PostMapping("/register")
    public Users register(@RequestBody Users users) {
        return userRepositoryService.save(users);
    }
}
