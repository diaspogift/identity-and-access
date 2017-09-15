package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

import java.io.Serializable;

public class UserId extends AssertionConcern implements Serializable {


    /**
     * Tenant to which belong the user
     */
    private TenantId tenantId;
    /**
     * The username (That uniquelly identify the user)
     */
    private String username;

    protected UserId(TenantId tenantId, String username) {

        System.out.println("\n\n SETTING UP TENANT AND USERNAME IN USERID CONSTRUCTORS tenantId = " + tenantId);
        System.out.println("\n\n SETTING UP TENANT AND USERNAME IN USERID CONSTRUCTORS username = " + username);

        this.assertArgumentNotNull(tenantId, "Tenant is required.");
        this.assertArgumentNotNull(username, "Username is required.");
        this.setTenantId(tenantId);
        this.setUsername(username);
    }

    public UserId() {
        super();
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    private void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public String username() {
        return this.username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserId userId = (UserId) o;

        if (tenantId != null ? !tenantId.equals(userId.tenantId) : userId.tenantId != null) return false;
        return username != null ? username.equals(userId.username) : userId.username == null;
    }

    @Override
    public int hashCode() {
        int result = tenantId != null ? tenantId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserId = [" + "tenantId=" + tenantId + ", username=" + username + " ]";
    }
}
