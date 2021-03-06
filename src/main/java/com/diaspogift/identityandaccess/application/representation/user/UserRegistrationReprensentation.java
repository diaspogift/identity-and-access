package com.diaspogift.identityandaccess.application.representation.user;

import org.springframework.hateoas.ResourceSupport;

public class UserRegistrationReprensentation extends ResourceSupport {


    private String tenantId;
    private String invitationIdentifier;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String primaryTelephone;
    private String secondaryTelephone;
    private String primaryCountryCode;
    private String primaryDialingCountryCode;
    private String secondaryCountryCode;
    private String secondaryDialingCountryCode;
    private String addressStreetAddress;
    private String addressCity;
    private String addressStateProvince;
    private String addressPostalCode;
    private String addressCountryCode;


    public UserRegistrationReprensentation() {

        super();
    }

    public UserRegistrationReprensentation(String tenantId, String invitationIdentifier, String username, String password, String firstName,
                                           String lastName, String emailAddress, String primaryTelephone,
                                           String secondaryTelephone, String primaryCountryCode, String primaryDialingCountryCode, String secondaryCountryCode,
                                           String secondaryDialingCountryCode, String addressStreetAddress, String addressCity, String addressStateProvince,
                                           String addressPostalCode, String addressCountryCode) {
        this.tenantId = tenantId;
        this.invitationIdentifier = invitationIdentifier;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.primaryTelephone = primaryTelephone;
        this.secondaryTelephone = secondaryTelephone;
        this.primaryCountryCode = primaryCountryCode;
        this.primaryDialingCountryCode = primaryDialingCountryCode;
        this.secondaryCountryCode = secondaryCountryCode;
        this.secondaryDialingCountryCode = secondaryDialingCountryCode;
        this.addressStreetAddress = addressStreetAddress;
        this.addressCity = addressCity;
        this.addressStateProvince = addressStateProvince;
        this.addressPostalCode = addressPostalCode;
        this.addressCountryCode = addressCountryCode;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getInvitationIdentifier() {
        return invitationIdentifier;
    }

    public void setInvitationIdentifier(String invitationIdentifier) {
        this.invitationIdentifier = invitationIdentifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPrimaryTelephone() {
        return primaryTelephone;
    }

    public void setPrimaryTelephone(String primaryTelephone) {
        this.primaryTelephone = primaryTelephone;
    }

    public String getSecondaryTelephone() {
        return secondaryTelephone;
    }

    public void setSecondaryTelephone(String secondaryTelephone) {
        this.secondaryTelephone = secondaryTelephone;
    }

    public String getPrimaryCountryCode() {
        return primaryCountryCode;
    }

    public void setPrimaryCountryCode(String primaryCountryCode) {
        this.primaryCountryCode = primaryCountryCode;
    }

    public String getPrimaryDialingCountryCode() {
        return primaryDialingCountryCode;
    }

    public void setPrimaryDialingCountryCode(String primaryDialingCountryCode) {
        this.primaryDialingCountryCode = primaryDialingCountryCode;
    }

    public String getSecondaryCountryCode() {
        return secondaryCountryCode;
    }

    public void setSecondaryCountryCode(String secondaryCountryCode) {
        this.secondaryCountryCode = secondaryCountryCode;
    }

    public String getSecondaryDialingCountryCode() {
        return secondaryDialingCountryCode;
    }

    public void setSecondaryDialingCountryCode(String secondaryDialingCountryCode) {
        this.secondaryDialingCountryCode = secondaryDialingCountryCode;
    }

    public String getAddressStreetAddress() {
        return addressStreetAddress;
    }

    public void setAddressStreetAddress(String addressStreetAddress) {
        this.addressStreetAddress = addressStreetAddress;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStateProvince() {
        return addressStateProvince;
    }

    public void setAddressStateProvince(String addressStateProvince) {
        this.addressStateProvince = addressStateProvince;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountryCode() {
        return addressCountryCode;
    }

    public void setAddressCountryCode(String addressCountryCode) {
        this.addressCountryCode = addressCountryCode;
    }
}
