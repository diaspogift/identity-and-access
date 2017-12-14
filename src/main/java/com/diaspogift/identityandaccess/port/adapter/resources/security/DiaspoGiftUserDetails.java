package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.domain.model.identity.UserDescriptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class DiaspoGiftUserDetails implements UserDetails {


    private UserDescriptor userDescriptor;
    private User user;


    public DiaspoGiftUserDetails(User user, UserDescriptor userDescriptor) {

        this.user = user;
        this.userDescriptor = userDescriptor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDescriptor.roleDescriptorList();
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String getUsername() {
        return this.userDescriptor.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}