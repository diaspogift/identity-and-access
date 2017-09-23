package com.diaspogift.identityandaccess.infrastructure.persistence.jpa;

import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;


@Repository
public class JPAGroupRepository implements GroupRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public void add(Group aGroup) {
        this.entityManager().persist(aGroup);
    }

    @Override
    public Collection<Group> allGroups(TenantId aTenantId) {

        return this.entityManager()
                .createNamedQuery("selectAllGroups", Group.class)
                .setParameter("tenantId", aTenantId)
                .getResultList();
    }

    @Override
    public Group groupNamed(TenantId aTenantId, String aName) {

        if (aName.startsWith(Group.ROLE_GROUP_PREFIX)) {
            throw new IllegalArgumentException("May not find internal groups.");
        }

        return this.entityManager()
                .createNamedQuery("selectGroupNamed", Group.class)
                .setParameter("tenantId", aTenantId)
                .setParameter("name", aName)
                .getSingleResult();
    }

    @Override
    public Group groupOfGroupId(GroupId aGroupId) {

        return this.entityManager()
                .createNamedQuery("selectGroupOfGroupId", Group.class)
                .setParameter("tenantId", aGroupId.tenantId())
                .setParameter("name", aGroupId.name()).getSingleResult();
    }


    @Override
    public void remove(Group aGroup) {
        this.entityManager().remove(aGroup);

    }

    @Override
    public Collection<GroupMember> groupMembers(TenantId tenantId, String aGroupName) {

        return this.entityManager()
                .createNamedQuery("selectGroupNamed", Group.class)
                .setParameter("tenantId", tenantId)
                .setParameter("name", aGroupName)
                .getSingleResult()
                .groupMembers();


    }

    public EntityManager entityManager() {
        return this.entityManager;
    }

}
