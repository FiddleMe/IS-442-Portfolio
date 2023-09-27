package com.BackendServices.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AccessLog")
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogID", nullable = false)
    private Long logId;

    @Column(name = "UserID", length = 36, nullable = false)
    private String userId;

    @Column(name = "Action", length = 50)
    private String action;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    // Getters and setters
    public Long getLogId() {
      return logId;
    }

    public void setLogId(Long logId) {
      this.logId = logId;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getAction() {
      return action;
    }

    public void setAction(String action) {
      this.action = action;
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
    }

    
    
}

