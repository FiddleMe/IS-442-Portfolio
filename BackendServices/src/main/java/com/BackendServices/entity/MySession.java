package com.BackendServices.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MySession")
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getLoginDatetime() {
        return loginDatetime;
    }

    public void setLoginDatetime(LocalDateTime loginDatetime) {
        this.loginDatetime = loginDatetime;
    }

    public LocalDateTime getLogoutDatetime() {
        return logoutDatetime;
    }

    public void setLogoutDatetime(LocalDateTime logoutDatetime) {
        this.logoutDatetime = logoutDatetime;
    }
}