package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;

import java.util.Date;

public class GroupGroupRemoved implements DomainEvent {

    private int eventVersion;
    private String groupName;
    private String nestedGroupName;
    private Date occurredOn;
    private TenantId tenantId;

    public GroupGroupRemoved(TenantId aTenantId, String aGroupName, String aNestedGroupName) {
        super();

        this.eventVersion = 1;
        this.groupName = aGroupName;
        this.nestedGroupName = aNestedGroupName;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String groupName() {
        return this.groupName;
    }

    public String nestedGroupName() {
        return this.nestedGroupName;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
