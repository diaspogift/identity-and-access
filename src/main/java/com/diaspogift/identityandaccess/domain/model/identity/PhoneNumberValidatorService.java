package com.diaspogift.identityandaccess.domain.model.identity;

public interface PhoneNumberValidatorService {
    boolean validate(String countryCode, String number);
}
