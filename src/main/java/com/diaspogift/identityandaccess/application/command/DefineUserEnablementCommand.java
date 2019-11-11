package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.user.UserEnablementReprensentation;

import java.time.ZonedDateTime;

public class DefineUserEnablementCommand {

    private String tenantId;
    private String username;
    private boolean enabled;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public DefineUserEnablementCommand(String tenantId, String username, boolean enabled,
                                       ZonedDateTime startDate, ZonedDateTime endDate) {

        super();

        this.tenantId = tenantId;
        this.username = username;
        this.enabled = enabled;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DefineUserEnablementCommand() {
        super();
    }

    public DefineUserEnablementCommand(String tenantId, String username, UserEnablementReprensentation userEnablementReprensentation) {

        this.initialyzeFrom(tenantId, username, userEnablementReprensentation);
    }

    private void initialyzeFrom(String tenantId, String username, UserEnablementReprensentation userEnablementReprensentation) {

        this.tenantId = tenantId;
        this.username = username;
        this.enabled = userEnablementReprensentation.isEnabled();
        this.startDate = ZonedDateTime.parse(userEnablementReprensentation.getStartDate());
        this.endDate = ZonedDateTime.parse(userEnablementReprensentation.getEndDate());
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ZonedDateTime getStartDate() {
        return this.startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return this.endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }
}

