package com.diaspogift.identityandaccess.infrastructure.repository;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import com.diaspogift.identityandaccess.domain.model.identity.TenantRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class MockTenantRepository implements TenantRepository{

    private Set<Tenant> tenants;

    @Override
    public void add(Tenant aTenant) {
        if (tenants == null){
            tenants = new HashSet<>();
        }
        tenants.add(aTenant);
    }

    @Override
    public TenantId nextIdentity() {

        return new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
    }

    @Override
    public void remove(Tenant aTenant) {
        tenants.remove(aTenant);
    }

    @Override
    public Tenant tenantNamed(String aName) {

        for (Tenant tenant : tenants){
            if (tenant.name().equals(aName)){
                return tenant;
            }
        }

        return null;
    }

    @Override
    public Tenant tenantOfId(TenantId aTenantId) {

        for (Tenant tenant : tenants){
            if (tenant.tenantId().equals(aTenantId)){
                return tenant;
            }
        }
        return null;
    }

    public Set<Tenant> tenants(){
        return tenants;
    }
}
