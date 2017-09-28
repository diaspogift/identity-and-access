package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;

public class ProvisionedTenantRepresentation {


    private String tenantId;
    private String tenantName;
    private String tenantDescription;


    public ProvisionedTenantRepresentation() {
        super();
    }

    public ProvisionedTenantRepresentation(String tenantId, String tenantName, String tenantDescription) {

        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantDescription = tenantDescription;

    }

    public ProvisionedTenantRepresentation(Tenant aTenant) {

        this.tenantId = aTenant.tenantId().id();
        this.tenantName = aTenant.name();
        this.tenantDescription = aTenant.description();
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getTenantDescription() {
        return tenantDescription;
    }


    @Override
    public String toString() {
        return "ProvisionTenantRepresentation{" +
                "tenantId='" + tenantId + '\'' +
                "tenantName='" + tenantName + '\'' +
                ", tenantDescription='" + tenantDescription + '\'' +
                '}';
    }
}
