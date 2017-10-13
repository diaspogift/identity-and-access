package com.diaspogift.identityandaccess.application.representation.user;

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

    public UserEmailRepresentation(String anEmailAddress) {

        this.emailAddress = anEmailAddress;

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
