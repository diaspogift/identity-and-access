package com.diaspogift.identityandaccess.infrastructure.persistence;

import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleRepository;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class JPARoleRepository implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void add(Role aRole) {
        this.entityManager().persist(aRole);
    }

    public Collection<Role> allRoles(TenantId aTenantId) {
        return this.entityManager().
                createQuery("select role from com.diaspogift.identityandaccess.domain.model.access.Role as role " +
                "where role.tenantId = :tenantId", Role.class)
                .setParameter("tenantId", aTenantId)
                .getResultList();
    }

    public void remove(Role aRole) {
        this.entityManager().remove(aRole);
    }

    public Role roleNamed(TenantId aTenantId, String aRoleName) {

        System.out.println("\n atenantId = "+aTenantId);
        System.out.println("\n aRoleName = "+aRoleName);

        return this.entityManager().
                createQuery("select role from com.diaspogift.identityandaccess.domain.model.access.Role as role " +
                        "where role.tenantId = :tenantId and role.name = :roleName ", Role.class)
                .setParameter("tenantId", aTenantId)
                .setParameter("roleName", aRoleName)
                .getSingleResult();
    }

    public EntityManager entityManager() {
        return this.entityManager;
    }
}
