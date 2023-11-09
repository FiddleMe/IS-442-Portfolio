package com.BackendServices.Logs;
import org.springframework.data.jpa.repository.JpaRepository;



public interface LogsRepository extends JpaRepository<Logs, Long> {
    // You can add custom queries or methods here if needed
}
