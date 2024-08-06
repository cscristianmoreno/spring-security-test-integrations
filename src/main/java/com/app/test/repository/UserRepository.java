package com.app.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.app.test.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findById(int id);

    Optional<Users> findByUsername(String username);
}
