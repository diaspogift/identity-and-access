package com.diaspogift.identityandaccess.application.command;

public class ChangePrimaryTelephoneCommand {
    private String tenantId;
    private String username;
    private String countryCode;
    private String dialingCountryCode;
    private String telephone;


    public ChangePrimaryTelephoneCommand(String tenantId, String username, String countryCode, String dialingCountryCode, String telephone) {
        super();

        this.tenantId = tenantId;
        this.username = username;
        this.countryCode = countryCode;
        this.dialingCountryCode = dialingCountryCode;
        this.telephone = telephone;
    }

    public ChangePrimaryTelephoneCommand() {
        super();
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

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDialingCountryCode() {
        return dialingCountryCode;
    }

    public void setDialingCountryCode(String dialingCountryCode) {
        this.dialingCountryCode = dialingCountryCode;
    }
}

