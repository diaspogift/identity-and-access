package com.diaspogift.identityandaccess.domain.model.identity;

public interface PhoneNumberValidatorService {
    public boolean validate(String countryCode, String number);
}
