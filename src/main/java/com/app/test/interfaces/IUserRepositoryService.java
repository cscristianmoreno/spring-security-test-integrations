package com.app.test.interfaces;

import java.util.Optional;

import com.app.test.entity.Users;


public interface IUserRepositoryService {
    Users save(Users users);

    Optional<Users> findById(int id);

    Optional<Users> findByUsername(String username);
}
