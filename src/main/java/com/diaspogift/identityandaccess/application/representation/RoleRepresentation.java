package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.access.Role;
import org.springframework.hateoas.ResourceSupport;

public class RoleRepresentation extends ResourceSupport {


    private String tenantId;
    private String name;
    private String descrition;
    private boolean supportsNesting;


    public RoleRepresentation() {
    }

    public RoleRepresentation(Role aRole) {

        this.initializeFrom(aRole);
    }

    private void initializeFrom(Role aRole) {


        System.out.println(" HEREEEEEEE _______________________________ " + aRole.tenantId().id());
        System.out.println(" HEREEEEEEE _______________________________ " + aRole.tenantId().id());
        System.out.println(" HEREEEEEEE _______________________________ " + aRole.tenantId().id());
        this.tenantId = aRole.tenantId().id();
        this.name = aRole.name();
        this.descrition = aRole.description();
        this.supportsNesting = aRole.supportsNesting();
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

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public boolean isSupportsNesting() {
        return supportsNesting;
    }

    public void setSupportsNesting(boolean supportsNesting) {
        this.supportsNesting = supportsNesting;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RoleRepresentation that = (RoleRepresentation) o;

        if (tenantId != null ? !tenantId.equals(that.tenantId) : that.tenantId != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
