package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import org.springframework.hateoas.ResourceSupport;

public class ProvisionedTenantRepresentation extends ResourceSupport {


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


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDescription() {
        return tenantDescription;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
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
