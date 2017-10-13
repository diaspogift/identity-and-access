package com.diaspogift.identityandaccess.application.representation.tenant;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

public class TenantRepresentation extends ResourceSupport implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TenantRepresentation.class);
    private String tenantId;
    private String name;
    private String description;
    private boolean active;

    public TenantRepresentation(Tenant aTenant) {
        super();
        this.initializeFrom(aTenant);
    }

    public TenantRepresentation() {
        super();
    }


    public TenantRepresentation(String tenantId, String name, String description) {
        this.tenantId = tenantId;
        this.name = name;
        this.description = description;
    }

    public static Logger logger() {
        return logger;
    }

    public void initializeFrom(Tenant aTenant) {

        this.tenantId = aTenant.tenantId().id();
        this.name = aTenant.name();
        this.description = aTenant.description();
        this.active = aTenant.isActive();
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TenantRepresentation that = (TenantRepresentation) o;

        return tenantId != null ? tenantId.equals(that.tenantId) : that.tenantId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TenantRepresentation{" +
                "tenantId='" + tenantId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
