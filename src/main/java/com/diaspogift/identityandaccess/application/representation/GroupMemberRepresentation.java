package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.GroupMember;
import org.springframework.hateoas.ResourceSupport;

public class GroupMemberRepresentation extends ResourceSupport {

    private String name;
    private String tenantId;
    private String type;


    public GroupMemberRepresentation() {
        super();
    }

    public GroupMemberRepresentation(GroupMember aGroupMember) {

        this.initialyzeFrom(aGroupMember);
    }

    private void initialyzeFrom(GroupMember aGroupMember) {

        this.tenantId = aGroupMember.tenantId().id();
        this.name = aGroupMember.name();
        this.type = aGroupMember.type().name();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupMemberRepresentation that = (GroupMemberRepresentation) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return tenantId != null ? tenantId.equals(that.tenantId) : that.tenantId == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "GroupMemberRepresentation{" +
                "name='" + name + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}


