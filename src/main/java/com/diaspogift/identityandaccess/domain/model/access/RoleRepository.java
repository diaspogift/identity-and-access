package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.identity.TenantId;

import java.util.Collection;

public interface RoleRepository {

    void add(Role aRole);

    Role roleOfId(RoleId aRoleId);

    Collection<Role> allRoles(TenantId aTenantId);

    void remove(Role aRole);

    Role roleNamed(TenantId aTenantId, String aRoleName);
}
