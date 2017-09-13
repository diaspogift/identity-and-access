package com.diaspogift.identityandaccess.application.identity;


import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;
import com.diaspogift.identityandaccess.application.ApplicationServiceTests;
import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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
                        parentGroup.tenantId().id(),
                        parentGroup.name(),
                        childGroup.name()));

        assertEquals(1, parentGroup.groupMembers().size());

        Group savedParentGroup =
                DomainRegistry.groupRepository().
                        groupNamed(parentGroup.tenantId(), parentGroup.name());

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
                        childGroup.tenantId().id(),
                        childGroup.name(),
                        user.username()));

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
                                user.tenantId().id(),
                                user.username(),
                                FIXTURE_PASSWORD));

        assertNotNull(userDescriptor);
        assertEquals(user.tenantId(), userDescriptor.tenantId());
        assertEquals(user.username(), userDescriptor.username());
        assertEquals(user.person().contactInformation().emailAddress(), new EmailAddress(userDescriptor.emailAddress()));
    }

    @Test
    public void changeUserContactInformation() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("mynewemailaddress@saasovation.com", user.person().emailAddress().address());
        assertNotEquals("777-555-1211", user.person().contactInformation().primaryTelephone().number());
        assertNotEquals("777-555-1212", user.person().contactInformation().secondaryTelephone().number());
        assertNotEquals("123 Pine Street", user.person().contactInformation().postalAddress().streetAddress());
        assertNotEquals("Loveland", user.person().contactInformation().postalAddress().city());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserContactInformation(
                        new ChangeContactInfoCommand(
                                user.tenantId().id(),
                                user.username(),
                                "mynewemailaddress@saasovation.com",
                                "US",
                                "001",
                                "777-555-1211",
                                "US",
                                "001",
                                "777-555-1212",
                                "123 Pine Street",
                                "Loveland",
                                "CO",
                                "80771",
                                "US"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.tenantId(),
                                user.username());

        assertNotNull(changedUser);
        assertEquals("mynewemailaddress@saasovation.com", changedUser.person().emailAddress().address());
        assertEquals("777-555-1211", changedUser.person().contactInformation().primaryTelephone().number());
        assertEquals("777-555-1212", changedUser.person().contactInformation().secondaryTelephone().number());
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
                                user.tenantId().id(),
                                user.username(),
                                "mynewemailaddress@saasovation.com"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.tenantId(),
                                user.username());

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
                                user.tenantId().id(),
                                user.username(),
                                "123 Pine Street",
                                "Loveland",
                                "CO",
                                "80771",
                                "US"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.tenantId(),
                                user.username());

        assertNotNull(changedUser);
        assertEquals("123 Pine Street", changedUser.person().contactInformation().postalAddress().streetAddress());
        assertEquals("Loveland", changedUser.person().contactInformation().postalAddress().city());
    }

    @Test
    public void changeUserPrimaryTelephone() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("777-555-1211", user.person().contactInformation().primaryTelephone().number());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserPrimaryTelephone(
                        new ChangePrimaryTelephoneCommand(
                                user.tenantId().id(),
                                user.username(),
                                "US",
                                "001",
                                "777-555-1211"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.tenantId(),
                                user.username());

        assertNotNull(changedUser);
        assertEquals("777-555-1211", changedUser.person().contactInformation().primaryTelephone().number());
    }

    @Test
    public void changeUserSecondaryTelephone() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertNotEquals("777-555-1212", user.person().contactInformation().secondaryTelephone().number());

        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserSecondaryTelephone(
                        new ChangeSecondaryTelephoneCommand(
                                user.tenantId().id(),
                                user.username(),
                                "US",
                                "001",
                                "777-555-1212"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.tenantId(),
                                user.username());

        assertNotNull(changedUser);
        assertEquals("777-555-1212", changedUser.person().contactInformation().secondaryTelephone().number());
    }

    @Test
    public void changeUserPassword() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        assertEquals(user.internalAccessOnlyEncryptedPassword(), DomainRegistry.encryptionService().encryptedValue(FIXTURE_PASSWORD));
        ApplicationServiceRegistry
                .identityApplicationService()
                .changeUserPassword(
                        new ChangeUserPasswordCommand(
                                user.tenantId().id(),
                                user.username(),
                                FIXTURE_PASSWORD,
                                "THIS.IS.FELICIEN'S.NEW.PASSWORD"));

        UserDescriptor userDescriptor =
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .authenticateUser(new AuthenticateUserCommand(
                                user.tenantId().id(),
                                user.username(),
                                "THIS.IS.FELICIEN'S.NEW.PASSWORD"));

        assertNotNull(userDescriptor);
        assertEquals(user.username(), userDescriptor.username());
        assertEquals(user.internalAccessOnlyEncryptedPassword(), DomainRegistry.encryptionService().encryptedValue("THIS.IS.FELICIEN'S.NEW.PASSWORD"));

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
                                user.tenantId().id(),
                                user.username(),
                                "World",
                                "Peace"));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.tenantId(),
                                user.username());

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
                                user.tenantId().id(),
                                user.username(),
                                true,
                                now,
                                tomorrow));

        User changedUser =
                DomainRegistry
                        .userRepository()
                        .userWithUsername(
                                user.tenantId(),
                                user.username());

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
                                parentGroup.tenantId().id(),
                                parentGroup.name(),
                                user.username()));

        assertTrue(
                ApplicationServiceRegistry
                        .identityApplicationService()
                        .isGroupMember(
                                childGroup.tenantId().id(),
                                childGroup.name(),
                                user.username()));
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
                        parentGroup.tenantId().id(),
                        parentGroup.name(),
                        childGroup.name()));

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
                        childGroup.tenantId().id(),
                        childGroup.name(),
                        user.username()));

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
                        .user(user.tenantId().id(), user.username());

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
                        .userDescriptor(user.tenantId().id(), user.username());

        assertNotNull(user);
        assertEquals(user.userDescriptor(), foundUserDescriptor);
    }


}
