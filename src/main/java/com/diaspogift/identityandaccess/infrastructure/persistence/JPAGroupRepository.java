package com.diaspogift.identityandaccess.infrastructure.persistence;

import com.diaspogift.identityandaccess.domain.model.identity.Group;
import com.diaspogift.identityandaccess.domain.model.identity.GroupRepository;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JPAGroupRepository implements GroupRepository{
    @Override
    public void add(Group aGroup) {

    }

    @Override
    public Collection<Group> allGroups(TenantId aTenantId) {
        return null;
    }

    @Override
    public Group groupNamed(TenantId aTenantId, String aName) {
        return null;
    }

    @Override
    public void remove(Group aGroup) {

    }
}
