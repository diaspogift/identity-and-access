package com.diaspogift.identityandaccess.domain.model.identity;

public interface PhoneNumberValidatorService {

    public boolean validate(String countryCode, String dialingCountryCode, String number);

    public boolean validateCountryCode(String countryCode);

    public boolean validateDialingCountryCode(String countryCode, String dialingCountryCode);

}
