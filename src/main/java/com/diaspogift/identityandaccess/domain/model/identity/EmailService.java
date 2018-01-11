package com.diaspogift.identityandaccess.domain.model.identity;

public interface EmailService {

    public void sendEmail(String to, String from, String aSubject, String aMessage);
}
