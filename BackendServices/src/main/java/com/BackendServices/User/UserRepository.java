package com.BackendServices.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // You can add custom queries or methods here if needed
    User findByEmail(String email);
}
