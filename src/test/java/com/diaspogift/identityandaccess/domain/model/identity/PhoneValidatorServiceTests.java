package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneValidatorServiceTests {

    @Test
    public void validateCamerounCamtelTelephoneNumber() {

        assertTrue(DomainRegistry.phoneNumberValidatorService().validate("CM", "233474566"));
    }

    @Test
    public void validateCamerounNextelTelephoneNumber() {

        assertTrue(DomainRegistry.phoneNumberValidatorService().validate("CM", "669262656"));
    }

    @Test
    public void validateCamerounMtnTelephoneNumber() {

        assertTrue(DomainRegistry.phoneNumberValidatorService().validate("CM", "655545434"));
    }


    @Test
    public void validateUnitedStateDenverTelephoneNumber() {

        assertTrue(DomainRegistry.phoneNumberValidatorService().validate("US", "8054745660"));
    }

    @Test
    public void validateUnitedStateLosAngelesTelephoneNumber() {

        assertTrue(DomainRegistry.phoneNumberValidatorService().validate("US", "8054745660"));
    }

    @Test
    public void validateWrongUnitedStateDenverTelephoneNumber() {

        assertFalse(DomainRegistry.phoneNumberValidatorService().validate("US", "30380735734444444444"));
    }

    @Test
    public void validateWrongUnitedStateCountryCode() {

        assertFalse(DomainRegistry.phoneNumberValidatorService().validate("USA", "3034745660"));

    }

    @Test
    public void validateWrongCamerounTelephoneNumber() {

        assertFalse(DomainRegistry.phoneNumberValidatorService().validate("CM", "655545434999999999999"));

    }


}
