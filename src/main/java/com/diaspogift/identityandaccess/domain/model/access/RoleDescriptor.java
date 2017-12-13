package com.diaspogift.identityandaccess.domain.model.access;


public class RoleDescriptor {

    private String tenantId;
    private String roleName;


    public RoleDescriptor(String tenantId, String roleName) {

        this.tenantId = tenantId;
        this.roleName = roleName;

    }

    @Override
    public String toString() {

        return "RoleDescriptor{" +
                "tenantId='" + tenantId + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
