package com.example.demo.service;


import java.util.Properties;


import javax.mail.Message;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;


import org.springframework.stereotype.Service;

@Service
public class EmailService {

public boolean sendEmail(String subject, String message, String to) {
    	
    	boolean f=false;
    	
        String from = "dheerajtechno42@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); 
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("dheerajtechno42@gmail.com", "qcrxrhwvmzehengx");
            }
        });
        session.setDebug(true);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
//            mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");

            Transport.send(mimeMessage);
            
            f=true;
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
