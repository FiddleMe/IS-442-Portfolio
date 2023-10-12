package com.BackendServices.service.User;

import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import com.BackendServices.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final UserService userService;

    public OtpService(UserService userService) {
        this.userService = userService;
    }

    public void generateOTP(String email) {
        // Check if the email is associated with a user
        Optional<User> userOptional = userService.getUserByEmail(email);

        if (userOptional.isPresent()) {

            // Generate a random OTP (6 characters in this example)
            String otp = RandomStringUtils.randomNumeric(6);

            // Store the OTP temporarily
            otpStore.put(email, otp);

            // Send the OTP to the user's email
            sendOTPByEmail(email, otp);
        } else {
            // Email is not associated with a user; you can handle this case accordingly
            System.out.println("Email is not associated with any user.");
        }
    }

    public boolean validateOTP(String email, String otpNum) {
        // Retrieve the stored OTP for the email
        String storedOTP = otpStore.get(email);

        if (storedOTP != null && storedOTP.equals(otpNum)) {
            // OTP matches, remove it from the store
            otpStore.remove(email);
            return true;
        }

        return false; // OTP does not match or has expired
    }

    private void sendOTPByEmail(String email, String otp) {
        // Configure email sending properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "your-smtp-host");
        props.put("mail.smtp.port", "your-smtp-port");
        props.put("mail.smtp.auth", "true");

        // Set up a Session with your email credentials
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@gmail.com", "your-email-password");
            }
        });

        try {
            // Create a MimeMessage
            Message message = new MimeMessage(session);

            // Set the sender and recipient email addresses
            message.setFrom(new InternetAddress("your-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set the email subject and content
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            // Send the email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle email sending errors here
        }
    }
}
