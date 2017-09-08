package com.diaspogift.identityandaccess.domain.model.identity;

import java.util.Map;

public interface CountryCodeRepository {

    public Map<String, CountryPhoneCodeInfo> allCountryPhoneInfo();
}
