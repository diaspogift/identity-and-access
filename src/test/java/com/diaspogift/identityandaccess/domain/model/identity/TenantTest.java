package com.diaspogift.identityandaccess.domain.model.identity;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantTest {

    private String id;
    private  TenantId tenantId;
    private Tenant tenant;

    private  FullName              fullName;
    private  ContactInformation    contactInformation;
    private Calendar calendier;
    private Date now;
    private  Date                  afterTomorow;
    private  Person                person;
    private  User                  user;
    private  Enablement            enablement;
    @Before
    public  void init(){
         id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        tenantId = new TenantId(id);
        tenant = new Tenant(tenantId, "CLINIQUE LES POITIERS",
                "Grande institution hospitaliere. Situe a Valee trois boutique de Deido", false);

        fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province","Postal code","US"),
                new Telephone("CMR","00237","691178154"),
                new Telephone("CMR","00237","669262656")
        );

        calendier = Calendar.getInstance();
        calendier.add(Calendar.DAY_OF_YEAR, 2);
        afterTomorow = calendier.getTime();
        now = new Date();
        person = new Person(tenantId, fullName, contactInformation);
        enablement = new Enablement(true, now, afterTomorow);
        user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                enablement,
                person
        );
    }

    @After
    public void reset(){
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
    @Test
    public void createTenant(){
        assertNotNull(tenant);
        assertEquals("CLINIQUE LES POITIERS", tenant.name());
        assertEquals("Grande institution hospitaliere. Situe a Valee trois boutique de Deido", tenant.description());
        assertEquals(tenantId, tenant.tenantId());
        assertEquals(false, tenant.isActive());
    }

    @Test
    public void activate(){
        tenant.activate();
        assertEquals(true, tenant.isActive());
    }

    @Test
    public void deactivate(){
        tenant.deactivate();
        assertEquals(false, tenant.isActive());
    }


    @Test(expected = IllegalStateException.class)
    public void offerRegistrationInvitation(){
        tenant.activate();
        tenant.offerRegistrationInvitation("First invitation");
        assertEquals(1, tenant.registrationInvitations().size());
        tenant.offerRegistrationInvitation("First invitation");
    }

    @Test(expected = IllegalStateException.class)
    public void registerUser(){
        User aUser =
                tenant.registerUser(tenantId.id(), "email@yahoo.fr", "secretSTRENGTH1234",
                       enablement,person);

        System.out.println("\n\n" + aUser.internalAccessOnlyEncryptedPassword() + "\n\n");
        assertEquals(user, aUser);

    }

    @Test
    public void withdrawInvitation(){
        tenant.activate();
        tenant.offerRegistrationInvitation("InvitationId");
        RegistrationInvitation invitation = tenant.invitation("InvitationId");

        assertEquals(1, tenant.registrationInvitations().size());
        tenant.withdrawInvitation("InvitationId");
        assertEquals(0, tenant.registrationInvitations().size());

    }




}
