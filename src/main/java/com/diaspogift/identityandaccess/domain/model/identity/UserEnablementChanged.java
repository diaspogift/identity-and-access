package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;

import java.util.Date;

public class UserEnablementChanged implements DomainEvent {

    private Enablement enablement;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;
    private String username;

    public UserEnablementChanged(
            TenantId aTenantId,
            String aUsername,
            Enablement anEnablement) {

        super();

        this.enablement = anEnablement;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.username = aUsername;
    }

    public Enablement enablement() {
        return this.enablement;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
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
