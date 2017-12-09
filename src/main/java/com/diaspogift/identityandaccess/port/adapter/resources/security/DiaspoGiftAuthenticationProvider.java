package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component("diaspoGiftAuthenticationProvider")
public class DiaspoGiftAuthenticationProvider implements AuthenticationProvider{



    @Autowired
    private IdentityApplicationService identityApplicationService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        System.out.println(authentication.getCredentials());

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
