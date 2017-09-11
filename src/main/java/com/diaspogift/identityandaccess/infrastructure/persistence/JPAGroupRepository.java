package com.diaspogift.identityandaccess.infrastructure.persistence;

import com.diaspogift.identityandaccess.domain.model.identity.Group;
import com.diaspogift.identityandaccess.domain.model.identity.GroupRepository;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;



@Repository
public class JPAGroupRepository implements GroupRepository{

    @PersistenceContext
    private EntityManager entityManager;


    public void add(Group aGroup) {
        this.entityManager().persist(aGroup);
    }

    @Override
    public Collection<Group> allGroups(TenantId aTenantId) {
        return this.entityManager()
                .createQuery("select group from com.diaspogift.identityandaccess.domain.model.identity.Group as group " +
                        "where group.tenantId = :tenantId", Group.class)
                .setParameter("tenantId", aTenantId)
                .getResultList();
    }

    @Override
    public Group groupNamed(TenantId aTenantId, String aName) {
        return this.entityManager()
                .createQuery("select group from com.diaspogift.identityandaccess.domain.model.identity.Group as group " +
                        "where group.tenantId = :tenantId and group.name = :name", Group.class)
                .setParameter("tenantId", aTenantId)
                .setParameter("name", aName)
                .getSingleResult();
    }

    @Override
    public void remove(Group aGroup) {
        this.entityManager().remove(aGroup);

    }

    public EntityManager entityManager() {
        return this.entityManager;
    }

}
