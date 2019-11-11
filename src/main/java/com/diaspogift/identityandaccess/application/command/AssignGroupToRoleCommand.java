package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.group.GroupRepresentation;

public class AssignGroupToRoleCommand {

    private String tenantId;
    private String groupName;
    private String roleName;

    public AssignGroupToRoleCommand(String tenantId, String groupName, String roleName) {
        super();

        this.tenantId = tenantId;
        this.groupName = groupName;
        this.roleName = roleName;
    }

    public AssignGroupToRoleCommand(String tenantId, String roleName, GroupRepresentation groupRepresentation) {

        this.initialiazeFrom(tenantId, roleName, groupRepresentation);
    }

    private void initialiazeFrom(String tenantId, String roleName, GroupRepresentation groupRepresentation) {

        this.tenantId = tenantId;
        this.groupName = groupRepresentation.getName();
        this.roleName = roleName;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

