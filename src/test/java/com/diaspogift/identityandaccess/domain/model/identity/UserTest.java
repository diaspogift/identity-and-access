package com.diaspogift.identityandaccess.domain.model.identity;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserTest {

    @Autowired
    private EncryptionService encryptionService;

    private  String                id;
    private  TenantId              tenantId;
    private  FullName              fullName;
    private  ContactInformation    contactInformation;
    private  Calendar              calendier;
    private  Date                  now;
    private  Date                  afterTomorow;
    private  Person                person;
    private  User                  user;
    @Before
    public void init(){
        id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        tenantId = new TenantId(id);
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

        user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, now, afterTomorow),
                person
        );
    }

    @After
    public void reset(){
        id = null;
        tenantId = null;
        fullName = null;
        contactInformation = null;
        calendier = null;
        now = null;
        afterTomorow = null;
        person = null;
        user = null;

    }

    @Test
    public void createUser(){

        assertNotNull(user);
        assertEquals("username@gmail.com", user.username());
        assertEquals(person, user.person());
        assertEquals(new Enablement(true, now, afterTomorow), user.enablement());
        assertEquals(encryptionService.encryptedValue("secretSTRENGTH1234"), user.password());

    }

    @Test
    public void changePassword(){

        user.changePassword("secretSTRENGTH1234", "Ange__1308");
        assertEquals(encryptionService.encryptedValue("Ange__1308"), user.password());

    }

    @Test
    public void changePersonalContactInformation(){
        ContactInformation newContactInformation = new ContactInformation(
                new EmailAddress("email.new@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province","Postal code","US"),
                new Telephone("CMR","00237","691178154"),
                new Telephone("USA","001","123-456-7899")
        );


        user.changePersonalContactInformation(newContactInformation);
        assertEquals(newContactInformation, user.person().contactInformation());

    }


    @Test
    public void changePersonalName(){

        FullName newFullname = new FullName("Mboh Tom", "Hilaire");


        user.changePersonalName(newFullname);
        assertEquals(newFullname, user.person().name());

    }

    @Test
    public void defineEnablement(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 200);
        Date after200 = calendar.getTime();
        Date now1 = new Date();
       Enablement enablement = new Enablement(true, now1, after200);


        user.defineEnablement(enablement);
        assertEquals(enablement, user.enablement());

    }

}
