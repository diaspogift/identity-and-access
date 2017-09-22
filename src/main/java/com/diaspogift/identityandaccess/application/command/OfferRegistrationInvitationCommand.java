package com.diaspogift.identityandaccess.application.command;

import java.time.ZonedDateTime;

public class OfferRegistrationInvitationCommand {


    private String description;
    private ZonedDateTime startingOn;
    private String tenantId;
    private ZonedDateTime until;


    public OfferRegistrationInvitationCommand(String description, ZonedDateTime startingOn, String tenantId, ZonedDateTime until) {
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

    public ZonedDateTime getStartingOn() {
        return startingOn;
    }

    public void setStartingOn(ZonedDateTime startingOn) {
        this.startingOn = startingOn;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ZonedDateTime getUntil() {
        return until;
    }

    public void setUntil(ZonedDateTime until) {
        this.until = until;
    }
}
