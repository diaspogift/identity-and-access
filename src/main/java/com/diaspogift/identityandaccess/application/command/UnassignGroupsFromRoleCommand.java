package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.group.GroupRepresentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UnassignGroupsFromRoleCommand {

    private String tenantId;
    private List<String> groupNames;
    private String roleName;

    public UnassignGroupsFromRoleCommand(String tenantId, List<String> groupNames, String roleName) {
        super();

        this.tenantId = tenantId;
        this.groupNames = groupNames;
        this.roleName = roleName;
    }

    public UnassignGroupsFromRoleCommand(String tenantId, String roleName, Collection<GroupRepresentation> groups) {

        this.initialiazeFrom(tenantId, roleName, groups);
    }

    private void initialiazeFrom(String tenantId, String roleName, Collection<GroupRepresentation> groups) {

        this.groupNames = new ArrayList<String>();

        this.tenantId = tenantId;

        for (GroupRepresentation next : groups) {
            this.groupNames.add(next.getName());
        }
        this.roleName = roleName;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public String roleName() {
        return this.roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

