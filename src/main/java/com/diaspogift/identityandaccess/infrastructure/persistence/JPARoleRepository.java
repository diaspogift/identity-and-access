package com.diaspogift.identityandaccess.infrastructure.persistence;

import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleRepository;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JPARoleRepository implements RoleRepository{
    @Override
    public void add(Role aRole) {

    }

    @Override
    public Collection<Role> allRoles(TenantId aTenantId) {
        return null;
    }

    @Override
    public void remove(Role aRole) {

    }

    @Override
    public Role roleNamed(TenantId aTenantId, String aRoleName) {
        return null;
    }
}
