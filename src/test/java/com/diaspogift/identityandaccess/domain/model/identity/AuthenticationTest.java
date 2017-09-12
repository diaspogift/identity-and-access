package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthenticationTest {

    private String id;
    private TenantId tenantId;
    private FullName fullName;
    private ContactInformation contactInformation;
    private Calendar calendier;
    private Date now;
    private Date afterTomorow;
    private Person person;
    private User user;
    private Tenant tenant;
    private Enablement enablement;


    @Before
    public void init() {

        id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        tenantId = new TenantId(id);

        tenant = new Tenant(tenantId, "CLINIQUE LES POITIERS",
                "Grande institution hospitaliere. Situe a Valee trois boutique de Deido", false);


        fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CMR", "00237", "691178154"),
                new Telephone("CMR", "00237", "669262656")
        );

        calendier = Calendar.getInstance();
        calendier.add(Calendar.DAY_OF_YEAR, 2);
        afterTomorow = calendier.getTime();
        now = new Date();
        person = new Person(tenantId, fullName, contactInformation);
        enablement = new Enablement(true, now, afterTomorow);
        /*user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                enablement,
                person
        );*/

        tenant.activate();
        RegistrationInvitation invitation = tenant.offerRegistrationInvitation("First invitation");
        DomainRegistry.tenantRepository().add(tenant);
        user = tenant.registerUser(invitation.invitationId(), "email@yahoo.fr", "secretSTRENGTH1234",
                enablement, person);
        if (user == null) {
            throw new IllegalArgumentException("User not registrated...");
        }
        DomainRegistry.userRepository().add(user);


    }

    @Test
    public void authenticate() {

        UserDescriptor userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId,
                "email@yahoo.fr", "secretSTRENGTH1234");
        assertNotNull(userDescriptor);
        assertEquals(user.userDescriptor(), userDescriptor);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void wrongAuthenticateWithBadUsername() {


        UserDescriptor userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId,
                "email.bad@yahoo.fr", "secretSTRENGTH1234");
        assertNotNull(userDescriptor);
        assertNull(userDescriptor.username());
        assertNull(userDescriptor.emailAddress());
        assertNull(userDescriptor.tenantId());

    }


    @Test(expected = EmptyResultDataAccessException.class)
    public void wrongAuthenticateWithBadPassword() {

        UserDescriptor userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId,
                "email@yahoo.fr", "badsecretSTRENGTH1234");
        assertNotNull(userDescriptor);
        assertNull(userDescriptor.username());
        assertNull(userDescriptor.emailAddress());
        assertNull(userDescriptor.tenantId());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void wrongAuthenticateWithBadTenant() {

        String id1 = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId1 = new TenantId(id1);

        UserDescriptor userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId1,
                "email@yahoo.fr", "secretSTRENGTH1234");
        assertNotNull(userDescriptor);
        assertNull(userDescriptor.username());
        assertNull(userDescriptor.emailAddress());
        assertNull(userDescriptor.tenantId());
    }

    @After
    public void reset() {
        id = null;
        tenantId = null;
        tenant = null;
        fullName = null;
        contactInformation = null;
        calendier = null;
        now = null;
        afterTomorow = null;
        person = null;
        user = null;
        enablement = null;

    }

}
