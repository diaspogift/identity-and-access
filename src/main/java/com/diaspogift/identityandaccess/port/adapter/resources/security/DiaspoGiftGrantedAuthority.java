package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.domain.model.access.Role;
import org.springframework.security.core.GrantedAuthority;

public class DiaspoGiftGrantedAuthority implements GrantedAuthority {

    private String authority;

    public DiaspoGiftGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
