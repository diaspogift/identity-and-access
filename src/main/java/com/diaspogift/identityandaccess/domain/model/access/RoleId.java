package com.diaspogift.identityandaccess.domain.model.access;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;

import java.io.Serializable;

public class RoleId extends AssertionConcern implements Serializable {

    /**
     * Tenant to which belong the role
     */
    private TenantId tenantId;

    /**
     * The name (That uniquelly identify the role)
     */
    private String name;

    public RoleId(TenantId tenantId, String name) {

        this.assertArgumentNotNull(tenantId, "Tenant is required.");
        this.assertArgumentNotNull(name, " Group name is required.");
        this.setTenantId(tenantId);
        this.setName(name);
    }

    public RoleId() {
        super();
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    private void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public String name() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleId userId = (RoleId) o;

        if (tenantId != null ? !tenantId.equals(userId.tenantId) : userId.tenantId != null) return false;
        return name != null ? name.equals(userId.name) : userId.name == null;
    }

    @Override
    public int hashCode() {
        int result = tenantId != null ? tenantId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserId = [" + "tenantId=" + tenantId + ", name=" + name + " ]";
    }
}
