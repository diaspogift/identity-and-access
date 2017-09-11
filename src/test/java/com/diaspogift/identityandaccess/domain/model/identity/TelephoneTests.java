package com.diaspogift.identityandaccess.domain.model.identity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TelephoneTests {

    @Autowired
    private InternationalPhoneNumberValidatorService internationalPhoneNumberValidatorService;

    @Test
    public void testCreateTelephone() {

        TelephoneFactory telephoneFactory = new TelephoneFactory();

        Telephone telephone = telephoneFactory.createTelephone("US", "001", "303-807-3573");

        assertNotNull(telephone);
        assertEquals("US", telephone.countryCode());
        assertEquals("001", telephone.countryDialingCode());
        assertEquals("303-807-3573", telephone.number());
    }

    //TO DO Add more create telephone tests method for different combinations
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTelephoneWithWrongCountryCode() {
        TelephoneFactory telephoneFactory = new TelephoneFactory();

        Telephone telephone = telephoneFactory.createTelephone("CMR", "001", "669262656");
        assertNull(telephone);
    }

    //
    @Test
    public void validateNumber(){
        boolean isValidePhone = internationalPhoneNumberValidatorService.validate("CM",  "233474566");
       assertEquals(true,isValidePhone);

    }

    @Test
    public void validateWrongNumber(){
        boolean isValidePhone = internationalPhoneNumberValidatorService.validate("CMR",  "3038073573");
        assertFalse(isValidePhone);

    }


}
