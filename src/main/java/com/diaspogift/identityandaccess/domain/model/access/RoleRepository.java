package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.identity.TenantId;

import java.util.Collection;

public interface RoleRepository {

    public void add(Role aRole);

    public Collection<Role> allRoles(TenantId aTenantId);

    public void remove(Role aRole);

    public Role roleNamed(TenantId aTenantId, String aRoleName);
}
