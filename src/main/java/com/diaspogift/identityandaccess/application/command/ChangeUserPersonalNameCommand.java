package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.user.UserPersonalNameRepresentation;

public class ChangeUserPersonalNameCommand {
    private String tenantId;
    private String username;
    private String firstName;
    private String lastName;

    public ChangeUserPersonalNameCommand(
            String tenantId, String username,
            String aFirstName, String aLastName) {

        super();

        this.tenantId = tenantId;
        this.username = username;
        this.firstName = aFirstName;
        this.lastName = aLastName;
    }

    public ChangeUserPersonalNameCommand() {
        super();
    }

    public ChangeUserPersonalNameCommand(String aTenantId, String aUsername, UserPersonalNameRepresentation userPersonalNameRepresentation) {

        this.initialyzeFrom(aTenantId, aUsername, userPersonalNameRepresentation);
    }

    private void initialyzeFrom(String aTenantId, String aUsername, UserPersonalNameRepresentation userPersonalNameRepresentation) {

        this.tenantId = aTenantId;
        this.username = aUsername;
        this.firstName = userPersonalNameRepresentation.getFirstName();
        this.lastName = userPersonalNameRepresentation.getLastName();
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

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

