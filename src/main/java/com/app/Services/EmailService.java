package com.app.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService
{

    @Autowired
    private JavaMailSender mailSender;

    private String baseUrl = "http://localhost:8080/";

    @Value("${app.mail.sender}")
    private String sender;


    public void sendEmail(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email,
            String token)
            throws MessagingException
    {
        final Map<String, String> map = prepareBodyAndSubject(firstName, lastName, token);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(sender);
        helper.setTo(email);
        helper.setSubject(map.get("subject"));
        helper.setText(map.get("body"), true);
        mailSender.send(mimeMessage);
    }

    private Map<String,String> prepareBodyAndSubject(@NotBlank String firstName, @NotBlank String lastName, String token)
    {

        final String subject =  String.format("Hello %s %s from thePeoplesPoll... Please verify your email", firstName ,lastName) ;
        final String template = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n" + "<meta charset=\"UTF-8\">\n"
                + "<title>Find Your Vibe..Email Verification</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<p align=\"center\"> Please click below button to verify your email and confirm your registration.\n"
                + "</p>\n"
                + "<a href=\"{0}api/internal/verify_email?token={1}\">Verify Email</a>\n"
                + "</body>\n"
                + "</html>";
        final String body = MessageFormat.format(template, baseUrl, token);
        Map<String, String> map = new HashMap<>();
        map.put("subject", subject);
        map.put("body", body);
        return map;
    }
}
