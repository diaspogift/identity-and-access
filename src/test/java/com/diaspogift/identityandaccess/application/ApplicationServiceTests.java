package com.diaspogift.identityandaccess.application;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import com.diaspogift.identityandaccess.infrastructure.exception.DiaspogiftRipositoryException;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;

import java.time.ZonedDateTime;


public abstract class ApplicationServiceTests {

    protected static final String FIXTURE_GROUP_NAME = "Test Group";
    protected static final String FIXTURE_GROUP_DESCRIPTION = "A test group";
    protected static final String FIXTURE_PASSWORD = "SecretPassword!";
    protected static final String FIXTURE_ROLE_NAME = "Test Role";
    protected static final String FIXTURE_ROLE_DESCRIPTION = "A Test Role";
    protected static final String FIXTURE_TENANT_DESCRIPTION = "This is a test tenant.";
    protected static final String FIXTURE_TENANT_NAME = "Test Tenant";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS = "felicien@gmail.com";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS2 = "didier.@yahoo.com";
    protected static final String FIXTURE_USERNAME = "elberto";
    protected static final String FIXTURE_USERNAME2 = "didier";

    protected Tenant activeTenant;
    protected ApplicationContext applicationContext;

    public ApplicationServiceTests() {
        super();
    }

    protected Group group1Aggregate() {
        return this.activeTenantAggregate()
                .provisionGroup(FIXTURE_GROUP_NAME + " 1", FIXTURE_GROUP_DESCRIPTION + " 1");
    }

    protected Group group2Aggregate() {
        return this.activeTenantAggregate()
                .provisionGroup(FIXTURE_GROUP_NAME + " 2", FIXTURE_GROUP_DESCRIPTION + " 2");
    }

    protected Role roleAggregate() {
        return this.activeTenantAggregate()
                .provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
    }

    protected Tenant activeTenantAggregate() {
        if (activeTenant == null) {

            try {
                activeTenant =
                        DomainRegistry
                                .tenantProvisioningService()
                                .provisionTenant(
                                        FIXTURE_TENANT_NAME,
                                        FIXTURE_TENANT_DESCRIPTION,
                                        new FullName("Felicien Papa", "Fotio Manfo"),
                                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS),
                                        new PostalAddress(
                                                "400 S Lafayette St",
                                                "Denver",
                                                "CO",
                                                "80209",
                                                "US"),
                                        new Telephone("US", "001", "303-555-1210"),
                                        new Telephone("US", "001", "303-555-1212"));
            } catch (DiaspogiftRipositoryException e) {
                e.printStackTrace();
            }
        }

        return activeTenant;
    }


    protected Tenant nonActiveTenantAggregate() {
        if (activeTenant == null) {

            try {
                activeTenant =
                        DomainRegistry
                                .tenantProvisioningService()
                                .provisionTenant(
                                        FIXTURE_TENANT_NAME,
                                        FIXTURE_TENANT_DESCRIPTION,
                                        new FullName("Felicien Papa", "Fotio Manfo"),
                                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS),
                                        new PostalAddress(
                                                "400 S Lafayette St",
                                                "Denver",
                                                "CO",
                                                "80209",
                                                "US"),
                                        new Telephone("US", "001", "303-555-1210"),
                                        new Telephone("US", "001", "303-555-1212"));
            } catch (DiaspogiftRipositoryException e) {
                e.printStackTrace();
            }
            activeTenant.deactivate();
        }else activeTenant.deactivate();

        return activeTenant;
    }

    protected User userAggregate() {

        Tenant tenant = this.activeTenantAggregate();

        RegistrationInvitation invitation =
                tenant.offerRegistrationInvitation("open-ended").openEnded();

        User user =
                tenant.registerUser(
                        invitation.invitationId(),
                        FIXTURE_USERNAME,
                        FIXTURE_PASSWORD,
                        Enablement.indefiniteEnablement(),
                        new Person(
                                tenant.tenantId(),
                                new FullName("Felicien Papa", "Fotio Manfo"),
                                new ContactInformation(
                                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS),
                                        new PostalAddress(
                                                "123 Pearl Street",
                                                "Boulder",
                                                "CO",
                                                "80301",
                                                "US"),
                                        new Telephone("US", "001", "303-555-1210"),
                                        new Telephone("US", "001", "303-555-1212"))));

        return user;
    }

    protected User disabledUserAggregate() {

        Tenant tenant = this.activeTenantAggregate();

        RegistrationInvitation invitation =
                tenant.offerRegistrationInvitation("open-ended").openEnded();

        User user =
                tenant.registerUser(
                        invitation.invitationId(),
                        FIXTURE_USERNAME,
                        FIXTURE_PASSWORD,
                        new Enablement(false, ZonedDateTime.now().plusDays(5l), ZonedDateTime.now().plusDays(10l)),
                        new Person(
                                tenant.tenantId(),
                                new FullName("Felicien Papa", "Fotio Manfo"),
                                new ContactInformation(
                                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS),
                                        new PostalAddress(
                                                "123 Pearl Street",
                                                "Boulder",
                                                "CO",
                                                "80301",
                                                "US"),
                                        new Telephone("US", "001", "303-555-1210"),
                                        new Telephone("US", "001", "303-555-1212"))));

        return user;
    }


    @Before
    public void setUp() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>> " + this.getClass().getSimpleName());

        DomainEventPublisher.instance().reset();
    }

    @After
    public void tearDown() throws Exception {

        System.out.println("<<<<<<<<<<<<<<<<<<<< (done)");
    }

}
