//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;


/**
 * Truly a value object need to be serialized and saved as it
 */

@Entity
public class RegistrationInvitation extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer _id;

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
            if (dateNow.isAfter(this.startingOn()) && dateNow.isBefore(this.until())) {
                isAvailable = true;
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
        if (this.until() != null) {
            throw new IllegalStateException("Cannot set starting-on date after until date.");
        }

        this.setStartingOn(aDate);

        // temporary if until() properly follows, but
        // prevents illegal state if until() doesn't follow
        // TO DO was not commented out this.setUntil(new Date(aDate.getTime() + 86400000));

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
        if (this.startingOn() == null) {
            throw new IllegalStateException("Cannot set until date before setting starting-on date.");
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
            ; // valid
        } else if (this.startingOn() == null || this.until() == null &&
                this.startingOn() != this.until()) {
            throw new IllegalStateException("This is an invalid open-ended invitation.");
        } else if (this.startingOn().isAfter(this.until())) {
            throw new IllegalStateException("The starting date and time must be before the until date and time.");
        }
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "The invitation description is required.");
        this.assertArgumentLength(aDescription, 1, 100, "The invitation description must be 100 characters or less.");

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
