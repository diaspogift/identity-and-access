package com.diaspogift.identityandaccess.application.representation;

import org.springframework.hateoas.ResourceSupport;

public class UserEmailRepresentation extends ResourceSupport{

    private String tenantId;
    private String username;
    private String emailAddress;

    public UserEmailRepresentation() {
        super();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
