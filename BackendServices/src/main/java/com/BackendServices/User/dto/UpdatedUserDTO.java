package com.BackendServices.User.dto;

import com.BackendServices.User.User;

import java.util.Optional;

public class UpdatedUserDTO extends UserDTO {
    private Optional<String> email;
    private Optional<String> firstName;
    private Optional<String> lastName;

    public UpdatedUserDTO() {
        // Initialize Optional fields as empty Optionals by default
        super();
        email = Optional.empty();
        firstName = Optional.empty();
        lastName = Optional.empty();
    }

    // Constructor with Optional fields
    public UpdatedUserDTO(Optional<String> email, Optional<String> firstName, Optional<String> lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter and setter for email
    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    // Getter and setter for firstName
    public Optional<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Optional<String> firstName) {
        this.firstName = firstName;
    }

    // Getter and setter for lastName
    public Optional<String> getLastName() {
        return lastName;
    }

    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName;
    }

    public User toEntity() {
        User user = new User();

        if (email.isPresent()) {
            user.setEmail(email.get());
        }
        if (firstName.isPresent()) {
            user.setFirstName(firstName.get());
        }
        if (lastName.isPresent()) {
            user.setLastName(lastName.get());
        }

        return user;
    }
}
