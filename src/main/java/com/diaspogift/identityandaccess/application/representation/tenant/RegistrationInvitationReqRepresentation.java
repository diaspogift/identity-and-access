package com.diaspogift.identityandaccess.application.representation.tenant;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

public class RegistrationInvitationReqRepresentation extends ResourceSupport {


    @NotEmpty
    private String description;
    @NotEmpty
    private String startingOn;
    @NotEmpty
    private String until;


    public RegistrationInvitationReqRepresentation() {
        super();
    }

    public RegistrationInvitationReqRepresentation(String description, String startingOn, String until) {
        this.description = description;
        this.startingOn = startingOn;
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
                ", startingOn='" + startingOn + '\'' +
                ", until='" + until + '\'' +
                '}';
    }
}
