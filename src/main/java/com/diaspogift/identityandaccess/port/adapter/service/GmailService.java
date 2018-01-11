package com.diaspogift.identityandaccess.port.adapter.service;

import com.diaspogift.identityandaccess.domain.model.identity.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class GmailService implements EmailService {


    @Autowired
    public JavaMailSender javaMailSender;


    public void sendEmail(String to, String from, String aSubject, String aMessage) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(aSubject);
        message.setText(aMessage);
        javaMailSender.send(message);
    }
}
