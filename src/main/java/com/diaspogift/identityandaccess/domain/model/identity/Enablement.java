package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * This class is for verify if a specific tenant is enabled or desabled.
 * <p>It can also be used to enable or desable a tenant</p>
 */
public final class Enablement extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean enabled;
    private ZonedDateTime endDate;
    private ZonedDateTime startDate;

    public Enablement(boolean anEnabled, ZonedDateTime aStartDate, ZonedDateTime anEndDate) {
        super();

        if (aStartDate != null || anEndDate != null) {
            this.assertArgumentNotNull(aStartDate, "The start date must be provided.");
            this.assertArgumentNotNull(anEndDate, "The end date must be provided.");
            this.assertArgumentFalse(aStartDate.isAfter(anEndDate), "Enablement start and/or end date is invalid.");
        }

        this.setEnabled(anEnabled);
        this.setEndDate(anEndDate);
        this.setStartDate(aStartDate);
    }

    public Enablement(Enablement anEnablement) {
        this(anEnablement.isEnabled(), anEnablement.startDate(), anEnablement.endDate());
    }

    protected Enablement() {
        super();
    }

    public static Enablement indefiniteEnablement() {
        return new Enablement(true, null, null);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    private void setEnabled(boolean anEnabled) {
        this.enabled = anEnabled;
    }

    public boolean isEnablementEnabled() {
        boolean enabled = false;

        if (this.isEnabled()) {
            if (!this.isTimeExpired()) {
                enabled = true;
            }
        }


        return enabled;
    }

    public ZonedDateTime endDate() {
        return this.endDate;
    }

    public boolean isTimeExpired() {
        boolean timeExpired = false;

        if (this.startDate() != null && this.endDate() != null) {
            ZonedDateTime now = ZonedDateTime.now();
            if (now.isBefore(this.startDate()) ||
                    now.isAfter(this.endDate())) {
                timeExpired = true;
            }
        }
        else if(this.endDate() == null && this.startDate() != null){
            ZonedDateTime now = ZonedDateTime.now();
            if (now.isBefore(this.startDate())) {
                timeExpired = true;
            }
        }

        return timeExpired;
    }

    public ZonedDateTime startDate() {
        return this.startDate;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Enablement typedObject = (Enablement) anObject;
            equalObjects =
                    this.isEnabled() == typedObject.isEnabled() &&
                            ((this.startDate() == null && typedObject.startDate() == null) ||
                                    (this.startDate() != null && this.startDate().equals(typedObject.startDate()))) &&
                            ((this.endDate() == null && typedObject.endDate() == null) ||
                                    (this.endDate() != null && this.endDate().equals(typedObject.endDate())));
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(19563 * 181)
                        + (this.isEnabled() ? 1 : 0)
                        + (this.startDate() == null ? 0 : this.startDate().hashCode())
                        + (this.endDate() == null ? 0 : this.endDate().hashCode());

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Enablement [enabled=" + enabled + ", endDate=" + endDate + ", startDate=" + startDate + "]";
    }

    private void setEndDate(ZonedDateTime anEndDate) {
        this.endDate = anEndDate;
    }

    private void setStartDate(ZonedDateTime aStartDate) {
        this.startDate = aStartDate;
    }
}
