package com.app.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.test.entity.Users;
import com.app.test.roles.Roles;
import com.app.test.services.UserRepositoryService;

@Controller
@ResponseBody
@RequestMapping("/users")
public class UserController {

    @PreAuthorize("hasAuthority('SAVE')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
