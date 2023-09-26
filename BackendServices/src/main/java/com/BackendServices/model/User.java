package com.BackendServices.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "UserId", length = 36, nullable = false)
    private String userId;

    @Column(name = "Email", length = 36, nullable = false)
    private String email;

    @Column(name = "Password", length = 36, nullable = false)
    private String password;

    @Column(name = "First_Name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "Last_Name", length = 50, nullable = false)
    private String lastName;

    // Getters and setters
}
