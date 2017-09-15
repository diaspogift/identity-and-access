package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GroupTest extends IdentityAndAccessTest {

    public GroupTest() {
        super();
    }


    @Test
    @Rollback(false)
    public void createGroup() {

        Tenant tenant = this.actifTenantAggregate();
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);

        assertNotNull(group);
        DomainRegistry.groupRepository().add(group);
        assertEquals(tenant.tenantId(), group.tenantId());
        assertEquals(FIXTURE_GROUP_DESCRIPTION_1, group.description());
        assertEquals(FIXTURE_GROUP_NAME_1, group.name());
        assertEquals(0, group.groupMembers().size());
        assertEquals(1, DomainRegistry.groupRepository().allGroups(tenant.tenantId()).size());

        Group foundGroup = DomainRegistry.groupRepository().groupNamed(group.tenantId(), group.name());
        assertEquals(group, foundGroup);

    }

    @Test
    public void addGroupToGroup() {

        Tenant tenant = this.actifTenantAggregate();
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group nestGroup = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        group.addGroup(nestGroup, DomainRegistry.groupMemberService());
        DomainRegistry.groupRepository().add(group);
        DomainRegistry.groupRepository().add(nestGroup);
        assertEquals(1, group.groupMembers().size());
        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = nestGroup.toGroupMember();
        for (GroupMember groupMember : group.groupMembers()) {
            if (groupMember.equals(groupMemberExpected)) {
                foundGroupMember = groupMember;
                break;
            }
        }
        assertNotNull(foundGroupMember);
        assertEquals(GroupMemberType.Group, foundGroupMember.type());
        Group foundGroup = new Group(tenant.tenantId(), foundGroupMember.name(), FIXTURE_GROUP_DESCRIPTION_1);
        assertEquals(nestGroup, foundGroup);
        assertTrue(foundGroupMember.isGroup());
        this.expectedEvents(3);
        this.expectedEvent(GroupGroupAdded.class, 1);
        this.expectedEvent(GroupProvisioned.class, 2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addGroupToGroupWithWrongTenantId() {

        Tenant tenant = this.actifTenantAggregate();
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        Group nestGroupWithWrongTenantId = new Group(tenantId1, FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        group.addGroup(nestGroupWithWrongTenantId, DomainRegistry.groupMemberService());
    }


    @Test
    public void addGroupToGroupWithGoodRecurrsion() {

        Tenant tenant = this.actifTenantAggregate();
        Group group0 = tenant.provisionGroup(FIXTURE_GROUP_NAME_0, FIXTURE_GROUP_DESCRIPTION_0);
        Group group1 = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group group2 = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        Group group3 = tenant.provisionGroup(FIXTURE_GROUP_NAME_3, FIXTURE_GROUP_DESCRIPTION_3);
        Group group4 = tenant.provisionGroup(FIXTURE_GROUP_NAME_4, FIXTURE_GROUP_DESCRIPTION_4);

        DomainRegistry.groupRepository().add(group0);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);

        group0 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group0.name());
        group1 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group1.name());
        group2 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group2.name());
        group3 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group3.name());
        group4 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group4.name());

        group1.addGroup(group2, DomainRegistry.groupMemberService());
        group2.addGroup(group3, DomainRegistry.groupMemberService());
        group2.addGroup(group4, DomainRegistry.groupMemberService());
        group0.addGroup(group1, DomainRegistry.groupMemberService());


        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group0, group4.toGroupMember()));
        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group0, group3.toGroupMember()));
        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group0, group2.toGroupMember()));
        assertFalse(DomainRegistry.groupMemberService().isMemberGroup(group3, group4.toGroupMember()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void addGroupToGroupWithBadRecurrsion() {


        Tenant tenant = this.actifTenantAggregate();
        Group group0 = tenant.provisionGroup(FIXTURE_GROUP_NAME_0, FIXTURE_GROUP_DESCRIPTION_0);
        Group group1 = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group group2 = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        Group group3 = tenant.provisionGroup(FIXTURE_GROUP_NAME_3, FIXTURE_GROUP_DESCRIPTION_3);
        Group group4 = tenant.provisionGroup(FIXTURE_GROUP_NAME_4, FIXTURE_GROUP_DESCRIPTION_4);

        DomainRegistry.groupRepository().add(group0);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);

        group0 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group0.name());
        group1 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group1.name());
        group2 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group2.name());
        group3 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group3.name());
        group4 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group4.name());

        group1.addGroup(group2, DomainRegistry.groupMemberService());
        group2.addGroup(group3, DomainRegistry.groupMemberService());
        group2.addGroup(group4, DomainRegistry.groupMemberService());
        group0.addGroup(group1, DomainRegistry.groupMemberService());
        group4.addGroup(group0, DomainRegistry.groupMemberService());


        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group0, group4.toGroupMember()));
        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group0, group3.toGroupMember()));
        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group0, group2.toGroupMember()));
        assertFalse(DomainRegistry.groupMemberService().isMemberGroup(group3, group4.toGroupMember()));

    }


    @Test
    public void removeGroup() {

        Tenant tenant = this.actifTenantAggregate();
        Group group0 = tenant.provisionGroup(FIXTURE_GROUP_NAME_0, FIXTURE_GROUP_DESCRIPTION_0);
        Group group1 = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group group2 = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        Group group3 = tenant.provisionGroup(FIXTURE_GROUP_NAME_3, FIXTURE_GROUP_DESCRIPTION_3);
        Group group4 = tenant.provisionGroup(FIXTURE_GROUP_NAME_4, FIXTURE_GROUP_DESCRIPTION_4);

        DomainRegistry.groupRepository().add(group0);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);

        group0 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group0.name());
        group1 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group1.name());
        group2 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group2.name());
        group3 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group3.name());
        group4 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group4.name());

        group1.addGroup(group2, DomainRegistry.groupMemberService());
        group2.addGroup(group3, DomainRegistry.groupMemberService());
        group2.addGroup(group4, DomainRegistry.groupMemberService());
        group0.addGroup(group1, DomainRegistry.groupMemberService());

        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group2, group3.toGroupMember()));

        group2.removeGroup(group3);

        assertEquals(1, group2.groupMembers().size());

        assertFalse(DomainRegistry.groupMemberService().isMemberGroup(group2, group3.toGroupMember()));

    }


    @Test
    public void addUser() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        group.addUser(user);
        DomainRegistry.groupRepository().add(group);
        assertEquals(1, group.groupMembers().size());
        assertTrue(DomainRegistry.groupMemberService().confirmUser(group, user));
        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = user.toGroupMember();
        for (GroupMember groupMember : group.groupMembers()) {
            if (groupMember.equals(groupMemberExpected)) {
                foundGroupMember = groupMember;
                break;
            }
        }
        assertNotNull(foundGroupMember);
        assertEquals(tenant.tenantId(), foundGroupMember.tenantId());
        assertEquals(user.userId().tenantId(), foundGroupMember.tenantId());
        assertEquals(user.userId().username(), foundGroupMember.name());
        assertEquals(GroupMemberType.User, foundGroupMember.type());
        assertEquals(true, foundGroupMember.isUser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToGroupWithWrongTenantId() {

        Tenant tenant = this.actifTenantAggregate();
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        TenantId tenantId2 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        User user = new User(
                new UserId(tenantId2, FIXTURE_USERNAME_1),
                FIXTURE_PASSWORD,
                new Enablement(true, ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l)),
                new Person(
                        new FullName(FIXTURE_FIRST_NAME_2, FIXTURE_LAST_NAME_2),
                        new ContactInformation(
                                new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS_2),
                                new PostalAddress(FIXTURE_STREET_ADDRESS_2, FIXTURE_CITY_2, FIXTURE_STATE_2, FIXTURE_POSTALCODE_1, FIXTURE_COUNTRY_CODE_2),
                                new Telephone(FIXTURE_COUNTRY_CODE_2, FIXTURE_COUNTRY_DAILING_CODE_2, FIXTURE_PHONE_NUMBER_2),
                                new Telephone(FIXTURE_COUNTRY_CODE_2, FIXTURE_COUNTRY_DAILING_CODE_2, FIXTURE_PHONE_NUMBER_2_2)
                        )));
        group.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addANonEnabledUserToGroup() {

        Tenant tenant = this.actifTenantAggregate();
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);


        User user = new User(
                new UserId(tenant.tenantId(), FIXTURE_USERNAME_1),
                FIXTURE_PASSWORD,
                new Enablement(false, ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l)),
                new Person(
                        new FullName(FIXTURE_FIRST_NAME_2, FIXTURE_LAST_NAME_2),
                        new ContactInformation(
                                new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS_2),
                                new PostalAddress(FIXTURE_STREET_ADDRESS_2, FIXTURE_CITY_2, FIXTURE_STATE_2, FIXTURE_POSTALCODE_1, FIXTURE_COUNTRY_CODE_2),
                                new Telephone(FIXTURE_COUNTRY_CODE_2, FIXTURE_COUNTRY_DAILING_CODE_2, FIXTURE_PHONE_NUMBER_2),
                                new Telephone(FIXTURE_COUNTRY_CODE_2, FIXTURE_COUNTRY_DAILING_CODE_2, FIXTURE_PHONE_NUMBER_2_2)
                        )));

        group.addUser(user);
    }

    @Test
    public void removeUser() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        group.addUser(user);
        DomainRegistry.groupRepository().add(group);
        assertTrue(DomainRegistry.groupMemberService().confirmUser(group, user));
        assertEquals(1, group.groupMembers().size());
        group = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group.name());
        group.removeUser(user);
        assertEquals(0, group.groupMembers().size());
    }


    @Test
    public void addGroupToGroupEvent() {

        Tenant tenant = this.actifTenantAggregate();
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group nestGroup = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        group.addGroup(nestGroup, DomainRegistry.groupMemberService());
        DomainRegistry.groupRepository().add(group);
        DomainRegistry.groupRepository().add(nestGroup);
        assertEquals(1, group.groupMembers().size());

        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = nestGroup.toGroupMember();
        for (GroupMember groupMember : group.groupMembers()) {
            if (groupMember.equals(groupMemberExpected)) {
                foundGroupMember = groupMember;
                break;
            }
        }

        assertNotNull(foundGroupMember);
        assertEquals(GroupMemberType.Group, foundGroupMember.type());

        Group foundGroup = new Group(tenant.tenantId(), foundGroupMember.name(), FIXTURE_GROUP_DESCRIPTION_1);
        assertEquals(nestGroup, foundGroup);
        assertTrue(foundGroupMember.isGroup());

        this.expectedEvents(3);
        this.expectedEvent(GroupGroupAdded.class, 1);
        this.expectedEvent(GroupProvisioned.class, 2);

    }


    @Test
    public void addUserEvent() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        group.addUser(user);
        DomainRegistry.groupRepository().add(group);
        assertEquals(1, group.groupMembers().size());
        assertTrue(DomainRegistry.groupMemberService().confirmUser(group, user));
        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = user.toGroupMember();
        for (GroupMember groupMember : group.groupMembers()) {
            if (groupMember.equals(groupMemberExpected)) {
                foundGroupMember = groupMember;
                break;
            }
        }
        assertNotNull(foundGroupMember);
        assertEquals(tenant.tenantId(), foundGroupMember.tenantId());
        assertEquals(user.userId().tenantId(), foundGroupMember.tenantId());
        assertEquals(user.userId().username(), foundGroupMember.name());
        assertEquals(GroupMemberType.User, foundGroupMember.type());
        assertEquals(true, foundGroupMember.isUser());

        this.expectedEvents(3);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(GroupProvisioned.class, 1);
        this.expectedEvent(GroupUserAdded.class, 1);
    }


    @Test
    public void removeGroupEvent() {
        Tenant tenant = this.actifTenantAggregate();
        Group group0 = tenant.provisionGroup(FIXTURE_GROUP_NAME_0, FIXTURE_GROUP_DESCRIPTION_0);
        Group group1 = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        Group group2 = tenant.provisionGroup(FIXTURE_GROUP_NAME_2, FIXTURE_GROUP_DESCRIPTION_2);
        Group group3 = tenant.provisionGroup(FIXTURE_GROUP_NAME_3, FIXTURE_GROUP_DESCRIPTION_3);
        Group group4 = tenant.provisionGroup(FIXTURE_GROUP_NAME_4, FIXTURE_GROUP_DESCRIPTION_4);

        DomainRegistry.groupRepository().add(group0);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);

        group0 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group0.name());
        group1 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group1.name());
        group2 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group2.name());
        group3 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group3.name());
        group4 = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group4.name());


        group1.addGroup(group2, DomainRegistry.groupMemberService());
        group2.addGroup(group3, DomainRegistry.groupMemberService());
        group2.addGroup(group4, DomainRegistry.groupMemberService());
        group0.addGroup(group1, DomainRegistry.groupMemberService());

        assertTrue(DomainRegistry.groupMemberService().isMemberGroup(group2, group3.toGroupMember()));

        group2.removeGroup(group3);

        assertEquals(1, group2.groupMembers().size());

        assertFalse(DomainRegistry.groupMemberService().isMemberGroup(group2, group3.toGroupMember()));

        this.expectedEvents(10);
        this.expectedEvent(GroupProvisioned.class, 5);
        this.expectedEvent(GroupGroupAdded.class, 4);
        this.expectedEvent(GroupGroupRemoved.class, 1);

    }


    @Test
    public void removeUserEvent() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Group group = tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);
        group.addUser(user);
        DomainRegistry.groupRepository().add(group);
        assertTrue(DomainRegistry.groupMemberService().confirmUser(group, user));
        assertEquals(1, group.groupMembers().size());
        group = DomainRegistry.groupRepository().groupNamed(tenant.tenantId(), group.name());
        group.removeUser(user);
        assertEquals(0, group.groupMembers().size());

        this.expectedEvents(4);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(GroupProvisioned.class, 1);
        this.expectedEvent(GroupUserAdded.class, 1);
        this.expectedEvent(GroupUserRemoved.class, 1);

    }

}
