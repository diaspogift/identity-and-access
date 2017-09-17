package com.diaspogift.identityandaccess.domain.model.identity;

public interface TenantRepository {

    void add(Tenant aTenant);

    TenantId nextIdentity();

    void remove(Tenant aTenant);

    Tenant tenantNamed(String aName);

    Tenant tenantOfId(TenantId aTenantId);
}
