package com.BackendServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackendServices.entity.Email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Email emailNoti){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailNoti.getRecipient());
        mailMessage.setFrom(emailNoti.getSender());
        mailMessage.setSubject(emailNoti.getSubject());
        mailMessage.setText(emailNoti.getMsg());

        javaMailSender.send(mailMessage);

    }

}
