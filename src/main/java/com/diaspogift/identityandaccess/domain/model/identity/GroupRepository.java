package com.diaspogift.identityandaccess.domain.model.identity;

import java.util.Collection;

public interface GroupRepository {

    void add(Group aGroup);

    Group groupOfGroupId(GroupId aGroupId);

    Collection<Group> allGroups(TenantId aTenantId);

    Group groupNamed(TenantId aTenantId, String aName);

    void remove(Group aGroup);

    Collection<GroupMember> groupMembers(TenantId tenantId, String groupName);
}
