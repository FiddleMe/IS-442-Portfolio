package com.BackendServices.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Session")
public class MySession {
    @Id
    @Column(name = "SessionID", length = 36, nullable = false)
    private String sessionId;

    @Column(name = "UserID", length = 36, nullable = false)
    private String userId;

    @Column(name = "Login_Datetime", nullable = false)
    private LocalDateTime loginDatetime;

    @Column(name = "Logout_Datetime")
    private LocalDateTime logoutDatetime;

    // Getters and setters
}
