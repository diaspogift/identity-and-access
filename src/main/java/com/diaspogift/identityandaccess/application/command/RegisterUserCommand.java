package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.user.UserRegistrationReprensentation;

public class RegisterUserCommand {

    private String tenantId;
    private String invitationIdentifier;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
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

    public RegisterUserCommand(String tenantId, String invitationIdentifier, String username, String password, String firstName,
                               String lastName, boolean enabled, String emailAddress,
                               String primaryCountryCode, String primaryDialingCountryCode, String primaryTelephone,
                               String secondaryCountryCode, String secondaryDialingCountryCode, String secondaryTelephone, String addressStreetAddress, String addressCity, String addressStateProvince,
                               String addressPostalCode, String addressCountryCode) {

        super();

        this.tenantId = tenantId;
        this.invitationIdentifier = invitationIdentifier;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.emailAddress = emailAddress;
        this.primaryTelephone = primaryTelephone;
        this.secondaryTelephone = secondaryTelephone;
        this.primaryCountryCode = primaryCountryCode;
        this.primaryDialingCountryCode = primaryDialingCountryCode;
        this.primaryTelephone = primaryTelephone;
        this.secondaryCountryCode = secondaryCountryCode;
        this.secondaryDialingCountryCode = secondaryDialingCountryCode;
        this.addressStreetAddress = addressStreetAddress;
        this.addressCity = addressCity;
        this.addressStateProvince = addressStateProvince;
        this.addressPostalCode = addressPostalCode;
        this.addressCountryCode = addressCountryCode;
    }

    public RegisterUserCommand(UserRegistrationReprensentation aUserRegistrationReprensentation) {

        this.initialyzeFrom(aUserRegistrationReprensentation);
    }

    private void initialyzeFrom(UserRegistrationReprensentation aUserRegistrationReprensentation) {

        this.tenantId = aUserRegistrationReprensentation.getTenantId();
        this.invitationIdentifier = aUserRegistrationReprensentation.getInvitationIdentifier();
        this.username = aUserRegistrationReprensentation.getUsername();
        this.password = aUserRegistrationReprensentation.getPassword();
        this.firstName = aUserRegistrationReprensentation.getFirstName();
        this.lastName = aUserRegistrationReprensentation.getLastName();
        this.enabled = true;
        this.emailAddress = aUserRegistrationReprensentation.getEmailAddress();
        this.primaryTelephone = aUserRegistrationReprensentation.getPrimaryTelephone();
        this.secondaryTelephone = aUserRegistrationReprensentation.getSecondaryTelephone();
        this.primaryCountryCode = aUserRegistrationReprensentation.getPrimaryCountryCode();
        this.primaryDialingCountryCode = aUserRegistrationReprensentation.getPrimaryDialingCountryCode();
        this.primaryTelephone = aUserRegistrationReprensentation.getPrimaryTelephone();
        this.secondaryCountryCode = aUserRegistrationReprensentation.getSecondaryCountryCode();
        this.secondaryDialingCountryCode = aUserRegistrationReprensentation.getSecondaryDialingCountryCode();
        this.addressStreetAddress = aUserRegistrationReprensentation.getAddressStreetAddress();
        this.addressCity = aUserRegistrationReprensentation.getAddressCity();
        this.addressStateProvince = aUserRegistrationReprensentation.getAddressStateProvince();
        this.addressPostalCode = aUserRegistrationReprensentation.getAddressPostalCode();
        this.addressCountryCode = aUserRegistrationReprensentation.getAddressCountryCode();
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getInvitationIdentifier() {
        return this.invitationIdentifier;
    }

    public void setInvitationIdentifier(String invitationIdentifier) {
        this.invitationIdentifier = invitationIdentifier;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPrimaryTelephone() {
        return this.primaryTelephone;
    }

    public void setPrimaryTelephone(String primaryTelephone) {
        this.primaryTelephone = primaryTelephone;
    }

    public String getSecondaryTelephone() {
        return this.secondaryTelephone;
    }

    public void setSecondaryTelephone(String secondaryTelephone) {
        this.secondaryTelephone = secondaryTelephone;
    }

    public String getAddressStreetAddress() {
        return this.addressStreetAddress;
    }

    public void setAddressStreetAddress(String addressStreetAddress) {
        this.addressStreetAddress = addressStreetAddress;
    }

    public String getAddressCity() {
        return this.addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStateProvince() {
        return this.addressStateProvince;
    }

    public void setAddressStateProvince(String addressStateProvince) {
        this.addressStateProvince = addressStateProvince;
    }

    public String getAddressPostalCode() {
        return this.addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountryCode() {
        return this.addressCountryCode;
    }

    public void setAddressCountryCode(String addressCountryCode) {
        this.addressCountryCode = addressCountryCode;
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

}

