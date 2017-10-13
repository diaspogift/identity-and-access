package com.diaspogift.identityandaccess.application.representation.user;

import org.springframework.hateoas.ResourceSupport;

public class UserChangedPasswordRepresentation extends ResourceSupport {

    private String currentPassword;
    private String changedPassword;

    public UserChangedPasswordRepresentation() {

        super();
    }

    public UserChangedPasswordRepresentation(String aCurrentPassword, String aChangedPassword) {

        this.currentPassword = aCurrentPassword;
        this.changedPassword = aChangedPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getChangedPassword() {
        return changedPassword;
    }

    public void setChangedPassword(String changedPassword) {
        this.changedPassword = changedPassword;
    }
}
