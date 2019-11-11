package com.diaspogift.identityandaccess.application.command;

public class AuthenticateUserCommand {
    private String tenantId;
    private String username;
    private String password;

    public AuthenticateUserCommand(String tenantId, String aUsername, String aPassword) {
        super();

        this.tenantId = tenantId;
        this.username = aUsername;
        this.password = aPassword;


        System.out.println("\n\n tenantId in AuthenticateUserCommand = " + tenantId);
        System.out.println("\n\n username in AuthenticateUserCommand = " + username);
        System.out.println("\n\n password in AuthenticateUserCommand = " + password);
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

