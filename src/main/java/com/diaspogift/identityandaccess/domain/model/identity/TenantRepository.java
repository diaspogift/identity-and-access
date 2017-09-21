package com.diaspogift.identityandaccess.domain.model.identity;

import java.util.Collection;

public interface TenantRepository {

    void add(Tenant aTenant);

    TenantId nextIdentity();

    void remove(Tenant aTenant);

    Tenant tenantNamed(String aName);

    Tenant tenantOfId(TenantId aTenantId);

    Collection<Tenant> allTenants();

    //TO DO TEST
    Collection<Tenant> allTenants(Integer aFirst, Integer aRangeSize);


}
