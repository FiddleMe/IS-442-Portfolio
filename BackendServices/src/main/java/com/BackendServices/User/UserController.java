package com.BackendServices.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import com.BackendServices.common.ApiResponse;
import com.BackendServices.entity.AccessLog;
import com.BackendServices.service.AccessLogService;
import com.BackendServices.entity.AccessLog;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final LoginOrRegistration loginOrRegistration;
    private final OtpService otpService;
    private final AccessLogService accessLogService;

    public UserController(UserService userService, LoginOrRegistration loginOrRegistration, OtpService otpService, AccessLogService accessLogService) {
        this.userService = userService;
        this.loginOrRegistration = loginOrRegistration;
        this.otpService = otpService;
        this.accessLogService = accessLogService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            AccessLog accessLog = new AccessLog();
            accessLog.setUserId(user.getUserId());
            accessLog.setAction("RETRIEVE USER");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), user, "User retrieved successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "User not found"));
        }
    }
    // @PostMapping 
    // public ResponseEntity<User> createUser(@RequestBody User user) {
    //     User createdUser = userService.createUser(user);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    // }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody EmailPasswordRequest loginRequest) {
        // Call the validateLogin method in LoginOrRegistration
        Optional<User> loginSuccess = loginOrRegistration.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());
        User userToShow = new User();
        User originalUser = loginSuccess.get();
        userToShow.setUserId(originalUser.getUserId());
        userToShow.setEmail(originalUser.getEmail());
        userToShow.setFirstName(originalUser.getFirstName());
        userToShow.setLastName(originalUser.getLastName());

        if (loginSuccess.isPresent()) {
            AccessLog accessLog = new AccessLog();
            accessLog.setUserId(originalUser.getUserId());
            accessLog.setAction("USER LOGIN");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), userToShow, "Login successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), null, "Login failed"));
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        // Call the registerUser method in LoginOrRegistration
        User createdUser = loginOrRegistration.registerUser(
            user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()
        );

        if (createdUser != null) {
            AccessLog accessLog = new AccessLog();
            accessLog.setUserId(createdUser.getUserId());
            accessLog.setAction("USER CREATED");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(), createdUser, "User created successfully"));
        } else {
            if (loginOrRegistration.isEmailInUse(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "Email is already in use. Please try another email."));
            } else if (!loginOrRegistration.isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "Invalid email format."));
            } else if (!loginOrRegistration.isValidPassword(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "Invalid password format. Password must be at least 8 characters long, have at least 1 uppercase letter, and include 1 number."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "Failed to create user for an unknown reason."));
            }
        }
    }


    // Test: POST http://localhost:8082/api/users, body: {
    // "email": "newuser@example.com",
    // "password": "asfasfasf",
    // "firstName": "John",
    // "lastName": "Doe"
    // }
    @PutMapping("/updatePassword")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody EmailPasswordRequest updatePasswordRequest) {
        // Call the changePassword method in LoginOrRegistration
        boolean passwordUpdated = loginOrRegistration.changePassword(updatePasswordRequest.getEmail(), updatePasswordRequest.getPassword());

        if (passwordUpdated) {
        // Fetch the user by email
        Optional<User> userOptional = userService.getUserByEmail(updatePasswordRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            AccessLog accessLog = new AccessLog();
            accessLog.setUserId(user.getUserId());
            accessLog.setAction("USER UPDATE PASSWORD");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
        }
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Password updated successfully", "Password updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "Email is not associated with a user or failed to update password"));
        }
    }

    @PostMapping("/generateOTP")
    public ResponseEntity<ApiResponse> generateOTP(@RequestParam String email) {
        // Call the generateOTP method in OtpService
        otpService.generateOTP(email);
        AccessLog accessLog = new AccessLog();
        accessLog.setUserId(email);
        accessLog.setAction("USER GENERATE OTP");
        accessLog.setTimestamp(LocalDateTime.now());
        accessLogService.createAccessLog(accessLog);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "OTP generated and sent to your email", "OTP generated and sent to your email"));
    }

    @PostMapping("/validateOTP")
    public ResponseEntity<ApiResponse> validateOTP(@RequestParam String email, @RequestParam String otp) {
        // Call the validateOTP method in OtpService
        boolean isOTPValid = otpService.validateOTP(email, otp);
        AccessLog accessLog = new AccessLog();

        if (isOTPValid) {
            accessLog.setUserId(email);
            accessLog.setAction("USER VALIDATE OTP");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "OTP is valid", "OTP is valid"));
        } else {
            accessLog.setUserId(email);
            accessLog.setAction("USER USES INVALID OTP");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "OTP is invalid or has expired"));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        AccessLog accessLog = new AccessLog();
        if (user != null) {
            accessLog.setUserId(userId);
            accessLog.setAction("USER UPDATE");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), user, "User updated successfully"));
        } else {
            accessLog.setUserId(userId);
            accessLog.setAction("USER UPDATE FAILED");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "User not found"));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        boolean deleted = userService.deleteUser(userId);
        AccessLog accessLog = new AccessLog();
        if (deleted) {
            accessLog.setUserId(userId);
            accessLog.setAction("USER DELETED");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.noContent().build();
        } else {
            accessLog.setUserId(userId);
            accessLog.setAction("USER DELETE FAILED");
            accessLog.setTimestamp(LocalDateTime.now());
            accessLogService.createAccessLog(accessLog);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "User not found"));
        }
    }
}