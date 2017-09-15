package com.diaspogift.identityandaccess.domain.model.identity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactInformationTests {

    @Test
    public void testChangeEmailAddress() {

        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );

        ContactInformation contactInformation1 = contactInformation.changeEmailAddress(new EmailAddress("changedemail@gmail.com"));

        assertEquals(new EmailAddress("changedemail@gmail.com"), contactInformation1.emailAddress());
    }

    @Test
    public void changePostalAddress() {
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );

        ContactInformation contactInformation1 = contactInformation.changePostalAddress(
                new PostalAddress("Street address CM", "Street city Douala", "State province LT", "Postal 1234", "CM")
        );

        assertEquals(new PostalAddress("Street address CM", "Street city Douala", "State province LT", "Postal 1234", "CM"),
                contactInformation1.postalAddress());

    }


    @Test
    public void changePrimaryTelephone() {
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );

        ContactInformation contactInformation1 = contactInformation.changePrimaryTelephone(
                new Telephone("US", "001", "303-456-4567"));

        assertEquals(new Telephone("US", "001", "303-456-4567"),
                contactInformation1.primaryTelephone());

    }

    @Test
    public void changeSecondaryTelephone() {
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CM", "00237", "691178154"),
                new Telephone("CM", "00237", "669262656")
        );

        ContactInformation contactInformation1 = contactInformation.changeSecondaryTelephone(
                new Telephone("US", "001", "303-807-4567"));

        assertEquals(new Telephone("US", "001", "303-807-4567"),
                contactInformation1.secondaryTelephone());

    }
}
