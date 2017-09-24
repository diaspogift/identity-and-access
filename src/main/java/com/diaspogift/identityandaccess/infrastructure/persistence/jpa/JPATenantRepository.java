package com.diaspogift.identityandaccess.infrastructure.persistence.jpa;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import com.diaspogift.identityandaccess.domain.model.identity.TenantRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.UUID;

@Repository
public class JPATenantRepository implements TenantRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public TenantId nextIdentity() {
        return new TenantId(UUID.randomUUID().toString().toUpperCase());
    }

    public void add(Tenant aTenant) {
        this.entityManager().persist(aTenant);
    }

    public void remove(Tenant aTenant) {
        this.entityManager().remove(aTenant);
    }

    public Tenant tenantNamed(String aName) {

        return this.entityManager().createNamedQuery("selectTenantNamed", Tenant.class).setParameter("name", aName).getSingleResult();
    }

    public Tenant tenantOfId(TenantId aTenantId) {

        return this.entityManager().createNamedQuery("selectTenantOfId", Tenant.class).setParameter("tenantId", aTenantId).getSingleResult();
    }

    public Collection<Tenant> allTenants() {

        Collection<Tenant> tenants = this.entityManager().createNamedQuery("selectAllTenants", Tenant.class).getResultList();

        return tenants;

    }

    public Collection<Tenant> allTenants(Integer aFirst, Integer aRangeSize) {

        Collection<Tenant> tenants = this.entityManager().createNamedQuery("selectAllTenantsInterval", Tenant.class)
                .setFirstResult(aFirst)
                .setMaxResults(aRangeSize)
                .getResultList();

        tenants.forEach(System.out::println);

        return tenants;
    }

    public EntityManager entityManager() {
        return this.entityManager;
    }
}
