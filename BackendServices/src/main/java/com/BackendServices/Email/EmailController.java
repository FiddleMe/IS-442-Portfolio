package com.BackendServices.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackendServices.Email.dto.EmailDTO;
import com.BackendServices.Email.dto.EmailMapper;
import com.BackendServices.common.ApiResponse;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<?>sendEmail(@RequestBody EmailDTO emailDto){
        try {
            Email email = emailMapper.convertToEntity(emailDto);
            emailService.sendEmail(email);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), email, "Email sent successfully"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), emailDto, "Failed to sent Email"));
        }
    }
}
