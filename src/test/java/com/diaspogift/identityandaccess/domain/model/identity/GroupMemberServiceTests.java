package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GroupMemberServiceTests extends IdentityAndAccessTest {

    public GroupMemberServiceTests() {
        super();
    }


    @Test
    public void confirmUser() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        group.addUser(user);
        DomainRegistry.groupRepository().add(group);
        assertTrue(DomainRegistry.groupMemberService().confirmUser(group, user));
    }


    @Test
    public void isMemberGroup() {

        Tenant tenant = this.actifTenantAggregate();
        Group group1 = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group group2 = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);

        group1 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group1.groupId().name());
        group1.addGroup(group2, DomainRegistry.groupMemberService());

        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group1, group2.toGroupMember()));
    }

    @Test
    public void isUserInNestedGroup() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Group group1 = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group group2 = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        group1.addGroup(group2, DomainRegistry.groupMemberService());
        group2.addUser(user);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        Role role = tenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        DomainRegistry.roleRepository().add(role);
        role.assignGroup(group1, DomainRegistry.groupMemberService());
        assertTrue(DomainRegistry.groupMemberService().isUserInNestedGroup(group1, user));
    }


}
