package com.diaspogift.identityandaccess.application.command;

import com.diaspogift.identityandaccess.application.representation.tenant.ProvisionTenantRepresentation;

public class ProvisionTenantCommand {

    private String tenantName;
    private String tenantDescription;
    private String administorFirstName;
    private String administorLastName;
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

    public ProvisionTenantCommand(String tenantName, String tenantDescription, String administorFirstName,
                                  String administorLastName, String emailAddress,
                                  String primaryCountryCode, String primaryDialingCountryCode, String primaryTelephone,
                                  String secondaryCountryCode, String secondaryDialingCountryCode, String secondaryTelephone,
                                  String addressStreetAddress, String addressCity, String addressStateProvince, String addressPostalCode,
                                  String addressCountryCode) {

        super();

        this.tenantName = tenantName;
        this.tenantDescription = tenantDescription;
        this.administorFirstName = administorFirstName;
        this.administorLastName = administorLastName;
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

    public ProvisionTenantCommand() {
        super();
    }

    public ProvisionTenantCommand(ProvisionTenantRepresentation provisionTenantRepresentation) {
        this.initializeFrom(provisionTenantRepresentation);
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDescription() {
        return this.tenantDescription;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public String getAdministorFirstName() {
        return this.administorFirstName;
    }

    public void setAdministorFirstName(String administorFirstName) {
        this.administorFirstName = administorFirstName;
    }

    public String getAdministorLastName() {
        return this.administorLastName;
    }

    public void setAdministorLastName(String administorLastName) {
        this.administorLastName = administorLastName;
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

    private void initializeFrom(ProvisionTenantRepresentation provisionTenantRepresentation) {
        this.tenantName = provisionTenantRepresentation.getTenantName();
        this.tenantDescription = provisionTenantRepresentation.getTenantDescription();
        this.administorFirstName = provisionTenantRepresentation.getAdministorFirstName();
        this.administorLastName = provisionTenantRepresentation.getAdministorLastName();
        this.emailAddress = provisionTenantRepresentation.getEmailAddress();
        this.primaryCountryCode = provisionTenantRepresentation.getPrimaryCountryCode();
        this.primaryDialingCountryCode = provisionTenantRepresentation.getPrimaryDialingCountryCode();
        this.primaryTelephone = provisionTenantRepresentation.getPrimaryTelephone();
        this.secondaryCountryCode = provisionTenantRepresentation.getSecondaryCountryCode();
        this.secondaryDialingCountryCode = provisionTenantRepresentation.getSecondaryDialingCountryCode();
        this.secondaryTelephone = provisionTenantRepresentation.getSecondaryTelephone();
        this.addressStreetAddress = provisionTenantRepresentation.getAddressStreetAddress();
        this.addressCity = provisionTenantRepresentation.getAddressCity();
        this.addressStateProvince = provisionTenantRepresentation.getAddressStateProvince();
        this.addressPostalCode = provisionTenantRepresentation.getAddressPostalCode();
        this.addressCountryCode = provisionTenantRepresentation.getAddressCountryCode();
    }
}

