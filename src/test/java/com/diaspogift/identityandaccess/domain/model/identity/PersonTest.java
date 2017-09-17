package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.EventTrackingTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest extends EventTrackingTests {

    public PersonTest() {
        super();
    }

    @Test
    public void createPerson() {

        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation =
                new ContactInformation(
                        new EmailAddress("email@yahoo.fr"),
                        new PostalAddress(
                                "Street address",
                                "Street city",
                                "State province",
                                "Postal code",
                                "US"
                        ),
                        new Telephone("CM", "00237", "691178154"),
                        new Telephone("CM", "00237", "669262656")
                );
        Person person = new Person(fullName, contactInformation);

        assertNotNull(person);
        assertEquals(fullName, person.name());
        assertEquals(contactInformation, person.contactInformation());
    }

    @Test
    public void changeContactInformation() {

        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation =
                new ContactInformation(
                        new EmailAddress("email@yahoo.fr"),
                        new PostalAddress(
                                "Street address",
                                "Street city",
                                "State province",
                                "Postal code",
                                "US"
                        ),
                        new Telephone("CM", "00237", "691178154"),
                        new Telephone("CM", "00237", "669262656")
                );
        Person person = new Person(fullName, contactInformation);
        ContactInformation newContactInformation =
                new ContactInformation(
                        new EmailAddress("email.new@yahoo.fr"),
                        new PostalAddress(
                                "Street address",
                                "Street city",
                                "State province",
                                "Postal code",
                                "US"
                        ),
                        new Telephone("CM", "00237", "691178154"),
                        new Telephone("US", "001", "805-456-7899")
                );

        assertNotEquals(person.contactInformation(), newContactInformation);

        person.changeContactInformation(newContactInformation);

        assertEquals(newContactInformation, person.contactInformation());
    }

    @Test
    public void changeName() {

        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");

        ContactInformation contactInformation =
                new ContactInformation(
                        new EmailAddress("email@yahoo.fr"),
                        new PostalAddress(
                                "Street address",
                                "Street city",
                                "State province",
                                "Postal code",
                                "US"
                        ),
                        new Telephone("CM", "00237", "691178154"),
                        new Telephone("CM", "00237", "669262656")
                );
        Person person = new Person(fullName, contactInformation);
        FullName newFullname = new FullName("Mboh Tom", "Hilaire");

        assertNotEquals(person.name(), newFullname);

        person.changeName(newFullname);

        assertEquals(newFullname, person.name());
    }

    @Test
    public void changeContactInformationEvent() {

        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");

        ContactInformation contactInformation =
                new ContactInformation(
                        new EmailAddress("email@yahoo.fr"),
                        new PostalAddress(
                                "Street address",
                                "Street city",
                                "State province",
                                "Postal code",
                                "US"
                        ),
                        new Telephone("CM", "00237", "691178154"),
                        new Telephone("CM", "00237", "669262656")
                );
        Person person = new Person(fullName, contactInformation);
        ContactInformation newContactInformation =
                new ContactInformation(
                        new EmailAddress("email.new@yahoo.fr"),
                        new PostalAddress(
                                "Street address",
                                "Street city",
                                "State province",
                                "Postal code",
                                "US"
                        ),
                        new Telephone("CM", "00237", "691178154"),
                        new Telephone("US", "001", "303-456-7899")
                );

        assertNotEquals(person.contactInformation(), newContactInformation);

        person.changeContactInformation(newContactInformation);

        assertEquals(newContactInformation, person.contactInformation());

    }


    @Test
    public void changeNameEvent() {

        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation =
                new ContactInformation(
                        new EmailAddress("email@yahoo.fr"),
                        new PostalAddress(
                                "Street address",
                                "Street city",
                                "State province",
                                "Postal code",
                                "US"
                        ),
                        new Telephone("CM", "00237", "691178154"),
                        new Telephone("CM", "00237", "669262656")
                );

        Person person = new Person(fullName, contactInformation);

        FullName newFullname = new FullName("Mboh Tom", "Hilaire");

        assertNotEquals(person.name(), newFullname);

        person.changeName(newFullname);

        assertEquals(newFullname, person.name());
    }


}
