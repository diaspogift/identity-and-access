package com.diaspogift.identityandaccess.application.command;

public class OfferRegistrationInvitationCommand {


    private String description;
    private String startingOn;
    private String tenantId;
    private String until;


    public OfferRegistrationInvitationCommand(String description, String startingOn, String tenantId, String until) {
        this.description = description;
        this.startingOn = startingOn;
        this.tenantId = tenantId;
        this.until = until;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartingOn() {
        return startingOn;
    }

    public void setStartingOn(String startingOn) {
        this.startingOn = startingOn;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }
}
