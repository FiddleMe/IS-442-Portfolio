package com.BackendServices.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.BackendServices.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    // You can add custom queries or methods here if needed
}
