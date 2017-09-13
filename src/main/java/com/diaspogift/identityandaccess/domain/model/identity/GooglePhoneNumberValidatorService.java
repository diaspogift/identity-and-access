package com.diaspogift.identityandaccess.domain.model.identity;

public interface GooglePhoneNumberValidatorService {
    public boolean validate(String countryCode, String number);
}
