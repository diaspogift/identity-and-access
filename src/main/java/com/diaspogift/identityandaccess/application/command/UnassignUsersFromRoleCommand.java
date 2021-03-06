package com.diaspogift.identityandaccess.application.command;

import java.util.List;

public class UnassignUsersFromRoleCommand {

    private String tenantId;
    private List<String> usernames;
    private String roleName;

    public UnassignUsersFromRoleCommand(String tenantId, String roleName, List<String> usernames) {
        super();

        this.tenantId = tenantId;
        this.usernames = usernames;
        this.roleName = roleName;
    }

    public UnassignUsersFromRoleCommand() {
        super();
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

