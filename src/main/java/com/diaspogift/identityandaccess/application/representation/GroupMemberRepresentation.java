package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.GroupMember;
import org.springframework.hateoas.ResourceSupport;

public class GroupMemberRepresentation extends ResourceSupport {

    private String name;
    private String type;


    public GroupMemberRepresentation() {
        super();
    }

    public GroupMemberRepresentation(GroupMember aGroupMember) {

        this.initialyzeFrom(aGroupMember);
    }

    private void initialyzeFrom(GroupMember aGroupMember) {

        this.name = aGroupMember.name();
        this.type = aGroupMember.type().name();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!super.equals(o)) return false;

        GroupMemberRepresentation that = (GroupMemberRepresentation) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupMemberRepresentation{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}


