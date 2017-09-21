package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.UserDescriptor;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

public class UserDescriptorRepresentation extends ResourceSupport implements Serializable {

    private String tenantId;
    private String username;
    private String emailAddress;

    public UserDescriptorRepresentation() {
        super();
    }

    public UserDescriptorRepresentation(UserDescriptor aUserDescriptor) {

        this.intialyzeFrom(aUserDescriptor);
    }

    private void intialyzeFrom(UserDescriptor aUserDescriptor) {

        this.tenantId = aUserDescriptor.tenantId().id();
        this.username = aUserDescriptor.username();
        this.emailAddress = aUserDescriptor.emailAddress();
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
