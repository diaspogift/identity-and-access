package com.diaspogift.identityandaccess.domain.model.identity;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TelephoneTests {

    @Test
    public void testCreateTelephone(){

        TelephoneFactory telephoneFactory = new TelephoneFactory();

        Telephone telephone = telephoneFactory.createTelephone("USA","001","303-807-3573");

        assertNotNull(telephone);
        assertEquals("USA", telephone.countryCode());
        assertEquals("001", telephone.countryDialingCode());
        assertEquals("303-807-3573", telephone.number());
    }

    //TO DO Add more create telephone tests method for different combinations
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTelephoneWithWrongCountryCode(){
        TelephoneFactory telephoneFactory = new TelephoneFactory();

        Telephone telephone = telephoneFactory.createTelephone("CMR","001","669262656");
        assertNull(telephone);
    }
}
