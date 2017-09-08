package com.diaspogift.identityandaccess.domain.model.identity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FullNameTest {

    @Test
    public void createFullName(){
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        assertNotNull(fullName);
        assertEquals("Nkalla Ehawe", fullName.firstName());
        assertEquals("Didier Junior", fullName.lastName());
    }

    @Test
    public void asFormattedName(){
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        assertEquals("Nkalla Ehawe Didier Junior", fullName.asFormattedName());
    }

    @Test
    public void withChangedFirstName(){
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        FullName fullName1 = fullName.withChangedFirstName("Essoua Ehawe");
        assertEquals("Essoua Ehawe", fullName1.firstName());
    }

    @Test
    public void withChangedLastName(){
        FullName fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        FullName fullName1 = fullName.withChangedLastName("Fotio Manfo");
        assertEquals("Fotio Manfo", fullName1.lastName());
    }

}
