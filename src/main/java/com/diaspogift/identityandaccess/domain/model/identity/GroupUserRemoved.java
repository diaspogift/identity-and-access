package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;

import java.util.Date;

public class GroupUserRemoved implements DomainEvent {

    private int eventVersion;
    private String groupName;
    private Date occurredOn;
    private TenantId tenantId;
    private String username;

    public GroupUserRemoved(TenantId aTenantId, String aGroupName, String aUsername) {
        super();

        this.eventVersion = 1;
        this.groupName = aGroupName;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.username = aUsername;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String groupName() {
        return this.groupName;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public String username() {
        return this.username;
    }
}
