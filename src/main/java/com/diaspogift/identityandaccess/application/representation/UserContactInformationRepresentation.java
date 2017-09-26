package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.ContactInformation;
import org.springframework.hateoas.ResourceSupport;


public class UserContactInformationRepresentation extends ResourceSupport{


    private String tenantId;
    private String username;

    //Email address
    private String emailAddress;

    //Postal Address
    private String city;
    private String countryCode;
    private String postalCode;
    private String stateProvince;
    private String streetAddress;


    //Primary telephone
    private String primaryCountryCode;
    private String primaryCountryDialingCode;
    private String primaryNumber;


    //secondary telephone
    private String secondaryCountryCode;
    private String secondaryCountryDialingCode;
    private String secondaryNumber;


    public UserContactInformationRepresentation() {
        super();
    }

    public UserContactInformationRepresentation(ContactInformation contactInformation) {

        this.initialyzeFrom(contactInformation);
    }

    private void initialyzeFrom(ContactInformation contactInformation) {

        this.emailAddress = contactInformation.emailAddress().address();
        this.city = contactInformation.postalAddress().city();
        this.countryCode = contactInformation.postalAddress().countryCode();
        this.postalCode = contactInformation.postalAddress().postalCode();
        this.stateProvince = contactInformation.postalAddress().stateProvince();
        this.streetAddress = contactInformation.postalAddress().streetAddress();
        this.primaryCountryCode = contactInformation.primaryTelephone().countryCode();
        this.primaryCountryDialingCode = contactInformation.primaryTelephone().countryDialingCode();
        this.primaryNumber = contactInformation.primaryTelephone().number();

        this.secondaryCountryCode = contactInformation.secondaryTelephone().countryCode();
        this.secondaryCountryDialingCode = contactInformation.secondaryTelephone().countryDialingCode();
        this.secondaryNumber = contactInformation.secondaryTelephone().number();

    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPrimaryCountryCode() {
        return primaryCountryCode;
    }

    public void setPrimaryCountryCode(String primaryCountryCode) {
        this.primaryCountryCode = primaryCountryCode;
    }

    public String getPrimaryCountryDialingCode() {
        return primaryCountryDialingCode;
    }

    public void setPrimaryCountryDialingCode(String primaryCountryDialingCode) {
        this.primaryCountryDialingCode = primaryCountryDialingCode;
    }

    public String getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getSecondaryCountryCode() {
        return secondaryCountryCode;
    }

    public void setSecondaryCountryCode(String secondaryCountryCode) {
        this.secondaryCountryCode = secondaryCountryCode;
    }

    public String getSecondaryCountryDialingCode() {
        return secondaryCountryDialingCode;
    }

    public void setSecondaryCountryDialingCode(String secondaryCountryDialingCode) {
        this.secondaryCountryDialingCode = secondaryCountryDialingCode;
    }

    public String getSecondaryNumber() {
        return secondaryNumber;
    }

    public void setSecondaryNumber(String secondaryNumber) {
        this.secondaryNumber = secondaryNumber;
    }

    @Override
    public String toString() {
        return "UserContactInformationRepresentation{" +
                "tenantId='" + tenantId + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", city='" + city + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", primaryCountryCode='" + primaryCountryCode + '\'' +
                ", primaryCountryDialingCode='" + primaryCountryDialingCode + '\'' +
                ", primaryNumber='" + primaryNumber + '\'' +
                ", secondaryCountryCode='" + secondaryCountryCode + '\'' +
                ", secondaryCountryDialingCode='" + secondaryCountryDialingCode + '\'' +
                ", secondaryNumber='" + secondaryNumber + '\'' +
                '}';
    }
}
