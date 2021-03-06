package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.group.GroupRepresentation;

public class ProvisionGroupCommand {

    private String description;
    private String groupName;
    private String tenantId;

    public ProvisionGroupCommand(
            String tenantId,
            String groupName,
            String description) {

        super();

        this.description = description;
        this.groupName = groupName;
        this.tenantId = tenantId;
    }

    public ProvisionGroupCommand() {
        super();
    }

    public ProvisionGroupCommand(String tenantId, GroupRepresentation groupRepresentation) {

        this.initialyzeFrom(tenantId, groupRepresentation);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    private void initialyzeFrom(String tenantId, GroupRepresentation aGroupRepresentation) {

        this.tenantId = tenantId;
        this.groupName = aGroupRepresentation.getName();
        this.description = aGroupRepresentation.getDescription();
    }
}

