package com.BackendServices.Email;
import org.springframework.stereotype.Service;

import com.BackendServices.Email.exception.EmailException;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


@Service
public class EmailService {
    

    public void sendEmail(Email email){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Email credentials
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("gigsterjob@gmail.com", "towlwhajswsrmubo");
            }
        });

        try {
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress("gigsterjob@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
            msg.setSubject(email.getSubject());
            msg.setText(email.getMsg());

            Transport.send(msg);
        }
        catch (AuthenticationFailedException e){
            throw new EmailException("Authentication fail. Please check your credentials.", e);
        }
        catch (AddressException e){
            throw new EmailException(e.getMessage(), e);
        }
        catch(MessagingException  e){
            throw new EmailException("Error occurred while sending the email", e);
        }
    }
}
