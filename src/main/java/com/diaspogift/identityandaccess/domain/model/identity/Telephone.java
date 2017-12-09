package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

import java.io.Serializable;

public final class Telephone extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private String countryCode;
    private String countryDialingCode;
    private String number;

    public Telephone(String aCountryCode, String aCountryDialingCode, String aNumber) {


        this();


        this.assertArgumentTrue(DomainRegistry.phoneNumberValidatorService().validate(aCountryCode, aNumber), "Invalid phone number.");
        this.setCountryCode(aCountryCode);
        this.setCountryDialingCode(aCountryDialingCode);
        this.setNumber(aNumber);

    }

    public Telephone(Telephone aTelephone) {
        this(aTelephone.countryCode(), aTelephone.countryDialingCode(), aTelephone.number());
    }

    public Telephone() {
        super();
    }

    public String number() {
        return this.number;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Telephone typedObject = (Telephone) anObject;
            equalObjects = this.number().equals(typedObject.number());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(35137 * 239)
                        + this.number().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Telephone [number=" + number + "]";
    }

    private void setNumber(String aNumber) {
        this.assertArgumentNotEmpty(aNumber, "Telephone number is required.");
        /*this.assertArgumentLength(aNumber, 5, 20, "Telephone number may not be more than 20 characters.");
       this.assertArgumentTrue(
                Pattern.matches("((\\(\\d{3}\\))|(\\d{3}-))\\d{3}-\\d{4}", aNumber),
                "Telephone number or its format is invalid.");*/
        //this.assertArgumentTrue(DomainRegistry.PhoneNumberValidatorService().validate(this.countryCode(), this.countryDialingCode(), this.number()), "Invalid phone number.");

        this.number = aNumber;
    }


    private void setCountryCode(String aCountryCode) {
        //this.assertArgumentTrue(DomainRegistry.PhoneNumberValidatorService().validateCountryCode(aCountryCode), "Invalid country code.");
        this.countryCode = aCountryCode;
    }

    private void setCountryDialingCode(String aCountryDialingCode) {
        //this.assertArgumentTrue(DomainRegistry.PhoneNumberValidatorService().validateDialingCountryCode(this.countryCode(),aCountryDialingCode ), "Invalid dialing country code.");
        this.countryDialingCode = aCountryDialingCode;
    }

    public String countryCode() {
        return countryCode;
    }

    public String countryDialingCode() {
        return countryDialingCode;
    }
}
