package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.InvitationDescriptor;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;

public class RegistrationInvitationRepresentation extends ResourceSupport {


    private String description;
    private String invitationId;
    private ZonedDateTime startingOn;
    private String tenantId;
    private ZonedDateTime until;


    public RegistrationInvitationRepresentation() {
        super();
    }

    public RegistrationInvitationRepresentation(String description, String invitationId, ZonedDateTime startingOn,
                                                String tenantId, ZonedDateTime until) {
        this.description = description;
        this.invitationId = invitationId;
        this.startingOn = startingOn;
        this.tenantId = tenantId;
        this.until = until;
    }

    public RegistrationInvitationRepresentation(InvitationDescriptor invitationDescriptor) {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
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
