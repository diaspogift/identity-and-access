package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberValidatorService extends AssertionConcern implements GooglePhoneNumberValidatorService {
    @Override
    public boolean validate(String countryCode, String number) {

        this.assertArgumentNotEmpty(countryCode, "Country code is required.");
        this.assertArgumentNotEmpty(number, "Number is required.");

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        Phonenumber.PhoneNumber phoneNumber = null;

        try {
            phoneNumber = phoneUtil.parse(number, countryCode);
        } catch (NumberParseException e) {
            System.out.println("\n\n\nNumberParseException was thrown: " + e.toString() + "\n\n\n");
            // e.printStackTrace();
            return false;
        }

        System.out.println("\n\nInfo: " + phoneNumber.toString() + "\n\n\ngetCountryCode: " + phoneNumber.getCountryCode() +
                "  getPreferredDomesticCarrierCode: " + phoneNumber.getPreferredDomesticCarrierCode() + "   getCountryCodeSource: " +
                phoneNumber.getCountryCodeSource() + "   getExtension: " +
                phoneNumber.getExtension());

        return phoneUtil.isValidNumber(phoneNumber);
    }
}
