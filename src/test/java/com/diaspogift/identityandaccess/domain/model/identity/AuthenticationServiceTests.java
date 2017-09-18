package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.infrastructure.exception.DiaspogiftRipositoryException;
import com.diaspogift.identityandaccess.infrastructure.exception.MessageKeyMapping;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthenticationServiceTests {

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
        enablement = new Enablement(true, ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l));

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

        UserDescriptor userDescriptor = null;
        try {
            userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId,
                    "email@yahoo.fr", "secretSTRENGTH1234");
        } catch (DiaspogiftRipositoryException e) {
            e.printStackTrace();
        }
        assertNotNull(userDescriptor);
        assertEquals(user.userDescriptor(), userDescriptor);
    }

    @Test(expected = DiaspogiftRipositoryException.class)
    public void wrongAuthenticateWithBadUsername() throws DiaspogiftRipositoryException {


        UserDescriptor userDescriptor = null;

            userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId,
                    "email.bad@yahoo.fr", "secretSTRENGTH1234");

        assertNotNull(userDescriptor);
        assertNull(userDescriptor.username());
        assertNull(userDescriptor.emailAddress());
        assertNull(userDescriptor.tenantId());

    }


    @Test(expected = DiaspogiftRipositoryException.class)
    public void wrongAuthenticateWithBadPassword() throws DiaspogiftRipositoryException {

        UserDescriptor userDescriptor = null;
       // try {
            userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId,
                    "email@yahoo.fr", "badsecretSTRENGTH1234");
        //}catch (DiaspogiftRipositoryException e){
           // System.out.println("\n\n\n\n\n\n\nERROR: " + MessageKeyMapping.map().get(e.getMessageKey()));
            //throw new DiaspogiftRipositoryException("", e, e.getMessageKey());
        //}
        assertNotNull(userDescriptor);
        assertNull(userDescriptor.username());
        assertNull(userDescriptor.emailAddress());
        assertNull(userDescriptor.tenantId());
    }

    @Test(expected = DiaspogiftRipositoryException.class)
    public void wrongAuthenticateWithBadTenant() throws DiaspogiftRipositoryException {

        String id1 = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId1 = new TenantId(id1);

        UserDescriptor userDescriptor = null;

            userDescriptor = DomainRegistry.authenticationService().authenticate(tenantId1,
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
