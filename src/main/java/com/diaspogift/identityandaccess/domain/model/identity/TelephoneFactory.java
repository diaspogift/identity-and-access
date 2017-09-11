package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

public class TelephoneFactory extends AssertionConcern {

    public Telephone createTelephone(String aCountryCode, String aCountryDialingCode, String aNumber) {

<<<<<<< HEAD

        this.assertArgumentTrue(DomainRegistry.phoneNumberValidatorService().validate(aCountryCode, aCountryDialingCode, aNumber), "Invalid phone number.");
=======
                this.assertArgumentTrue(DomainRegistry.phoneNumberValidatorService().validate(aCountryCode, aCountryDialingCode, aNumber), "Invalid phone number.");
>>>>>>> didier-user-aggregate-setup

        return new Telephone(aCountryCode, aCountryDialingCode, aNumber);

    }
}
