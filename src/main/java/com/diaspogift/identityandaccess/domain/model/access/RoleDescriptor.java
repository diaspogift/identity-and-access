package com.diaspogift.identityandaccess.domain.model.access;


import org.springframework.security.core.GrantedAuthority;

public class RoleDescriptor implements GrantedAuthority {

    private String tenantId;
    private String roleName;


    public RoleDescriptor() {
        super();
    }

    public RoleDescriptor(String tenantId, String roleName) {

        this.tenantId = tenantId;
        this.roleName = roleName;

    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {

        return "RoleDescriptor{" +
                "tenantId='" + tenantId + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
