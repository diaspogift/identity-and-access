package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleProvisioned;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

        Tenant foundTenant =
                DomainRegistry.tenantRepository()
                        .tenantOfId(activeTenant.tenantId());

        assertNotNull(foundTenant);
        assertEquals(activeTenant, foundTenant);
        assertTrue(activeTenant.isActive());
    }

    @Test
    public void createNonActiveTenant() {

        Tenant activeTenant = this.nonActifTenantAggregate();

        assertNotNull(activeTenant);

        Tenant foundTenant =
                DomainRegistry.tenantRepository()
                        .tenantOfId(activeTenant.tenantId());

        assertNotNull(foundTenant);
        assertEquals(activeTenant, foundTenant);
        assertFalse(activeTenant.isActive());
    }

    @Test
    public void activate() {

        Tenant tenant = this.nonActifTenantAggregate();

        assertFalse(tenant.isActive());

        tenant.activate();

        assertTrue(tenant.isActive());
        this.expectedEvent(TenantActivated.class, 1);
        this.expectedEvents(1);
    }

    @Test
    public void deactivate() {

        Tenant tenant = this.actifTenantAggregate();

        assertTrue(tenant.isActive());

        tenant.deactivate();

        assertFalse(tenant.isActive());
        this.expectedEvent(TenantDeactivated.class, 1);
        this.expectedEvents(1);
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
    public void isRegistrationAvailableThroughForDeactivatedTenant() {

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
                tenant.offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1, new EmailAddress("didnkallaehawe@gmail.com"));

        assertNotNull(registrationInvitation);

        assertEquals(1, tenant.registrationInvitations().size());
        assertEquals(tenant.tenantId(), registrationInvitation.tenantId());
        assertEquals(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1, registrationInvitation.description());
        assertTrue(tenant.registrationInvitations().contains(registrationInvitation));
    }


    @Test
    public void provisionGroup() {

        Tenant tenant = this.actifTenantAggregate();
        Group group =
                tenant.provisionGroup(FIXTURE_GROUP_NAME_1, FIXTURE_GROUP_DESCRIPTION_1);

        assertNotNull(group);

        assertEquals(FIXTURE_GROUP_NAME_1, group.groupId().name());
        assertEquals(tenant.tenantId(), group.groupId().tenantId());
        assertEquals(FIXTURE_GROUP_DESCRIPTION_1, group.description());
        this.expectedEvent(GroupProvisioned.class, 1);
        this.expectedEvents(1);
    }


    @Test
    public void provisionRole() {

        Tenant tenant = this.actifTenantAggregate();
        Role role =
                tenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);

        assertNotNull(role);

        assertEquals(FIXTURE_ROLE_NAME, role.name());
        assertEquals(tenant.tenantId(), role.tenantId());
        assertEquals(FIXTURE_ROLE_DESCRIPTION, role.description());
        this.expectedEvent(RoleProvisioned.class, 1);
        this.expectedEvents(1);

    }

    @Test
    public void registerUser() {

        Tenant tenant = this.actifTenantAggregate();
        RegistrationInvitation registrationInvitation =
                tenant.offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1, new EmailAddress("felicien.fotiomanfo@gmail.com"));
        User user =
                tenant.registerUser(
                        registrationInvitation.invitationId(),
                        FIXTURE_USERNAME_1,
                        FIXTURE_PASSWORD,
                        new Enablement(),
                        new Person(
                                new FullName(FIXTURE_FIRST_NAME_1, FIXTURE_LAST_NAME_1),
                                new ContactInformation(
                                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS_1),
                                        new PostalAddress(FIXTURE_STREET_ADDRESS_1, FIXTURE_CITY_1, FIXTURE_STATE_1, FIXTURE_POSTALCODE_1, FIXTURE_COUNTRY_CODE_1),
                                        new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1),
                                        new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1_1)
                                )));

        assertNotNull(user);

        assertEquals(tenant.tenantId(), user.userId().tenantId());
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvents(1);
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

    @Test
    public void testCreateOpenEndedInvitation() throws Exception {

        Tenant tenant = this.actifTenantAggregate();

        tenant.offerRegistrationInvitation("Open-Ended", new EmailAddress("felicien.fotiomanfo@gmail.com"))
                .openEnded();

        assertNotNull(tenant.redefineRegistrationInvitationAsOpenEnded("Open-Ended"));
    }

    @Test
    public void openEndedInvitationAvailable() throws Exception {

        Tenant tenant = this.actifTenantAggregate();

        tenant.offerRegistrationInvitation("Open-Ended", new EmailAddress("felicien.fotiomanfo@gmail.com"))
                .openEnded();

        assertTrue(tenant.isRegistrationAvailableThrough("Open-Ended"));
    }

    @Test
    public void closedEndedInvitationAvailable() throws Exception {

        Tenant tenant = this.actifTenantAggregate();

        tenant.offerRegistrationInvitation("Yesterday-and-Tomorrow", new EmailAddress("felicien.fotiomanfo@gmail.com"))
                .startingOn(this.yesterday())
                .until(this.tomorrow());

        assertTrue(tenant.isRegistrationAvailableThrough("Yesterday-and-Tomorrow"));
    }

    @Test
    public void testClosedEndedInvitationNotAvailable() throws Exception {

        Tenant tenant = this.actifTenantAggregate();

        tenant.offerRegistrationInvitation("Tomorrow-and-Day-After-Tomorrow", new EmailAddress("felicien.fotiomanfo@gmail.com"))
                .startingOn(this.tomorrow())
                .until(this.dayAfterTomorrow());

        assertFalse(tenant.isRegistrationAvailableThrough("Tomorrow-and-Day-After-Tomorrow"));
    }

    @Test
    public void availableInivitationDescriptor() throws Exception {

        Tenant tenant = this.actifTenantAggregate();

        tenant.offerRegistrationInvitation("Open-Ended", new EmailAddress("felicien.fotiomanfo@gmail.com"))
                .openEnded();
        tenant.offerRegistrationInvitation("Yesterday-and-Tomorrow", new EmailAddress("felicien.fotiomanfo@gmail.com"))
                .startingOn(this.yesterday())
                .until(this.tomorrow());

        assertEquals(2, tenant.allAvailableRegistrationInvitations().size());
    }

    @Test
    public void unavailableInivitationDescriptor() throws Exception {

        Tenant tenant = this.actifTenantAggregate();

        tenant.offerRegistrationInvitation("Tomorrow-and-Day-After-Tomorrow", new EmailAddress("felicien.fotiomanfo@gmail.com"))
                .startingOn(this.tomorrow())
                .until(this.dayAfterTomorrow());

        assertEquals(tenant.allUnavailableRegistrationInvitations().size(), 1);
    }

}
