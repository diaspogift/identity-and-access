package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.user.UserChangedPasswordRepresentation;

public class ChangeUserPasswordCommand {
    private String tenantId;
    private String username;
    private String currentPassword;
    private String changedPassword;

    public ChangeUserPasswordCommand(String tenantId, String aUsername,
                                     String aCurrentPassword, String aChangedPassword) {

        super();

        this.tenantId = tenantId;
        this.username = aUsername;
        this.currentPassword = aCurrentPassword;
        this.changedPassword = aChangedPassword;
    }

    public ChangeUserPasswordCommand(String aTenantId, String username, UserChangedPasswordRepresentation userChangedPasswordRepresentation) {


        this.initialiazeFrom(aTenantId, username, userChangedPasswordRepresentation);
    }

    private void initialiazeFrom(String aTenantId, String username, UserChangedPasswordRepresentation userChangedPasswordRepresentation) {

        this.tenantId = aTenantId;
        this.username = username;
        this.changedPassword = userChangedPasswordRepresentation.getChangedPassword();
        this.currentPassword = userChangedPasswordRepresentation.getCurrentPassword();
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

    public String getCurrentPassword() {
        return this.currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getChangedPassword() {
        return this.changedPassword;
    }

    public void setChangedPassword(String changedPassword) {
        this.changedPassword = changedPassword;
    }
}

