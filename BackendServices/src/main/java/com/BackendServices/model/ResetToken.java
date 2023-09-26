package com.BackendServices.model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ResetToken")
public class ResetToken {
    @Id
    @Column(name = "TokenID", length = 36, nullable = false)
    private String tokenId;

    @Column(name = "UserID", length = 36, nullable = false)
    private String userId;

    @Column(name = "Expiry_Datetime", nullable = false)
    private LocalDateTime expiryDatetime;

    // Getters and setters
}
