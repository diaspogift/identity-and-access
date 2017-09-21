package com.diaspogift.identityandaccess.application.representation;

public class TenantAvailabilityRepresentation {

    private String tenantId;
    private boolean active;

    public TenantAvailabilityRepresentation() {
    }

    public TenantAvailabilityRepresentation(String tenantId, boolean active) {
        this.tenantId = tenantId;
        this.active = active;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
                "tenantId='" + tenantId + '\'' +
                ", active=" + active +
                '}';
    }
}
