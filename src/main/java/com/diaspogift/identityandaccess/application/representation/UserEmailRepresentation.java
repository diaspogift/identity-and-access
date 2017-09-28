package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.EmailAddress;
import org.springframework.hateoas.ResourceSupport;

public class UserEmailRepresentation extends ResourceSupport {

    private String emailAddress;

    public UserEmailRepresentation() {
        super();
    }

    public UserEmailRepresentation(EmailAddress emailAddress) {

        this.emailAddress = emailAddress.address();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
