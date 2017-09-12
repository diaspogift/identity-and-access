package com.diaspogift.identityandaccess.domain.model.identity;

public interface InternationalPhoneNumberValidatorService {
    public boolean validate(String countryCode, String number);
}
