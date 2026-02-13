package com.example.login.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.login.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
