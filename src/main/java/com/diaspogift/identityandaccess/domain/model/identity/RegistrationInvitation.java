package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;

import java.time.ZonedDateTime;


/**
 * Truly a value object need to be serialized and saved as it
 */

public class RegistrationInvitation extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    private String description;
    private String invitationId;
    private ZonedDateTime startingOn;
    private TenantId tenantId;
    private ZonedDateTime until;

    protected RegistrationInvitation(
            TenantId aTenantId,
            String anInvitationId,
            String aDescription) {

        this();

        this.setDescription(aDescription);
        this.setInvitationId(anInvitationId);
        this.setTenantId(aTenantId);

        this.assertValidInvitationDates();
    }

    protected RegistrationInvitation() {
        super();
    }

    public String description() {
        return this.description;
    }

    public String invitationId() {
        return this.invitationId;
    }

    public boolean isAvailable() {
        boolean isAvailable = false;
        if (this.startingOn() == null && this.until() == null) {
            isAvailable = true;
        } else {

            ZonedDateTime dateNow = ZonedDateTime.now();

            if (this.startingOn() == null) {
                isAvailable = false;
            } else if (this.until() == null) {
                isAvailable = dateNow.isAfter(this.startingOn());
            } else {
                isAvailable = dateNow.isAfter(this.startingOn()) && dateNow.isBefore(this.until());
            }


        }
        return isAvailable;
    }

    public boolean isIdentifiedBy(String anInvitationIdentifier) {
        boolean isIdentified = this.invitationId().equals(anInvitationIdentifier);
        if (!isIdentified && this.description() != null) {
            isIdentified = this.description().equals(anInvitationIdentifier);
        }
        return isIdentified;
    }

    public RegistrationInvitation openEnded() {
        this.setStartingOn(null);
        this.setUntil(null);
        return this;
    }

    public RegistrationInvitation redefineAs() {
        this.setStartingOn(null);
        this.setUntil(null);
        return this;
    }

    public ZonedDateTime startingOn() {
        return this.startingOn;
    }

    public RegistrationInvitation startingOn(ZonedDateTime aDate) {

        if (this.until() != null && aDate.isAfter(this.until())) {
            throw new IllegalStateException("Cannot set starting-on date after until date.");
        }

        this.setStartingOn(aDate);

        return this;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public InvitationDescriptor toDescriptor() {
        return
                new InvitationDescriptor(
                        this.tenantId(),
                        this.invitationId(),
                        this.description(),
                        this.startingOn(),
                        this.until());
    }

    public ZonedDateTime until() {
        return this.until;
    }

    public RegistrationInvitation until(ZonedDateTime aDate) {

        if (this.startingOn() == null && aDate != null) {
            throw new IllegalStateException("Cannot set until date before setting starting-on date.");
        }
        if (this.startingOn() != null && aDate != null && aDate.isBefore(this.startingOn())) {
            throw new IllegalStateException("Until date must not  be before  starting-on date.");
        }

        this.setUntil(aDate);

        return this;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            RegistrationInvitation typedObject = (RegistrationInvitation) anObject;
            equalObjects =
                    this.tenantId().equals(typedObject.tenantId()) &&
                            this.invitationId().equals(typedObject.invitationId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(6325 * 233)
                        + this.tenantId().hashCode()
                        + this.invitationId().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "RegistrationInvitation ["
                + "tenantId=" + tenantId
                + ", description=" + description
                + ", invitationId=" + invitationId
                + ", startingOn=" + startingOn
                + ", until=" + until + "]";
    }

    protected void assertValidInvitationDates() {
        // either both dates must be null, or both dates must be set
        if (this.startingOn() == null && this.until() == null) {
            // valid
        } else if (this.startingOn() == null && this.until() != null) {
            throw new IllegalStateException("This is an invalid open-ended invitation.");
        } else if (this.startingOn() != null && this.until() != null && this.until().isBefore(this.startingOn())) {
            throw new IllegalStateException("The starting date and time must be before the until date and time.");
        }
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "The invitation description is required.");
        this.assertArgumentLength(aDescription, 1, 200, "The invitation description must be 100 characters or less.");

        this.description = aDescription;
    }

    protected void setInvitationId(String anInvitationId) {
        this.assertArgumentNotEmpty(anInvitationId, "The invitationId is required.");
        this.assertArgumentLength(anInvitationId, 1, 36, "The invitation id must be 36 characters or less.");

        this.invitationId = anInvitationId;
    }

    protected void setStartingOn(ZonedDateTime aStartingOn) {
        this.startingOn = aStartingOn;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId is required.");

        this.tenantId = aTenantId;
    }

    protected void setUntil(ZonedDateTime anUntil) {
        this.until = anUntil;
    }


}

