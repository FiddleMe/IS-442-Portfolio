package com.BackendServices.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackendServices.entity.User;
import com.BackendServices.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @PostMapping 
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    //Test: POST http://localhost:8082/api/users, body:  {
    //     "userId": "new_user_id",
    //     "email": "newuser@example.com",
    //     "password": "asfasfasf",
    //     "firstName": "John",
    //     "lastName": "Doe"
    // }

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