package com.diaspogift.identityandaccess.infrastructure.persistence.jpa;

import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleId;
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

    public Role roleOfId(RoleId aRoleId) {

        return this.entityManager().
                createNamedQuery("selectRoleOfId", Role.class)
                .setParameter("tenantId", aRoleId.tenantId())
                .setParameter("roleName", aRoleId.name())
                .getSingleResult();

    }

    public Collection<Role> allRoles(TenantId aTenantId) {

        return this.entityManager().
                createNamedQuery("selectAllRoles", Role.class)
                .setParameter("tenantId", aTenantId)
                .getResultList();
    }

    public void remove(Role aRole) {
        this.entityManager().remove(aRole);
    }

    public Role roleNamed(TenantId aTenantId, String aRoleName) {

        return this.entityManager().
                createNamedQuery("selectRoleNamed", Role.class)
                .setParameter("tenantId", aTenantId)
                .setParameter("roleName", aRoleName)
                .getSingleResult();

    }

    public EntityManager entityManager() {
        return this.entityManager;
    }
}
