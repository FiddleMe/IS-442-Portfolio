package com.BackendServices.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.*;

import com.BackendServices.entity.User;

@Service
public class LoginOrRegistration {

    private final UserService userService;

    @Autowired
    public LoginOrRegistration(UserService userService) {
        this.userService = userService;
    }

    public boolean validateLogin(String email, String password) {
        // Get all users from the UserService
        Optional<User> userOptional = userService.getUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Compare the hashed entered password with the stored hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return true; // Passwords match, login successful
            }
            else
                return false;
        }

        return false; // Email is not associated with a user or passwords do not match
    }

    public User registerUser(String firstName, String lastName, String email, String password) {
        // Check if the email is valid
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format");
            return null;
        }
    
        // Get all users from the UserService
        List<User> users = userService.getAllUsers();
    
        // Check if the email is already in use
        boolean emailInUse = users.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    
        if (emailInUse) {
            System.out.println("Email already used, please try another Email");
            return null;
        }
    
        // Generate a unique user ID
        String userId = generateUniqueUserId();
    
        // Hash the user's password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
    
        // Create a new user object with the hashed password
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword); // Store the hashed password
    
        // Use the createUser method in UserService
        return userService.createUser(newUser);
    }

    public boolean changePassword(String email, String newPassword) {
        // Check if the email is associated with a user
        Optional<User> userOptional = userService.getUserByEmail(email);
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Hash the new password
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedNewPassword = passwordEncoder.encode(newPassword);
    
            // Update the user's password with the hashed password
            user.setPassword(hashedNewPassword);
            
            // Save the updated user to the database
            userService.updateUser(user.getUserId(), user);
    
            return true; // Password updated successfully
        } else {
            // Email is not associated with a user
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    private String generateUniqueUserId() {
        // Generate a unique user ID (e.g., using UUID)
        return UUID.randomUUID().toString();
    }
}
