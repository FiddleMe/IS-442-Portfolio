package com.BackendServices.User;

import org.springframework.stereotype.Service;
import com.BackendServices.User.exception.UserException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            throw new UserException("Failed to retrieve all users", e);
        }
    }

    public Optional<User> getUserById(String userId) {
        try {
            return userRepository.findById(userId);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            throw new UserException("Failed to retrieve user by ID", e);
        }
    }

    public Optional<User> getUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            throw new UserException("Failed to retrieve user by email", e);
        }
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            throw new UserException("Failed to create user", e);
        }
    }

    public User updateUser(String userId, User updatedUser) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // Update user properties as needed
                user.setEmail(updatedUser.getEmail());
                user.setFirstName(updatedUser.getFirstName());
                user.setLastName(updatedUser.getLastName());
                // Save and return the updated user
                return userRepository.save(user);
            }
            return null; // User not found
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            throw new UserException("Failed to update user", e);
        }
    }

    public boolean deleteUser(String userId) {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return true;
            }
            return false; // User not found
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            throw new UserException("Failed to delete user", e);
        }
    }
}
