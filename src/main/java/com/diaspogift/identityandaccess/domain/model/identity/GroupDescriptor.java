package com.diaspogift.identityandaccess.domain.model.identity;

public class GroupDescriptor {

    private String tenantId;
    private String name;
    private String description;


    public GroupDescriptor() {
    }

    public GroupDescriptor(String tenantId, String name, String description) {
        this.tenantId = tenantId;
        this.name = name;
        this.description = description;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
