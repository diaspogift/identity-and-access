package com.diaspogift.identityandaccess.domain.model;

import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;
import com.diaspogift.identityandaccess.domain.model.common.EventTrackingTests;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.junit.After;
import org.junit.Before;

import java.time.ZonedDateTime;

public class IdentityAndAccessTest extends EventTrackingTests {


    protected static final String FIXTURE_PASSWORD = "SecretPassword@@2017!";
    protected static final String FIXTURE_TENANT_DESCRIPTION = "This is a test tenant.";
    protected static final String FIXTURE_TENANT_NAME = "Bingo Hospital";
    protected static final String FIXTURE_FIRST_NAME_1 = "Felicien Papa";
    protected static final String FIXTURE_FIRST_NAME_2 = "Felicien Papa";
    protected static final String FIXTURE_LAST_NAME_1 = "Felicien Papa";
    protected static final String FIXTURE_LAST_NAME_2 = "Felicien Papa";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS_1 = "felicien@diaspogift.com";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS_2 = "didier@diaspogift.com";
    protected static final String FIXTURE_USERNAME_1 = "felicien";
    protected static final String FIXTURE_USERNAME_2 = "didier";

    protected static final String FIXTURE_STREET_ADDRESS_1 = "123 Pearl Street";
    protected static final String FIXTURE_CITY_1 = "Boulder";
    protected static final String FIXTURE_STATE_1 = "CO";
    protected static final String FIXTURE_POSTALCODE_1 = "80301";
    protected static final String FIXTURE_COUNTRY_CODE_1 = "US";
    protected static final String FIXTURE_COUNTRY_DAILING_CODE_1 = "001";
    protected static final String FIXTURE_PHONE_NUMBER_1 = "303-555-1210";
    protected static final String FIXTURE_PHONE_NUMBER_1_1 = "303-555-1212";


    protected static final String FIXTURE_STREET_ADDRESS_2 = "Denver 3 boutiques";
    protected static final String FIXTURE_CITY_2 = "Douala";
    protected static final String FIXTURE_STATE_2 = "Littoral";
    protected static final String FIXTURE_POSTALCODE_2 = "28988";
    protected static final String FIXTURE_COUNTRY_CODE_2 = "CM";
    protected static final String FIXTURE_COUNTRY_DAILING_CODE_2 = "00237";
    protected static final String FIXTURE_PHONE_NUMBER_2 = "669262656";
    protected static final String FIXTURE_PHONE_NUMBER_2_2 = "671917167";

    protected static final String FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1 = "Invitation description 1";
    protected static final String FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_2 = "Invitation description 2";
    protected static final String FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_3 = "Invitation description 3";

    protected static final String FIXTURE_GROUP_NAME_0 = "GROUP NAME 0";
    protected static final String FIXTURE_GROUP_DESCRIPTION_0 = "GROUP DESCRIPTION 0";
    protected static final String FIXTURE_GROUP_NAME_1 = "GROUP NAME 1";
    protected static final String FIXTURE_GROUP_DESCRIPTION_1 = "GROUP DESCRIPTION 1";
    protected static final String FIXTURE_GROUP_NAME_2 = "GROUP NAME 2";
    protected static final String FIXTURE_GROUP_DESCRIPTION_2 = "GROUP DESCRIPTION 2";
    protected static final String FIXTURE_GROUP_NAME_3 = "GROUP NAME 3";
    protected static final String FIXTURE_GROUP_DESCRIPTION_3 = "GROUP DESCRIPTION 3";
    protected static final String FIXTURE_GROUP_NAME_4 = "GROUP NAME 4";
    protected static final String FIXTURE_GROUP_DESCRIPTION_4 = "GROUP DESCRIPTION 4";

    protected static final String FIXTURE_ROLE_NAME = "ROLE NAME";
    protected static final String FIXTURE_ROLE_DESCRIPTION = "ROLE DESCRIPTION";
    protected static final String FIXTURE_ROLE_NAME_1 = "ROLE NAME 1";
    protected static final String FIXTURE_ROLE_DESCRIPTION_1 = "ROLE DESCRIPTION 1";
    protected static final String FIXTURE_ROLE_NAME_2 = "ROLE NAME 2";
    protected static final String FIXTURE_ROLE_DESCRIPTION_2 = "ROLE DESCRIPTION 2";


    private Tenant tenant;
    private RegistrationInvitation registrationInvitation1;
    private RegistrationInvitation registrationInvitation2;
    private RegistrationInvitation registrationInvitation3;


    public IdentityAndAccessTest() {
        super();
    }


    protected ContactInformation contactInformation() {
        return
                new ContactInformation(
                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS_1),
                        new PostalAddress(
                                FIXTURE_STREET_ADDRESS_1,
                                FIXTURE_CITY_1,
                                FIXTURE_STATE_1,
                                FIXTURE_POSTALCODE_1,
                                FIXTURE_COUNTRY_CODE_1),
                        new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1),
                        new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1_1));
    }

    protected ZonedDateTime dayAfterTomorrow() {
        return ZonedDateTime.now().plusDays(2l);
    }

    protected ZonedDateTime dayBeforeYesterday() {
        return ZonedDateTime.now().minusDays(2l);
    }

    protected Person personEntity(Tenant aTenant) {

        Person person =
                new Person(
                        new FullName(FIXTURE_FIRST_NAME_1, FIXTURE_LAST_NAME_1),
                        this.contactInformation());

        return person;
    }

    protected Person personEntity2(Tenant aTenant) {

        Person person =
                new Person(
                        new FullName(FIXTURE_FIRST_NAME_2, FIXTURE_LAST_NAME_2),
                        new ContactInformation(
                                new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS_2),
                                new PostalAddress(
                                        FIXTURE_STREET_ADDRESS_2,
                                        FIXTURE_CITY_2,
                                        FIXTURE_STATE_2,
                                        FIXTURE_POSTALCODE_2,
                                        FIXTURE_COUNTRY_CODE_2),
                                new Telephone(FIXTURE_COUNTRY_CODE_2, FIXTURE_COUNTRY_DAILING_CODE_2, FIXTURE_PHONE_NUMBER_2),
                                new Telephone(FIXTURE_COUNTRY_CODE_2, FIXTURE_COUNTRY_DAILING_CODE_2, FIXTURE_PHONE_NUMBER_2_2)));

        return person;
    }

    protected User userAggregate() {


        Tenant tenant = this.actifTenantAggregate();

        RegistrationInvitation registrationInvitation =
                this.registrationInvitationEntity(tenant);

        User user =
                tenant.registerUser(
                        registrationInvitation.invitationId(),
                        FIXTURE_USERNAME_1,
                        FIXTURE_PASSWORD,
                        new Enablement(true, null, null),
                        this.personEntity(tenant));

        return user;
    }


    protected RegistrationInvitation registrationInvitationEntity(Tenant aTenant) {

        ZonedDateTime today = ZonedDateTime.now().minusDays(1l);

        ZonedDateTime tomorrow = ZonedDateTime.now().plusDays(1l);

        RegistrationInvitation registrationInvitation =
                aTenant.offerRegistrationInvitation("Today-and-Tomorrow: " + today.toString() + " ---- " + tomorrow.toString())
                        .startingOn(today)
                        .until(tomorrow);

        return registrationInvitation;
    }

    protected Tenant actifTenantAggregate() {

        if (this.tenant == null) {
            TenantId tenantId =
                    DomainRegistry.tenantRepository().nextIdentity();

            this.tenant =
                    new Tenant(
                            tenantId,
                            FIXTURE_TENANT_NAME,
                            FIXTURE_TENANT_DESCRIPTION,
                            true);

            DomainRegistry.tenantRepository().add(tenant);
        }

        return this.tenant;
    }


    protected Tenant tenantAggregateWithOfferedRegistrationInvitations() {

        if (this.tenant == null) {
            TenantId tenantId =
                    DomainRegistry.tenantRepository().nextIdentity();

            this.tenant =
                    new Tenant(
                            tenantId,
                            FIXTURE_TENANT_NAME,
                            FIXTURE_TENANT_DESCRIPTION,
                            true);


            this.registrationInvitation1 = this.tenant().offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1);
            this.registrationInvitation2 = this.tenant().offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_2);
            this.registrationInvitation3 =
                    this.tenant()
                            .offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_3)
                            .startingOn(ZonedDateTime.now().plusDays(1l));

            DomainRegistry.tenantRepository().add(this.tenant());
        }

        return this.tenant;
    }

    protected Tenant nonActifTenantAggregateWithOfferedRegistrationInvitations() {

        if (this.tenant == null) {
            TenantId tenantId =
                    DomainRegistry.tenantRepository().nextIdentity();

            this.tenant =
                    new Tenant(
                            tenantId,
                            FIXTURE_TENANT_NAME,
                            FIXTURE_TENANT_DESCRIPTION,
                            false);


            this.registrationInvitation1 = this.tenant().offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_1);
            this.registrationInvitation2 = this.tenant().offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_2);
            this.registrationInvitation3 =
                    this.tenant()
                            .offerRegistrationInvitation(FIXTURE_REGISTRATION_INVITATION_DESCRIPTION_3)
                            .startingOn(ZonedDateTime.now().plusDays(1l));

            DomainRegistry.tenantRepository().add(this.tenant());
        }

        return this.tenant;
    }

    protected Tenant nonActifTenantAggregate() {

        if (this.tenant == null) {
            TenantId tenantId =
                    DomainRegistry.tenantRepository().nextIdentity();

            this.tenant =
                    new Tenant(
                            tenantId,
                            FIXTURE_TENANT_NAME,
                            FIXTURE_TENANT_DESCRIPTION,
                            false);

            DomainRegistry.tenantRepository().add(tenant);
        }

        return this.tenant;
    }

    protected ZonedDateTime today() {
        return ZonedDateTime.now();
    }


    protected ZonedDateTime tomorrow() {
        return ZonedDateTime.now().plusDays(1l);
    }


    protected User userAggregate2() throws Exception {
        Tenant tenant = this.actifTenantAggregate();

        RegistrationInvitation registrationInvitation =
                this.registrationInvitationEntity(tenant);

        User user =
                tenant.registerUser(
                        registrationInvitation.invitationId(),
                        FIXTURE_USERNAME_2,
                        FIXTURE_PASSWORD,
                        new Enablement(true, null, null),
                        this.personEntity2(tenant));

        return user;
    }

    protected ZonedDateTime yesterday() {
        return ZonedDateTime.now().minusDays(1l);
    }

    private Tenant tenant() {
        return this.tenant;
    }

    protected RegistrationInvitation registrationInvitation1() {
        return registrationInvitation1;
    }

    protected RegistrationInvitation registrationInvitation2() {
        return registrationInvitation2;
    }

    protected RegistrationInvitation registrationInvitation3() {
        return registrationInvitation3;
    }


    @Before
    public void setUp() throws Exception {

        super.setUp();

        System.out.println(">>>>>>>>>>>>>>>>>>>>> (started) " + this.getClass().getSimpleName());

    }


    @After
    public void tearDown() throws Exception {

        super.tearDown();

        this.setTenant(null);
        this.setRegistrationInvitation1(null);
        this.setRegistrationInvitation2(null);
        this.setRegistrationInvitation3(null);
        this.setRegistrationInvitation1(null);
        this.setRegistrationInvitation2(null);
        this.setRegistrationInvitation3(null);

        DomainEventPublisher.instance().reset();


        System.out.println("<<<<<<<<<<<<<<<<<<<< (done) " + this.getClass().getSimpleName());

    }

    private void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    private void setRegistrationInvitation1(RegistrationInvitation registrationInvitation1) {
        this.registrationInvitation1 = registrationInvitation1;
    }

    private void setRegistrationInvitation2(RegistrationInvitation registrationInvitation2) {
        this.registrationInvitation2 = registrationInvitation2;
    }

    private void setRegistrationInvitation3(RegistrationInvitation registrationInvitation3) {
        this.registrationInvitation3 = registrationInvitation3;
    }


}

