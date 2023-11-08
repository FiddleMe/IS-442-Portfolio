package com.BackendServices.Logs;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Logs")
public class Logs {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "LogID", length = 36, nullable = false)
    private UUID logId;  // Change data type to UUID

    @Column(name = "UserID", length = 36, nullable = false)
    private String userId;

    @Column(name = "Action", length = 50)
    private String action;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    // Getters and setters
    public UUID getLogId() {
      return logId;
    }

    public void setLogId(UUID logId) {
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

