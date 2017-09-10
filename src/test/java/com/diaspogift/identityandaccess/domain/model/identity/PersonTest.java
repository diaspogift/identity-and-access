package com.diaspogift.identityandaccess.domain.model.identity;


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
public class PersonTest {

    @Test
    public void createPerson(){
        String id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId = new TenantId(id);
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province","Postal code","US"),
                new Telephone("CMR","00237","691178154"),
                new Telephone("CMR","00237","669262656")
        );

        Person person = new Person(tenantId, fullName, contactInformation);

        assertNotNull(person);
        assertEquals(tenantId, person.tenantId());
        assertEquals(fullName, person.name());
        assertEquals(contactInformation, person.contactInformation());
    }

    @Test
    public void changeContactInformation(){
        String id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId = new TenantId(id);
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");

        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province","Postal code","US"),
                new Telephone("CMR","00237","691178154"),
                new Telephone("CMR","00237","669262656")
        );

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 2);
        Date afterTomorow = c.getTime();
        Date now = new Date();

        Person person = new Person(tenantId, fullName, contactInformation);

        User user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, now, afterTomorow),
                person
        );


        ContactInformation newContactInformation = new ContactInformation(
                new EmailAddress("email.new@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province","Postal code","US"),
                new Telephone("CMR","00237","691178154"),
                new Telephone("USA","001","123-456-7899")
        );

        person.changeContactInformation(newContactInformation);

        assertEquals(newContactInformation, person.contactInformation());

    }


    @Test
    public void changeName(){
        String id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId = new TenantId(id);
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");

        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province","Postal code","US"),
                new Telephone("CMR","00237","691178154"),
                new Telephone("CMR","00237","669262656")
        );

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 2);
        Date afterTomorow = c.getTime();
        Date now = new Date();

        Person person = new Person(tenantId, fullName, contactInformation);

        User user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, now, afterTomorow),
                person
        );


        FullName newFullname = new FullName("Mboh Tom", "Hilaire");

        person.changeName(newFullname);

        assertEquals(newFullname, person.name());

    }


}
