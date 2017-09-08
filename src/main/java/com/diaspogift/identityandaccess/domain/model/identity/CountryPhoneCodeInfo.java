package com.diaspogift.identityandaccess.domain.model.identity;

public class CountryPhoneCodeInfo {

    private String name;
    private String code;
    private String dialingCode;
    private String regExp;

    public CountryPhoneCodeInfo(String name, String code, String dialingCode, String regExp) {
        this.name = name;
        this.code = code;
        this.dialingCode = dialingCode;
        this.regExp = regExp;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String code() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String dialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String regExp() {
        return regExp;
    }

    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }
}
