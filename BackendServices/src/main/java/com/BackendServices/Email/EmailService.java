package com.BackendServices.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Email email){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.getRecipient());
        mailMessage.setFrom(email.getSender());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getMsg());

        javaMailSender.send(mailMessage);
    }
}
