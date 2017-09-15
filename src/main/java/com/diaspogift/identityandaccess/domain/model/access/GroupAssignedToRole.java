package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;

import java.util.Date;

public class GroupAssignedToRole implements DomainEvent {

    private int eventVersion;
    private String groupName;
    private Date occurredOn;
    private String roleName;
    private TenantId tenantId;

    public GroupAssignedToRole(TenantId aTenantId, String aRoleName, String aGroupName) {
        super();

        this.eventVersion = 1;
        this.groupName = aGroupName;
        this.occurredOn = new Date();
        this.roleName = aRoleName;
        this.tenantId = aTenantId;
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

    public String roleName() {
        return this.roleName;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
