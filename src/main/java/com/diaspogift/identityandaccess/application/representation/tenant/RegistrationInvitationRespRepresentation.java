package com.diaspogift.identityandaccess.application.representation.tenant;

import com.diaspogift.identityandaccess.domain.model.identity.InvitationDescriptor;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;

public class RegistrationInvitationRespRepresentation extends ResourceSupport {


    private String description;
    private String invitationId;
    private String startingOn;
    private String until;


    public RegistrationInvitationRespRepresentation() {
        super();
    }

    public RegistrationInvitationRespRepresentation(String description, String invitationId, ZonedDateTime startingOn, ZonedDateTime until) {
        this.description = description;
        this.invitationId = invitationId;
        this.startingOn = startingOn.toString();
        this.until = until.toString();
    }

    public RegistrationInvitationRespRepresentation(InvitationDescriptor invitationDescriptor) {

        this.description = invitationDescriptor.description();
        this.invitationId = invitationDescriptor.invitationId();
        this.startingOn = invitationDescriptor.startingOn().toString();
        this.until = invitationDescriptor.until().toString();
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

    public String getStartingOn() {
        return startingOn;
    }

    public void setStartingOn(String startingOn) {
        this.startingOn = startingOn;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }


    @Override
    public String toString() {
        return "RegistrationInvitationRespRepresentation{" +
                "description='" + description + '\'' +
                ", invitationId='" + invitationId + '\'' +
                ", startingOn='" + startingOn + '\'' +
                ", until='" + until + '\'' +
                '}';
    }
}
