package com.diaspogift.identityandaccess.application.representation.tenant;

public class TenantAvailabilityRepresentation {

    private boolean active;

    public TenantAvailabilityRepresentation() {
    }

    public TenantAvailabilityRepresentation(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TenantAvailabilityRepresentation{" +
                ", active=" + active +
                '}';
    }
}
