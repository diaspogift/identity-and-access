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
    public void validateNumber() {
        assertTrue(DomainRegistry.phoneNumberValidatorService().validate("CM", "233474566"));

    }

    @Test
    public void validateWrongNumber() {
        assertFalse(DomainRegistry.phoneNumberValidatorService().validate("CM", "30380735734444"));

    }

    @Test
    public void validateWrongCountryCode() {
        assertFalse(DomainRegistry.phoneNumberValidatorService().validate("US", "233474566"));

    }

}
