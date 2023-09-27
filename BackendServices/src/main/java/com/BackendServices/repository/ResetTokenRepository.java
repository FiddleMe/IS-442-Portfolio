package com.BackendServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackendServices.entity.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken, String> {
    // You can add custom queries or methods here if needed
}
