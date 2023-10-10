package com.BackendServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackendServices.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    // You can add custom queries or methods here if needed
    User findByEmail(String email);
}
