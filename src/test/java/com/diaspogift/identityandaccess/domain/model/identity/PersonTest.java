package com.diaspogift.identityandaccess.domain.model.identity;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Test
    public void createPerson() {
        String id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId = new TenantId(id);
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        ContactInformation contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CMR", "00237", "691178154"),
                new Telephone("CMR", "00237", "669262656")
        );

        Person person = new Person(tenantId, fullName, contactInformation);

        assertNotNull(person);
        assertEquals(tenantId, person.tenantId());
        assertEquals(fullName, person.name());
        assertEquals(contactInformation, person.contactInformation());
    }
}
