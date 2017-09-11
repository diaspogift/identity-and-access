package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

public class TelephoneFactory extends AssertionConcern {

    public Telephone createTelephone(String aCountryCode, String aCountryDialingCode, String aNumber) {


                this.assertArgumentTrue(DomainRegistry.phoneNumberValidatorService().validate(aCountryCode, aNumber), "Invalid phone number.");

        return new Telephone(aCountryCode, aCountryDialingCode, aNumber);

    }
}
