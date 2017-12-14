package com.diaspogift.identityandaccess.domain.model.access;


import org.springframework.security.core.GrantedAuthority;

public class RoleDescriptor implements GrantedAuthority {

    private String tenantId;
    private String roleName;


    public RoleDescriptor(String tenantId, String roleName) {

        this.tenantId = tenantId;
        this.roleName = roleName;

    }


    public String tenantId() {
        return tenantId;
    }

    public String roleName() {
        return roleName;
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
