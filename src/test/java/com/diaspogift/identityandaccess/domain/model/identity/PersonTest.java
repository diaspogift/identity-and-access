package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.EventTrackingTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest extends EventTrackingTests {


    @Test
    public void createPerson() {
        TenantId tenantId = new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );
        Person person = new Person(tenantId, fullName, contactInformation);
        assertNotNull(person);
        assertEquals(tenantId, person.tenantId());
        assertEquals(fullName, person.name());
        assertEquals(contactInformation, person.contactInformation());
    }

    @Test
    public void changeContactInformation() {
        TenantId tenantId = new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );
        Person person = new Person(tenantId, fullName, contactInformation);
        User user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l)),
                person
        );
        ContactInformation newContactInformation = new ContactInformation(
                new EmailAddress("email.new@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("US", "001", "805-456-7899")
        );

        person.changeContactInformation(newContactInformation);
        assertEquals(newContactInformation, person.contactInformation());
    }

    @Test
    public void changeName() {
        TenantId tenantId = new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");

        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );
        Person person = new Person(tenantId, fullName, contactInformation);
        User user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l)),
                person
        );
        FullName newFullname = new FullName("Mboh Tom", "Hilaire");
        person.changeName(newFullname);
        assertEquals(newFullname, person.name());
    }

    @Test
    public void changeContactInformationEvent() {
        TenantId tenantId = new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");

        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );
        Person person = new Person(tenantId, fullName, contactInformation);
        User user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l)),
                person
        );
        ContactInformation newContactInformation = new ContactInformation(
                new EmailAddress("email.new@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("US", "001", "303-456-7899")
        );
        person.changeContactInformation(newContactInformation);
        assertEquals(newContactInformation, person.contactInformation());

        this.expectedEvents(2);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(PersonContactInformationChanged.class, 1);

    }


    @Test
    public void changeNameEvent() {

        TenantId tenantId = new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );

        Person person = new Person(tenantId, fullName, contactInformation);

        User user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l)),
                person
        );
        FullName newFullname = new FullName("Mboh Tom", "Hilaire");
        person.changeName(newFullname);
        assertEquals(newFullname, person.name());

        this.expectedEvents(2);
        this.expectedEvent(PersonNameChanged.class, 1);
        this.expectedEvent(UserRegistered.class, 1);
    }


}
