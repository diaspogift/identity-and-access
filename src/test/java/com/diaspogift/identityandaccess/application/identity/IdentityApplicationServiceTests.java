package com.diaspogift.identityandaccess.application.identity;


import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;
import com.diaspogift.identityandaccess.application.ApplicationServiceTests;
import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class IdentityApplicationServiceTests extends ApplicationServiceTests {

    @Test
    public void activateTenant() throws Exception {

        Tenant tenant = this.activeTenantAggregate();
        tenant.deactivate();
        assertFalse(tenant.isActive());

        ApplicationServiceRegistry
                .identityApplicationService()
                .activateTenant(new ActivateTenantCommand(tenant.tenantId().id()));

        Tenant changedTenant = DomainRegistry.tenantRepository().tenantOfId(tenant.tenantId());

        assertNotNull(changedTenant);
        assertEquals(tenant.tenantId(), changedTenant.tenantId());
        assertEquals(tenant.name(), changedTenant.name());
        assertTrue(changedTenant.isActive());
    }

    @Test
    public void deactivateTenant() throws Exception {
        Tenant tenant = this.activeTenantAggregate();
        assertTrue(tenant.isActive());

        ApplicationServiceRegistry
                .identityApplicationService()
                .deactivateTenant(new DeactivateTenantCommand(tenant.tenantId().id()));

        Tenant changedTenant = DomainRegistry.tenantRepository().tenantOfId(tenant.tenantId());

        assertNotNull(changedTenant);
        assertEquals(tenant.name(), changedTenant.name());
        assertFalse(changedTenant.isActive());
    }


    @Test
    public void addGroupToGroup() throws Exception {
        Group parentGroup = this.group1Aggregate();
        DomainRegistry.groupRepository().add(parentGroup);

        Group childGroup = this.group2Aggregate();
        DomainRegistry.groupRepository().add(childGroup);

        assertEquals(0, parentGroup.groupMembers().size());

        ApplicationServiceRegistry
                .identityApplicationService()
                .addGroupToGroup(new AddGroupToGroupCommand(
                        parentGroup.groupId().tenantId().id(),
                        parentGroup.groupId().name(),
                        childGroup.groupId().name()));

        assertEquals(1, parentGroup.groupMembers().size());

        Group savedParentGroup =
                DomainRegistry.groupRepository().
                        groupNamed(parentGroup.groupId().tenantId(), parentGroup.groupId().name());

        assertEquals(1, savedParentGroup.groupMembers().size());

    }

    @Test
    public void addUserToGroup() throws Exception {

        Group parentGroup = this.group1Aggregate();
        DomainRegistry.groupRepository().add(parentGroup);

        Group childGroup = this.group2Aggregate();
        DomainRegistry.groupRepository().add(childGroup);

        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertEquals(0, parentGroup.groupMembers().size());
        assertEquals(0, childGroup.groupMembers().size());

        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());

        ApplicationServiceRegistry
                .identityApplicationService()
                .addUserToGroup(new AddUserToGroupCommand(
                        childGroup.groupId().tenantId().id(),
                        childGroup.groupId().name(),
                        user.userId().username()));

        assertEquals(1, parentGroup.groupMembers().size());
        assertEquals(1, childGroup.groupMembers().size());
        assertTrue(parentGroup.isMember(user, DomainRegistry.groupMemberService()));
        assertTrue(childGroup.isMember(user, DomainRegistry.groupMemberService()));
    }

    @Test
    public void authenticateUser() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        UserDescriptor userDescriptor =
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .authenticateUser(new AuthenticateUserCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                FIXTURE_PASSWORD));

        assertNotNull(userDescriptor);
        assertEquals(user.userId().tenantId(), userDescriptor.tenantId());
        assertEquals(user.userId().username(), userDescriptor.username());
        assertEquals(user.person().contactInformation().emailAddress(), new EmailAddress(userDescriptor.emailAddress()));
    }

    @Test
    public void changeUserContactInformation() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("mynewemailaddress@saasovation.com", user.person().emailAddress().address());
        assertNotEquals("805-555-1211", user.person().contactInformation().primaryTelephone().number());
        assertNotEquals("777-555-1212", user.person().contactInformation().secondaryTelephone().number());
        assertNotEquals("123 Pine Street", user.person().contactInformation().postalAddress().streetAddress());
        assertNotEquals("Loveland", user.person().contactInformation().postalAddress().city());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserContactInformation(
                        new ChangeContactInfoCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                "mynewemailaddress@saasovation.com",
                                "US",
                                "001",
                                "805-555-1211",
                                "US",
                                "001",
                                "805-555-1212",
                                "123 Pine Street",
                                "Loveland",
                                "CO",
                                "80771",
                                "US"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.userId().tenantId(),
                                user.userId().username());

        assertNotNull(changedUser);
        assertEquals("mynewemailaddress@saasovation.com", changedUser.person().emailAddress().address());
        assertEquals("805-555-1211", changedUser.person().contactInformation().primaryTelephone().number());
        assertEquals("805-555-1212", changedUser.person().contactInformation().secondaryTelephone().number());
        assertEquals("123 Pine Street", changedUser.person().contactInformation().postalAddress().streetAddress());
        assertEquals("Loveland", changedUser.person().contactInformation().postalAddress().city());
    }


    @Test
    public void changeUserEmailAddress() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("mynewemailaddress@saasovation.com", user.person().emailAddress().address());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserEmailAddress(
                        new ChangeEmailAddressCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                "mynewemailaddress@saasovation.com"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.userId().tenantId(),
                                user.userId().username());

        assertNotNull(changedUser);
        assertEquals("mynewemailaddress@saasovation.com", changedUser.person().emailAddress().address());
    }

    @Test
    public void testChangeUserPostalAddress() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("123 Pine Street", user.person().contactInformation().postalAddress().streetAddress());
        assertNotEquals("Loveland", user.person().contactInformation().postalAddress().city());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserPostalAddress(
                        new ChangePostalAddressCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                "123 Pine Street",
                                "Loveland",
                                "CO",
                                "80771",
                                "US"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.userId().tenantId(),
                                user.userId().username());

        assertNotNull(changedUser);
        assertEquals("123 Pine Street", changedUser.person().contactInformation().postalAddress().streetAddress());
        assertEquals("Loveland", changedUser.person().contactInformation().postalAddress().city());
    }

    @Test
    public void changeUserPrimaryTelephone() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("805-555-1211", user.person().contactInformation().primaryTelephone().number());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserPrimaryTelephone(
                        new ChangePrimaryTelephoneCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                "US",
                                "001",
                                "805-555-1211"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.userId().tenantId(),
                                user.userId().username());

        assertNotNull(changedUser);
        assertEquals("805-555-1211", changedUser.person().contactInformation().primaryTelephone().number());
    }

    @Test
    public void changeUserSecondaryTelephone() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("805-555-1212", user.person().contactInformation().secondaryTelephone().number());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserSecondaryTelephone(
                        new ChangeSecondaryTelephoneCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                "US",
                                "001",
                                "805-555-1212"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.userId().tenantId(),
                                user.userId().username());

        assertNotNull(changedUser);
        assertEquals("805-555-1212", changedUser.person().contactInformation().secondaryTelephone().number());
    }

    @Test
    public void changeUserPassword() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertTrue(DomainRegistry.encryptionService().matchesPassword(FIXTURE_PASSWORD, user.internalAccessOnlyEncryptedPassword()));
        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserPassword(
                        new ChangeUserPasswordCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                FIXTURE_PASSWORD,
                                "THIS.IS.FELICIEN'S.NEW.PASSWORD"));

        UserDescriptor userDescriptor =
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .authenticateUser(new AuthenticateUserCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                "THIS.IS.FELICIEN'S.NEW.PASSWORD"));

        assertNotNull(userDescriptor);
        assertEquals(user.userId().username(), userDescriptor.username());
        assertTrue(DomainRegistry.encryptionService().matchesPassword("THIS.IS.FELICIEN'S.NEW.PASSWORD", user.internalAccessOnlyEncryptedPassword()));

    }

    @Test
    public void changeUserPersonalName() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        assertNotEquals("World Peace", user.person().name().asFormattedName());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserPersonalName(
                        new ChangeUserPersonalNameCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                "World",
                                "Peace"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.userId().tenantId(),
                                user.userId().username());

        assertNotNull(changedUser);
        assertEquals("World Peace", changedUser.person().name().asFormattedName());
    }


    //TO DO SHOULD NOT PASS
    @Test
    public void testDefineUserEnablement() throws Exception {
        User user = this.disabledUserAggregate();

        assertFalse(user.isEnabled());

        DomainRegistry.userRepository().add(user);

        ZonedDateTime now = ZonedDateTime.now().minusDays(1l);
        ZonedDateTime tomorrow = ZonedDateTime.now().plusDays(1l);

        ApplicationServiceRegistry
                .identityApplicationService()
                .defineUserEnablement(
                        new DefineUserEnablementCommand(
                                user.userId().tenantId().id(),
                                user.userId().username(),
                                true,
                                now,
                                tomorrow));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.userId().tenantId(),
                                user.userId().username());

        assertNotNull(changedUser);
        assertTrue(changedUser.isEnabled());
    }

    @Test
    public void isGroupMember() throws Exception {
        Group parentGroup = this.group1Aggregate();
        DomainRegistry.groupRepository().add(parentGroup);

        Group childGroup = this.group2Aggregate();
        DomainRegistry.groupRepository().add(childGroup);

        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertEquals(0, parentGroup.groupMembers().size());
        assertEquals(0, childGroup.groupMembers().size());

        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());
        childGroup.addUser(user);

        assertTrue(
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .isGroupMember(
                                parentGroup.groupId().tenantId().id(),
                                parentGroup.groupId().name(),
                                user.userId().username()));

        assertTrue(
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .isGroupMember(
                                childGroup.groupId().tenantId().id(),
                                childGroup.groupId().name(),
                                user.userId().username()));
    }

    @Test
    public void removeGroupFromGroup() throws Exception {
        Group parentGroup = this.group1Aggregate();
        DomainRegistry.groupRepository().add(parentGroup);

        Group childGroup = this.group2Aggregate();
        DomainRegistry.groupRepository().add(childGroup);

        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());

        assertEquals(1, parentGroup.groupMembers().size());


        ApplicationServiceRegistry
                .identityApplicationService()
                .removeGroupFromGroup(new RemoveGroupFromGroupCommand(
                        parentGroup.groupId().tenantId().id(),
                        parentGroup.groupId().name(),
                        childGroup.groupId().name()));

        assertEquals(0, parentGroup.groupMembers().size());
    }

    @Test
    public void removeUserFromGroup() throws Exception {
        Group parentGroup = this.group1Aggregate();
        DomainRegistry.groupRepository().add(parentGroup);

        Group childGroup = this.group2Aggregate();
        DomainRegistry.groupRepository().add(childGroup);

        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());
        childGroup.addUser(user);

        assertEquals(1, parentGroup.groupMembers().size());
        assertEquals(1, childGroup.groupMembers().size());
        assertTrue(parentGroup.isMember(user, DomainRegistry.groupMemberService()));
        assertTrue(childGroup.isMember(user, DomainRegistry.groupMemberService()));

        ApplicationServiceRegistry
                .identityApplicationService()
                .removeUserFromGroup(new RemoveUserFromGroupCommand(
                        childGroup.groupId().tenantId().id(),
                        childGroup.groupId().name(),
                        user.userId().username()));

        assertEquals(1, parentGroup.groupMembers().size());
        assertEquals(0, childGroup.groupMembers().size());
        assertFalse(parentGroup.isMember(user, DomainRegistry.groupMemberService()));
        assertFalse(childGroup.isMember(user, DomainRegistry.groupMemberService()));
    }

    @Test
    public void tenant() throws Exception {
        Tenant tenant = this.activeTenantAggregate();

        Tenant foundTenant =
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .tenant(tenant.tenantId().id());

        assertNotNull(foundTenant);
        assertEquals(tenant, foundTenant);
    }

    @Test
    public void user() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        User foundUser =
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .user(user.userId().tenantId().id(), user.userId().username());

        assertNotNull(user);
        assertEquals(user, foundUser);
    }

    @Test
    public void userDescriptor() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        UserDescriptor foundUserDescriptor =
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .userDescriptor(user.userId().tenantId().id(), user.userId().username());

        assertNotNull(user);
        assertEquals(user.userDescriptor(), foundUserDescriptor);
    }

    //
    @Test
    //TO DO
    @Rollback(false)
    public void provisionTenant() throws Exception {

        ProvisionTenantCommand provisionTenantCommand =
                new ProvisionTenantCommand("BINGO", "HOPITAL BINGO", "Bingo Admin", "Bingo", "didier@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques", "Douala", "Littoral", "80209",
                        "US");

        ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand);

    }


}
