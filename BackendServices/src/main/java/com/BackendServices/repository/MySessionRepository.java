
package com.BackendServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackendServices.entity.MySession;

public interface MySessionRepository extends JpaRepository<MySession, String> {
    // You can add custom queries or methods here if needed
}
