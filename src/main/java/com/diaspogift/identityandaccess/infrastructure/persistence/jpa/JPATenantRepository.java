package com.diaspogift.identityandaccess.infrastructure.persistence.jpa;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import com.diaspogift.identityandaccess.domain.model.identity.TenantRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

        Query query =
                this.entityManager().createQuery("select tenant from com.diaspogift.identityandaccess.domain.model.identity.Tenant as tenant" +
                        " where tenant.name = :name", Tenant.class)
                        .setParameter("name", aName);

        return (Tenant) JPASingleResultHelper.getSingleResultOrNull(query);
    }

    public Tenant tenantOfId(TenantId aTenantId) {

        Query query =
                this.entityManager().createQuery("select tenant from com.diaspogift.identityandaccess.domain.model.identity.Tenant as tenant" +
                        " where tenant.tenantId = :tenantId", Tenant.class)
                        .setParameter("tenantId", aTenantId);

        return (Tenant) JPASingleResultHelper.getSingleResultOrNull(query);
    }


    public EntityManager entityManager() {
        return this.entityManager;
    }
}
