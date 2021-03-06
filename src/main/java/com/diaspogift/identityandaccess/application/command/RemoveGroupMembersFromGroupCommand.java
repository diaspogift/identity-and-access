package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.group.GroupMemberRepresentation;

import java.util.ArrayList;
import java.util.Collection;

public class RemoveGroupMembersFromGroupCommand {

    private String tenantId;
    private Collection<GroupMemberRepresentation> groupMembers = new ArrayList<GroupMemberRepresentation>();
    private String parentGroupName;


    public RemoveGroupMembersFromGroupCommand(String tenantId, String groupName, Collection<GroupMemberRepresentation> groupMembers) {

        this.tenantId = tenantId;
        this.groupMembers = groupMembers;
        this.parentGroupName = groupName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Collection<GroupMemberRepresentation> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Collection<GroupMemberRepresentation> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getParentGroupName() {
        return parentGroupName;
    }

    public void setParentGroupName(String parentGroupName) {
        this.parentGroupName = parentGroupName;
    }
}

