package com.diaspogift.identityandaccess.domain.model.identity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TelephoneTests {


    @Test
    public void createTelephone() {

        Telephone telephone =
                new Telephone("US", "001", "303-807-3573");

        assertNotNull(telephone);
        assertEquals("US", telephone.countryCode());
        assertEquals("001", telephone.countryDialingCode());
        assertEquals("303-807-3573", telephone.number());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWrongTelephone() {

        Telephone telephone =
                new Telephone("USAAAA", "001", "303-807-3573");

        assertNotNull(telephone);
        assertEquals("USAAAA", telephone.countryCode());
        assertEquals("001", telephone.countryDialingCode());
        assertEquals("303-807-3573", telephone.number());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWrongTelephone1() {

        Telephone telephone =
                new Telephone("USA", "001", "303-807-3573555");

        assertNotNull(telephone);
        assertEquals("USAAAA", telephone.countryCode());
        assertEquals("001", telephone.countryDialingCode());
        assertEquals("303-807-3573555", telephone.number());
    }


}
