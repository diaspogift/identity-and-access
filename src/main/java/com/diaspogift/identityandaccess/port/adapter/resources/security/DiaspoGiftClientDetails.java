package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.google.gson.Gson;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class DiaspoGiftClientDetails implements ClientDetails {


    private User user;

    private DiaspoGiftGrantedAuthority diaspoGiftGrantedAuthority;

    public DiaspoGiftClientDetails(User user, DiaspoGiftGrantedAuthority diaspoGiftGrantedAuthority) {
        this.user = user;
        this.diaspoGiftGrantedAuthority = diaspoGiftGrantedAuthority;
    }

    @Override
    public String getClientId() {

        Gson gson = new Gson();
        String retVal = gson.toJson(user.userId());
        System.out.println("\n\nclientId: " + retVal + "\n\n");
        return retVal;
    }

    @Override
    public Set<String> getResourceIds() {
        return new HashSet<>();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return user.password();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return new HashSet<>(Arrays.asList("password"));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<>(Arrays.asList(this.diaspoGiftGrantedAuthority));
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return new Integer(60*60*1000);
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return new Integer(60*60*1000);
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    @Override
    public String toString() {
        return "DiaspoGiftClientDetails{" +
                "user=" + user +
                ", diaspoGiftGrantedAuthority=" + diaspoGiftGrantedAuthority +
                '}' +
                "\nAuthorities: " + this.getAuthorities() +
                "\ngetClientId(): " + this.getClientId();
    }
}
