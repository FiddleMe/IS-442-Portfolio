package com.BackendServices.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackendServices.entity.Email;
import com.BackendServices.service.EmailService;


@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<String>sendEmail(@RequestBody Email email){
        try {
            emailService.sendEmail(email);
            return ResponseEntity.ok("Email sent successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
}
