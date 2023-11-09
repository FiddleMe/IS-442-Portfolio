package com.BackendServices.User;

import java.util.List;

import com.BackendServices.Portfolio.Portfolio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "UserId", length = 36, nullable = false)
    private String userId;

    @Column(name = "Email", length = 36, nullable = false)
    private String email;

    @Column(name = "Password", length = 72, nullable = false)
    private String password;

    @Column(name = "First_Name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "Last_Name", length = 50, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Portfolio> portfolios;
    

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }


    // Getters and setters
    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

}
