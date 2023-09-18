package com.cardManagement.cardmanagementapp.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.entities.Statement;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private PdfGenerationService pdfGenerationService;
	
	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(message,true);
		javaMailSender.send(msg);
	}
	
	public void sendPdfByEmail(List<Statement> statement, String outputPath,String recepientEmail) {
		
//		String recipientEmail = user.getEmail();
        // Step 1: Set up mail properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com."); // Replace with your SMTP server
        properties.put("mail.smtp.port", "587"); // Replace with the SMTP server port
        properties.put("mail.smtp.auth", "true"); // Enable SMTP authentication
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Step 2: Create a session with the email server
       
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aaj.anushka@gmail.com", "dbbwxuljprkadjtx"); // Replace with your email credentials
            }
        });


        try {
            // Step 3: Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aaj.anushka@gmail.com")); // Replace with your email address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("hetalparmar1409@gmail.com")); // Set recipient email address
            message.setSubject("PDF Statement");
            message.setText("Please find the PDF statement attached.");

            // Step 4: Create and attach the PDF file
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile(new java.io.File(outputPath)); // Attach the generated PDF
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(pdfAttachment);
            message.setContent(multipart);

            // Step 5: Send the message
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
	}
}


