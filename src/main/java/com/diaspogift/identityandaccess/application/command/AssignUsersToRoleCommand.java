package com.diaspogift.identityandaccess.application.command;

import java.util.ArrayList;
import java.util.List;

public class AssignUsersToRoleCommand {

    private String tenantId;
    private List<String> usernames;
    private String roleName;

    public AssignUsersToRoleCommand(String tenantId, String roleName, List<String> usernames) {
        super();

        this.usernames = new ArrayList<>();

        for (String next : usernames) {

            this.usernames.add(next);
        }

        this.tenantId = tenantId;
        this.roleName = roleName;
    }

    public AssignUsersToRoleCommand() {
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

