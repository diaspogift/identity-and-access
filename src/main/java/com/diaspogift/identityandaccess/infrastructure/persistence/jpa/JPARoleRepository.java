package com.diaspogift.identityandaccess.infrastructure.persistence.jpa;

import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleId;
import com.diaspogift.identityandaccess.domain.model.access.RoleRepository;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class JPARoleRepository implements RoleRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public void add(Role aRole) {
        this.entityManager().persist(aRole);
    }

    public Role roleOfId(RoleId aRoleId) {

        Query query =
                this.entityManager().
                        createQuery("select role from com.diaspogift.identityandaccess.domain.model.access.Role as role " +
                                "where role.roleId.tenantId = :tenantId and role.roleId.name = :roleName ", Role.class)
                        .setParameter("tenantId", aRoleId.tenantId())
                        .setParameter("roleName", aRoleId.name());

        return (Role) JPASingleResultHelper.getSingleResultOrNull(query);
    }

    public Collection<Role> allRoles(TenantId aTenantId) {
        return this.entityManager().
                createQuery("select role from com.diaspogift.identityandaccess.domain.model.access.Role as role " +
                        "where role.roleId.tenantId = :tenantId", Role.class)
                .setParameter("tenantId", aTenantId)
                .getResultList();
    }

    public void remove(Role aRole) {
        this.entityManager().remove(aRole);
    }

    public Role roleNamed(TenantId aTenantId, String aRoleName) {

        Query query =
                this.entityManager().
                        createQuery("select role from com.diaspogift.identityandaccess.domain.model.access.Role as role " +
                                "where role.roleId.tenantId = :tenantId and role.roleId.name = :roleName ", Role.class)
                        .setParameter("tenantId", aTenantId)
                        .setParameter("roleName", aRoleName);

        return (Role) JPASingleResultHelper.getSingleResultOrNull(query);
    }

    public EntityManager entityManager() {
        return this.entityManager;
    }
}
