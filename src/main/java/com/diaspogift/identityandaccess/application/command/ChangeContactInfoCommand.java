package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.user.UserContactInformationRepresentation;

public class ChangeContactInfoCommand {
    private String tenantId;
    private String username;
    private String emailAddress;
    private String primaryTelephone;
    private String secondaryTelephone;
    private String addressStreetAddress;
    private String addressCity;
    private String addressStateProvince;
    private String addressPostalCode;
    private String addressCountryCode;
    private String primaryCountryCode;
    private String primaryDialingCountryCode;
    private String secondaryCountryCode;
    private String secondaryDialingCountryCode;


    public ChangeContactInfoCommand(String tenantId, String username, String emailAddress, String primaryCountryCode, String primaryDialingCountryCode,
                                    String primaryTelephone,
                                    String secondaryCountryCode, String secondaryDialingCountryCode, String secondaryTelephone, String addressStreetAddress, String addressCity, String addressStateProvince,
                                    String addressPostalCode, String addressCountryCode) {

        super();

        this.tenantId = tenantId;
        this.username = username;
        this.emailAddress = emailAddress;
        this.primaryCountryCode = primaryCountryCode;
        this.primaryDialingCountryCode = primaryDialingCountryCode;
        this.primaryTelephone = primaryTelephone;
        this.secondaryCountryCode = secondaryCountryCode;
        this.secondaryDialingCountryCode = secondaryDialingCountryCode;
        this.secondaryTelephone = secondaryTelephone;
        this.addressStreetAddress = addressStreetAddress;
        this.addressCity = addressCity;
        this.addressStateProvince = addressStateProvince;
        this.addressPostalCode = addressPostalCode;
        this.addressCountryCode = addressCountryCode;

    }

    public ChangeContactInfoCommand() {
        super();
    }

    public ChangeContactInfoCommand(String aTenantId, String aUsername, UserContactInformationRepresentation aUserContactInformationRepresentation) {

        this.initialyzeFrom(aTenantId, aUsername, aUserContactInformationRepresentation);
    }

    private void initialyzeFrom(String aTenantId, String aUsername, UserContactInformationRepresentation aUserContactInformationRepresentation) {

        this.tenantId = aTenantId;
        this.username = aUsername;
        this.emailAddress = aUserContactInformationRepresentation.getEmailAddress();
        this.primaryCountryCode = aUserContactInformationRepresentation.getPrimaryCountryCode();
        this.primaryDialingCountryCode = aUserContactInformationRepresentation.getPrimaryCountryDialingCode();
        this.primaryTelephone = aUserContactInformationRepresentation.getPrimaryNumber();
        this.secondaryCountryCode = aUserContactInformationRepresentation.getSecondaryCountryCode();
        this.secondaryDialingCountryCode = aUserContactInformationRepresentation.getSecondaryCountryDialingCode();
        this.secondaryTelephone = aUserContactInformationRepresentation.getSecondaryNumber();
        this.addressStreetAddress = aUserContactInformationRepresentation.getStreetAddress();
        this.addressCity = aUserContactInformationRepresentation.getCity();
        this.addressStateProvince = aUserContactInformationRepresentation.getStateProvince();
        this.addressPostalCode = aUserContactInformationRepresentation.getPostalCode();
        this.addressCountryCode = aUserContactInformationRepresentation.getCountryCode();
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return this.primaryCountryCode;
    }

    public String getPrimaryDialingCountryCode() {
        return this.primaryDialingCountryCode;
    }

    public String getSecondaryCountryCode() {
        return this.secondaryCountryCode;
    }

    public String getSecondaryDialingCountryCode() {
        return this.secondaryDialingCountryCode;
    }
}

