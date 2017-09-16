package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

import java.io.Serializable;

public class GroupId extends AssertionConcern implements Serializable {


    /**
     * Tenant to which belong the group
     */
    private TenantId tenantId;
    /**
     * The name (That uniquelly identify the group)
     */
    private String name;

    public GroupId(TenantId tenantId, String name) {


        this.assertArgumentNotNull(tenantId, "Tenant is required.");
        this.assertArgumentNotNull(name, "Role name is required.");
        this.setTenantId(tenantId);
        this.setName(name);
    }

    public GroupId() {
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

        GroupId userId = (GroupId) o;

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
        return "GroupId = [" + "tenantId=" + tenantId + ", name=" + name + " ]";
    }
}
