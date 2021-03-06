package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.user.UserEmailRepresentation;

public class ChangeEmailAddressCommand {

    private String tenantId;
    private String username;
    private String emailAddress;

    public ChangeEmailAddressCommand(String tenantId, String username, String emailAddress) {
        super();

        this.tenantId = tenantId;
        this.username = username;
        this.emailAddress = emailAddress;
    }

    public ChangeEmailAddressCommand() {
        super();
    }

    public ChangeEmailAddressCommand(String tenantId, String username, UserEmailRepresentation userEmailRepresentation) {

        this.initialiazeFrom(tenantId, username, userEmailRepresentation);
    }

    private void initialiazeFrom(String tenantId, String username, UserEmailRepresentation userEmailRepresentation) {

        this.tenantId = tenantId;
        this.username = username;
        this.emailAddress = userEmailRepresentation.getEmailAddress();
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

