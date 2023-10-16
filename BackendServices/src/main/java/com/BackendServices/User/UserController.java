package com.BackendServices.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final LoginOrRegistration loginOrRegistration;
    private final OtpService otpService;

    public UserController(UserService userService, LoginOrRegistration loginOrRegistration, OtpService otpService) {
        this.userService = userService;
        this.loginOrRegistration = loginOrRegistration;
        this.otpService = otpService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // @PostMapping 
    // public ResponseEntity<User> createUser(@RequestBody User user) {
    //     User createdUser = userService.createUser(user);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    // }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody EmailPasswordRequest loginRequest) {
        // Call the validateLogin method in LoginOrRegistration
        boolean loginSuccess = loginOrRegistration.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());

        if (loginSuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Call the registerUser method in LoginOrRegistration
        User createdUser = loginOrRegistration.registerUser(
            user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()
        );

        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    // Test: POST http://localhost:8082/api/users, body: {
    // "email": "newuser@example.com",
    // "password": "asfasfasf",
    // "firstName": "John",
    // "lastName": "Doe"
    // }
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody EmailPasswordRequest updatePasswordRequest) {
        // Call the changePassword method in LoginOrRegistration
        boolean passwordUpdated = loginOrRegistration.changePassword(updatePasswordRequest.getEmail(), updatePasswordRequest.getPassword());

        if (passwordUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not associated with a user or failed to update password");
        }
    }

    @PostMapping("/generateOTP")
    public ResponseEntity<String> generateOTP(@RequestParam String email) {
        // Call the generateOTP method in OtpService
        otpService.generateOTP(email);

        return ResponseEntity.status(HttpStatus.OK).body("OTP generated and sent to your email");
    }

    @PostMapping("/validateOTP")
    public ResponseEntity<String> validateOTP(@RequestParam String email, @RequestParam String otp) {
        // Call the validateOTP method in OtpService
        boolean isOTPValid = otpService.validateOTP(email, otp);

        if (isOTPValid) {
            return ResponseEntity.status(HttpStatus.OK).body("OTP is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is invalid or has expired");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}