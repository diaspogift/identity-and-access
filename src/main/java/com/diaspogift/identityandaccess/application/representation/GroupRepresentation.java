package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.Group;
import org.springframework.hateoas.ResourceSupport;

public class GroupRepresentation extends ResourceSupport {

    private String tenantId;
    private String name;
    private String description;

    public GroupRepresentation() {
        super();
    }

    public GroupRepresentation(Group aGroup) {

        this.initialyzeFrom(aGroup);
    }


    private void initialyzeFrom(Group aGroup) {

        this.tenantId = aGroup.tenantId().id();

        this.name = aGroup.name();
        this.description = aGroup.description();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupRepresentation that = (GroupRepresentation) o;

        if (tenantId != null ? !tenantId.equals(that.tenantId) : that.tenantId != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = tenantId != null ? tenantId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupRepresentation{" +
                "tenantId='" + tenantId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
