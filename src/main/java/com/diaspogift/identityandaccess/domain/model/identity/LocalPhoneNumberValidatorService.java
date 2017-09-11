package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.infrastructure.persistence.MockCountryCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import java.util.regex.Pattern;




@Component
public class LocalPhoneNumberValidatorService extends AssertionConcern implements PhoneNumberValidatorService{

    @Autowired
    private CountryCodeRepository countryCodeRepository = new MockCountryCodeRepository();


    @Override
    public boolean validate(String countryCode, String dialingCountryCode, String number) {
        this.assertArgumentNotEmpty(countryCode, "Country code is required.");
        this.assertArgumentNotEmpty(dialingCountryCode, "Dialing country code is required.");
        this.assertArgumentNotEmpty(number, "Number is required.");


        CountryPhoneCodeInfo countryPhoneCodeInfo = countryCodeRepository.allCountryPhoneInfo().get(countryCode);
        if (countryPhoneCodeInfo == null)
            return false;

        return dialingCountryCode.equals(countryPhoneCodeInfo.dialingCode()) && Pattern.matches(countryPhoneCodeInfo.regExp(), number);
    }

}
