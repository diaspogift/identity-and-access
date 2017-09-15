package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoleTests extends IdentityAndAccessTest {

    public RoleTests() {
        super();
    }

    @Test
    public void cretateRoleThatSupportNesting() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        assertTrue(role.supportsNesting());
        DomainRegistry.roleRepository().add(role);
        Role savedRole = DomainRegistry.roleRepository().roleNamed(activeTenant.tenantId(), role.name());
        assertEquals(1, DomainRegistry.roleRepository().allRoles(activeTenant.tenantId()).size());
        assertEquals(role, savedRole);
        this.expectedEvents(1);
        this.expectedEvent(RoleProvisioned.class, 1);

    }


    @Test
    public void cretateRoleThatDoesNotSupportNesting() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION);
        assertFalse(role.supportsNesting());
        DomainRegistry.roleRepository().add(role);
        Role savedRole = DomainRegistry.roleRepository().roleNamed(activeTenant.tenantId(), role.name());
        assertEquals(1, DomainRegistry.roleRepository().allRoles(activeTenant.tenantId()).size());
        assertEquals(role, savedRole);
        this.expectedEvents(1);
        this.expectedEvent(RoleProvisioned.class, 1);
    }


    @Test
    public void assignGroup() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        DomainRegistry.roleRepository().add(role);
        Group group = activeTenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        role.assignGroup(group, DomainRegistry.groupMemberService());
        assertEquals(1, role.group().groupMembers().size());
        GroupMember addedGroupMember = null;
        for (GroupMember next : role.group().groupMembers()) {
            if (next.isGroup() && next.tenantId().equals(activeTenant.tenantId()) && next.name().equals(FIXTURE_GROUP_NAME_1)) {
                addedGroupMember = next;
                break;
            }
        }
        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(role.group(), addedGroupMember));
        this.expectedEvents(4);
        this.expectedEvent(GroupAssignedToRole.class, 1);
        this.expectedEvent(RoleProvisioned.class, 1);
        this.expectedEvent(GroupProvisioned.class, 1);
        this.expectedEvent(GroupGroupAdded.class, 1);


    }

    @Test
    public void unassignGroup() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        DomainRegistry.roleRepository().add(role);
        Group group = activeTenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        role.assignGroup(group, DomainRegistry.groupMemberService());
        assertEquals(1, role.group().groupMembers().size());
        GroupMember addedGroupMember = null;
        for (GroupMember next : role.group().groupMembers()) {
            if (next.isGroup() && next.tenantId().equals(activeTenant.tenantId()) && next.name().equals(FIXTURE_GROUP_NAME_1)) {
                addedGroupMember = next;
                break;
            }
        }
        role.unassignGroup(group);
        assertEquals(0, role.group().groupMembers().size());
        assertFalse(DomainRegistry.groupMemberService().isMemberGroup(role.group(), addedGroupMember));
        this.expectedEvents(6);
        this.expectedEvent(GroupAssignedToRole.class, 1);
        this.expectedEvent(GroupUnassignedFromRole.class, 1);
        this.expectedEvent(RoleProvisioned.class, 1);
        this.expectedEvent(GroupProvisioned.class, 1);
        this.expectedEvent(GroupGroupAdded.class, 1);
        this.expectedEvent(GroupGroupRemoved.class, 1);
    }


    @Test(expected = IllegalStateException.class)
    public void assignGroupToRoleThatDoesNotSupportNestting() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION);
        DomainRegistry.roleRepository().add(role);
        Group group = activeTenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        role.assignGroup(group, DomainRegistry.groupMemberService());
        this.expectedEvent(GroupAssignedToRole.class, 1);
    }

    @Test
    public void assignGroupThatIsAlreadyAssignedToRole() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        DomainRegistry.roleRepository().add(role);
        Group group = activeTenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        role.assignGroup(group, DomainRegistry.groupMemberService());
        role.assignGroup(group, DomainRegistry.groupMemberService());
        assertEquals(1, role.group().groupMembers().size());
        this.expectedEvents(5);
        this.expectedEvent(GroupAssignedToRole.class, 2);
        this.expectedEvent(RoleProvisioned.class, 1);
        this.expectedEvent(GroupProvisioned.class, 1);
        this.expectedEvent(GroupGroupAdded.class, 1);

    }


    @Test(expected = IllegalArgumentException.class)
    public void assignGroupRecursion() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role1 = activeTenant.provisionRole(FIXTURE_ROLE_NAME_1, FIXTURE_ROLE_DESCRIPTION_1, true);
        Role role2 = activeTenant.provisionRole(FIXTURE_ROLE_NAME_2, FIXTURE_ROLE_DESCRIPTION_2, true);
        DomainRegistry.roleRepository().add(role1);
        DomainRegistry.roleRepository().add(role2);
        Group group1 = activeTenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group group2 = activeTenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        group2.addGroup(group1, DomainRegistry.groupMemberService());
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        group1.addGroup(role1.group(), DomainRegistry.groupMemberService());
        role1.assignGroup(group2, DomainRegistry.groupMemberService());
        this.expectedEvents(1);
        this.expectedEvent(GroupAssignedToRole.class, 1);
    }

    @Test
    public void assignUser() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME_1, FIXTURE_ROLE_DESCRIPTION_1, true);
        User user = this.userAggregate();

        assertNotNull(user);

        DomainRegistry.userRepository().add(user);
        role.assignUser(user);
        assertEquals(1, role.group().groupMembers().size());

        for (GroupMember next : role.group().groupMembers()) {
            assertEquals(GroupMemberType.User, next.type());
            assertEquals(user.userId().username(), next.name());
            assertEquals(user.userId().tenantId(), next.tenantId());
        }

        this.expectedEvents(4);
        this.expectedEvent(UserAssignedToRole.class, 1);
        this.expectedEvent(RoleProvisioned.class, 1);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(GroupUserAdded.class, 1);


    }

    @Test
    public void unassignUser() {

        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME_1, FIXTURE_ROLE_DESCRIPTION_1, true);
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        role.assignUser(user);
        assertEquals(1, role.group().groupMembers().size());
        for (GroupMember next : role.group().groupMembers()) {
            assertEquals(GroupMemberType.User, next.type());
            assertEquals(user.userId().username(), next.name());
            assertEquals(user.userId().tenantId(), next.tenantId());
        }
        role.unassignUser(user);
        assertEquals(0, role.group().groupMembers().size());
        this.expectedEvents(5);
        this.expectedEvent(UserAssignedToRole.class, 1);
        this.expectedEvent(UserUnassignedFromRole.class, 1);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(RoleProvisioned.class, 1);
        this.expectedEvent(GroupUserAdded.class, 1);

    }


    @Test
    public void isUserInRole() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();

        System.out.println("\n\n\n HERE MY USER ID == " + user.userId());
        System.out.println("\n\n\n HERE MY USER ID == " + user.userId());
        System.out.println("\n\n\n HERE MY USER ID == " + user.userId());

        DomainRegistry.userRepository().add(user);
        Role managerRole = tenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        Group group = new Group(user.userId().tenantId(), FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        group.addUser(user);
        DomainRegistry.groupRepository().add(group);
        managerRole.assignGroup(group, DomainRegistry.groupMemberService());
        DomainRegistry.roleRepository().add(managerRole);
        assertTrue(group.isMember(user, DomainRegistry.groupMemberService()));
        assertTrue(managerRole.isInRole(user, DomainRegistry.groupMemberService()));
    }

    @Test
    public void UserIsNotInRole() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Role managerRole = tenant.provisionRole(FIXTURE_ROLE_NAME_1, FIXTURE_ROLE_DESCRIPTION_1, true);
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        DomainRegistry.groupRepository().add(group);
        managerRole.assignGroup(group, DomainRegistry.groupMemberService());
        DomainRegistry.roleRepository().add(managerRole);
        Role accountantRole = new Role(user.userId().tenantId(), FIXTURE_ROLE_NAME_2, FIXTURE_ROLE_DESCRIPTION_2);
        DomainRegistry.roleRepository().add(accountantRole);

        assertFalse(managerRole.isInRole(user, DomainRegistry.groupMemberService()));
        assertFalse(accountantRole.isInRole(user, DomainRegistry.groupMemberService()));

    }

}
