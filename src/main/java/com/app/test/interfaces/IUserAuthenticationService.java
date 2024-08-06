package com.app.test.interfaces;

import com.app.test.dto.LoginDTO;

public interface IUserAuthenticationService {
    String login(LoginDTO loginDTO);
}
