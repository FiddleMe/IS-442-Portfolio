package com.BackendServices.Email.dto;
import org.springframework.stereotype.Component;

import com.BackendServices.Email.Email;

@Component
public class EmailMapper {
    
    public Email convertToEntity (EmailDTO emailDto){
        Email email = new Email();
        email.setRecipient(emailDto.getRecipient());
        email.setSubject(emailDto.getSubject());
        email.setMsg(emailDto.getMsg());

        return email;

    }
    public EmailDTO convertToDTO (Email email){
        EmailDTO emailDto = new EmailDTO();
        emailDto.setRecipient(email.getRecipient());
        emailDto.setSubject(email.getSubject());
        emailDto.setMsg(email.getMsg());

        return emailDto;
    }
}
