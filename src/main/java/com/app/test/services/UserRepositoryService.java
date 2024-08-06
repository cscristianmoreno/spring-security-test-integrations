package com.app.test.services;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.test.entity.Users;
import com.app.test.interfaces.IUserRepositoryService;
import com.app.test.repository.UserRepository;

@Service
public class UserRepositoryService implements IUserRepositoryService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users save(Users users) {
        String password = users.getPassword();
        users.setPassword(passwordEncoder.encode(password));
        return userRepository.save(users);
    }

    @Override
    public Optional<Users> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    
}
