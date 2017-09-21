package com.diaspogift.identityandaccess.application.representation;

public class ProvisionTenantRepresentation {

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

    public ProvisionTenantRepresentation() {
        super();
    }

    public ProvisionTenantRepresentation(String tenantName, String tenantDescription, String administorFirstName,
                                         String administorLastName, String emailAddress, String primaryTelephone,
                                         String secondaryTelephone, String primaryCountryCode, String primaryDialingCountryCode,
                                         String secondaryCountryCode, String secondaryDialingCountryCode,
                                         String addressStreetAddress, String addressCity, String addressStateProvince,
                                         String addressPostalCode, String addressCountryCode) {
        this.tenantName = tenantName;
        this.tenantDescription = tenantDescription;
        this.administorFirstName = administorFirstName;
        this.administorLastName = administorLastName;
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

    public String getTenantName() {
        return tenantName;
    }

    public String getTenantDescription() {
        return tenantDescription;
    }

    public String getAdministorFirstName() {
        return administorFirstName;
    }

    public String getAdministorLastName() {
        return administorLastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPrimaryTelephone() {
        return primaryTelephone;
    }

    public String getSecondaryTelephone() {
        return secondaryTelephone;
    }

    public String getPrimaryCountryCode() {
        return primaryCountryCode;
    }

    public String getPrimaryDialingCountryCode() {
        return primaryDialingCountryCode;
    }

    public String getSecondaryCountryCode() {
        return secondaryCountryCode;
    }

    public String getSecondaryDialingCountryCode() {
        return secondaryDialingCountryCode;
    }

    public String getAddressStreetAddress() {
        return addressStreetAddress;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public String getAddressStateProvince() {
        return addressStateProvince;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public String getAddressCountryCode() {
        return addressCountryCode;
    }
}
