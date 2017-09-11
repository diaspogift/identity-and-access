package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleProvisioned;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventSubscriber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TenantTest extends IdentityAndAccessTest {

    public TenantTest() {
        super();
    }




    @Test
    public void createTenantId() {

        String id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId = new TenantId(id);
        assertNotNull(tenantId);
        assertEquals(id, tenantId.id());
    }

    @Test
    public void createActiveTenant() {

        Tenant activeTenant = this.actifTenantAggregate();
        assertNotNull(activeTenant);
        Tenant foundTenant = DomainRegistry.tenantRepository().tenantOfId(activeTenant.tenantId());
        assertNotNull(foundTenant);
        assertEquals(activeTenant, foundTenant);
        assertTrue(activeTenant.isActive());
    }

    @Test
    public void createNonActiveTenant() {

        Tenant activeTenant = this.nonActifTenantAggregate();
        assertNotNull(activeTenant);
        Tenant foundTenant = DomainRegistry.tenantRepository().tenantOfId(activeTenant.tenantId());
        assertNotNull(foundTenant);
        assertEquals(activeTenant, foundTenant);
        assertFalse(activeTenant.isActive());
    }

    @Test
    public void activate() {

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TenantActivated>() {

            @Override
            public void handleEvent(TenantActivated aDomainEvent) {

                tenantActivatedHandled = true;

            }

            @Override
            public Class<TenantActivated> subscribedToEventType() {
                return TenantActivated.class;
            }
        });
        Tenant tenant = this.nonActifTenantAggregate();
        assertFalse(tenant.isActive());
        tenant.activate();
        assertTrue(tenant.isActive());
        assertTrue(this.istenantActivatedHandled());
    }

    @Test
    public void deactivate() {

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TenantDeactivated>() {

            @Override
            public void handleEvent(TenantDeactivated aDomainEvent) {
                tenantDeactivatedHandled = true;

            }

            @Override
            public Class<TenantDeactivated> subscribedToEventType() {
                return TenantDeactivated.class;
            }
        });
        Tenant tenant = this.actifTenantAggregate();
        assertTrue(tenant.isActive());
        tenant.deactivate();
        assertFalse(tenant.isActive());
        assertTrue(this.istenantDeactivatedHandled());
    }

    @Test
    public void allAvailableRegistrationInvitations() {

        Tenant tenant = this.tenantAggregateWithOfferedRegistrationInvitations();
        Collection<InvitationDescriptor> allAvailableRegistrationInvitations = tenant.allAvailableRegistrationInvitations();
        assertNotNull(allAvailableRegistrationInvitations);
        assertEquals(2, allAvailableRegistrationInvitations.size());
        assertTrue(allAvailableRegistrationInvitations.contains(this.registrationInvitation1().toDescriptor()));
        assertTrue(allAvailableRegistrationInvitations.contains(this.registrationInvitation2().toDescriptor()));
    }

    @Test(expected = IllegalStateException.class)
    public void allAvailableRegistrationInvitationsWithDeactivatedTenant() {

        Tenant tenant = this.nonActifTenantAggregateWithOfferedRegistrationInvitations();
        Collection<InvitationDescriptor> allAvailableRegistrationInvitations = tenant.allAvailableRegistrationInvitations();
        assertNotNull(allAvailableRegistrationInvitations);
        assertEquals(2, allAvailableRegistrationInvitations.size());
        assertTrue(allAvailableRegistrationInvitations.contains(this.registrationInvitation1().toDescriptor()));
        assertTrue(allAvailableRegistrationInvitations.contains(this.registrationInvitation2().toDescriptor()));
    }

    @Test
    public void allUnvailableRegistrationInvitations() {

        Tenant tenant = this.tenantAggregateWithOfferedRegistrationInvitations();
        Collection<InvitationDescriptor> allUnavailableRegistrationInvitations = tenant.allUnavailableRegistrationInvitations();
        assertNotNull(allUnavailableRegistrationInvitations);
        assertEquals(1, allUnavailableRegistrationInvitations.size());
        assertTrue(allUnavailableRegistrationInvitations.contains(this.registrationInvitation3().toDescriptor()));
    }

    @Test(expected = IllegalStateException.class)
    public void allUnvailableRegistrationInvitationsWithDeactivatedTenant() {

        Tenant tenant = this.nonActifTenantAggregateWithOfferedRegistrationInvitations();
        Collection<InvitationDescriptor> allUnavailableRegistrationInvitations = tenant.allUnavailableRegistrationInvitations();
        assertNotNull(allUnavailableRegistrationInvitations);
        assertEquals(1, allUnavailableRegistrationInvitations.size());
        assertTrue(allUnavailableRegistrationInvitations.contains(this.registrationInvitation3().toDescriptor()));
    }


    @Test
    public void isRegistrationAvailableThrough() {

        Tenant tenant = this.tenantAggregateWithOfferedRegistrationInvitations();
        assertTrue(tenant.isRegistrationAvailableThrough(this.registrationInvitation1().invitationId()));
        assertTrue(tenant.isRegistrationAvailableThrough(this.registrationInvitation2().invitationId()));
        assertTrue(tenant.isRegistrationAvailableThrough(this.registrationInvitation1().description()));
        assertTrue(tenant.isRegistrationAvailableThrough(this.registrationInvitation2().description()));
    }

    @Test(expected = IllegalStateException.class)
    public void isRegistrationAvailableThrough_ForDeactivatedTenant() {

        Tenant tenant = this.nonActifTenantAggregateWithOfferedRegistrationInvitations();
        assertFalse(tenant.isRegistrationAvailableThrough(this.registrationInvitation1().invitationId()));
        assertFalse(tenant.isRegistrationAvailableThrough(this.registrationInvitation2().invitationId()));
        assertFalse(tenant.isRegistrationAvailableThrough(this.registrationInvitation3().invitationId()));
        assertFalse(tenant.isRegistrationAvailableThrough(this.registrationInvitation1().description()));
        assertFalse(tenant.isRegistrationAvailableThrough(this.registrationInvitation2().description()));
        assertFalse(tenant.isRegistrationAvailableThrough(this.registrationInvitation3().description()));
    }


    @Test
    public void offerRegistrationInvitation() {

        Tenant tenant = this.actifTenantAggregate();
        RegistrationInvitation registrationInvitation =
                tenant.offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1);
        assertNotNull(registrationInvitation);
        assertEquals(1, tenant.registrationInvitations().size());
        assertEquals(tenant.tenantId(), registrationInvitation.tenantId());
        assertEquals(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1, registrationInvitation.description());
        assertTrue(tenant.registrationInvitations().contains(registrationInvitation));
    }


    @Test
    public void provisionGroup() {

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupProvisioned>() {


            @Override
            public void handleEvent(GroupProvisioned aDomainEvent) {
                groupProvisionedHandled = true;
            }

            @Override
            public Class<GroupProvisioned> subscribedToEventType() {
                return GroupProvisioned.class;
            }
        });
        Tenant tenant = this.actifTenantAggregate();
        Group group =
                tenant.provisionGroup(FIXTURE_GROUP_NAME, FIXTURE_GROUP_DESCRIPTION);
        assertNotNull(group);
        assertEquals(FIXTURE_GROUP_NAME, group.name());
        assertEquals(tenant.tenantId(), group.tenantId());
        assertEquals(FIXTURE_GROUP_DESCRIPTION, group.description());
        assertTrue(this.isgroupProvisionedHandled());
    }


    @Test
    public void provisionRole() {

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<RoleProvisioned>() {


            @Override
            public void handleEvent(RoleProvisioned aDomainEvent) {
                roleProvisionedHandled = true;
            }

            @Override
            public Class<RoleProvisioned> subscribedToEventType() {
                return RoleProvisioned.class;
            }
        });
        Tenant tenant = this.actifTenantAggregate();
        Role role = tenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        assertNotNull(role);
        assertEquals(FIXTURE_ROLE_NAME, role.name());
        assertEquals(tenant.tenantId(), role.tenantId());
        assertEquals(FIXTURE_ROLE_DESCRIPTION, role.description());
        assertTrue(this.isroleProvisionedHandled());
    }

    @Test
    public void registerUser() {

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<UserRegistered>() {


            @Override
            public void handleEvent(UserRegistered aDomainEvent) {
                userRegisteredHandled = true;
            }

            @Override
            public Class<UserRegistered> subscribedToEventType() {
                return UserRegistered.class;
            }
        });
        Tenant tenant = this.actifTenantAggregate();
        RegistrationInvitation registrationInvitation =
                tenant.offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1);
        User user =
                tenant.registerUser(
                        registrationInvitation.invitationId(),
                        FIXTURE_USERNAME_1,
                        FIXTURE_PASSWORD,
                        new Enablement(),
                        new Person(
                                tenant.tenantId(),
                                new FullName(FIXTURE_FIRST_NAME_1, FIXTURE_LAST_NAME_1),
                                new ContactInformation(
                                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS_1),
                                        new PostalAddress(FIXTURE_STREET_ADDRESS_1, FIXTURE_CITY_1, FIXTURE_STATE_1, FIXTURE_POSTALCODE_1, FIXTURE_COUNTRY_CODE_1),
                                        new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1),
                                        new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1_1)
                                )));
        assertNotNull(user);
        assertEquals(tenant.tenantId(), user.tenantId());
        assertEquals(tenant.tenantId(), user.person().tenantId());
        assertTrue(this.isuserRegisteredHandled());
    }

    @Test
    public void redefineRegistrationInvitationAsOpenEnded() {

        Tenant tenant = this.tenantAggregateWithOfferedRegistrationInvitations();
        for (RegistrationInvitation next : tenant.registrationInvitations()) {
            if (next.equals(this.registrationInvitation3())) {
                assertNotNull(next.startingOn());
            }
        }
        tenant.redefineRegistrationInvitationAsOpenEnded(this.registrationInvitation3().invitationId());

        for (RegistrationInvitation next : tenant.registrationInvitations()) {
            if (next.equals(this.registrationInvitation3())) {
                assertNull(next.startingOn());
                assertNull(next.until());
            }
        }
    }


    @Test
    public void withdrawInvitation() {

        Tenant tenant = this.tenantAggregateWithOfferedRegistrationInvitations();
        assertTrue(tenant.registrationInvitations().contains(this.registrationInvitation1()));
        assertEquals(3, tenant.registrationInvitations().size());
        tenant.withdrawInvitation(this.registrationInvitation1().invitationId());
        assertEquals(2, tenant.registrationInvitations().size());
        assertFalse(tenant.registrationInvitations().contains(this.registrationInvitation1()));
    }

}
