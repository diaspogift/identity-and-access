package com.diaspogift.identityandaccess.domain.model.identity;

import java.util.Collection;

public interface GroupRepository {

    void add(Group aGroup);

    Collection<Group> allGroups(TenantId aTenantId);

    Group groupNamed(TenantId aTenantId, String aName);

    void remove(Group aGroup);
}
