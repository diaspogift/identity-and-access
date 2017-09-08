package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.repository.MockCountryCodeRepository;

import java.util.regex.Pattern;

public class LocalPhoneNumberValidatorService extends AssertionConcern implements PhoneNumberValidatorService{


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

    @Override
    public boolean validateCountryCode(String countryCode) {
        this.assertArgumentNotEmpty(countryCode, "Country code is required.");

        CountryPhoneCodeInfo countryPhoneCodeInfo = countryCodeRepository.allCountryPhoneInfo().get(countryCode);
        if (countryPhoneCodeInfo == null)
            return false;

        return countryCode.equals(countryPhoneCodeInfo.code());
    }

    @Override
    public boolean validateDialingCountryCode(String countryCode, String dialingCountryCode) {


        this.assertArgumentNotEmpty(countryCode, "Country code is required.");
        this.assertArgumentNotEmpty(dialingCountryCode, "Dialing country code is required.");


        CountryPhoneCodeInfo countryPhoneCodeInfo = countryCodeRepository.allCountryPhoneInfo().get(countryCode);
        if (countryPhoneCodeInfo == null)
            return false;

        return dialingCountryCode.equals(countryPhoneCodeInfo.dialingCode()) && countryCode.equals(countryPhoneCodeInfo.code());
    }
}
