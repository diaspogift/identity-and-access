package com.diaspogift.identityandaccess.repository;

import com.diaspogift.identityandaccess.domain.model.identity.CountryCodeRepository;
import com.diaspogift.identityandaccess.domain.model.identity.CountryPhoneCodeInfo;

import java.util.HashMap;
import java.util.Map;

public class MockCountryCodeRepository implements CountryCodeRepository{


    @Override
    public Map<String, CountryPhoneCodeInfo> allCountryPhoneInfo() {
        Map<String, CountryPhoneCodeInfo> map = new HashMap<>();
        map.put("USA",new CountryPhoneCodeInfo(
                "United States",
                "USA",
                "001",
                "((\\(\\d{3}\\))|(\\d{3}-))\\d{3}-\\d{4}"
        ));

        map.put("CMR",new CountryPhoneCodeInfo(
                "Cameroon",
                "CMR",
                "00237",
                "[0-9]{9}"
        ));

        return map;
    }
}
