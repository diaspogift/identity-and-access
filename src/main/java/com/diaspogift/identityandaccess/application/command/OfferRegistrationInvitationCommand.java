package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.tenant.RegistrationInvitationReqRepresentation;

public class OfferRegistrationInvitationCommand {


    private String description;
    private String startingOn;
    private String tenantId;
    private String until;
    private String email;


    public OfferRegistrationInvitationCommand(String description, String startingOn, String tenantId, String until, String email) {
        this.description = description;
        this.startingOn = startingOn;
        this.tenantId = tenantId;
        this.until = until;
        this.email = email;
    }

    public OfferRegistrationInvitationCommand(String aTenantId, RegistrationInvitationReqRepresentation aRegistrationInvitationRespRepresentation) {

        this.description = aRegistrationInvitationRespRepresentation.getDescription();
        this.startingOn = aRegistrationInvitationRespRepresentation.getStartingOn();
        this.tenantId = aTenantId;
        this.until = aRegistrationInvitationRespRepresentation.getUntil();
        this.email = aRegistrationInvitationRespRepresentation.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

