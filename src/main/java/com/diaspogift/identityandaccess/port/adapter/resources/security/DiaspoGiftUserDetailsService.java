package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
public class DiaspoGiftUserDetailsService implements UserDetailsService {


    private static final Logger log = LoggerFactory.getLogger(AuthorizationServerOauth2Config.class);

    @Autowired
    private IdentityApplicationService identityApplicationService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("\n\n username in loadUserByUsername  ==  " + username + "\n\n");
        log.info("\n\n username in loadUserByUsername  ==  " + username + "\n\n");
        log.info("\n\n username in loadUserByUsername  ==  " + username + "\n\n");


        String tenantId = username.split("_")[0];
        String userName = username.split("_")[1];

        User user = null;
        try {
            user = identityApplicationService.user(tenantId, userName);
        } catch (DiaspoGiftRepositoryException e) {
            e.printStackTrace();
        }

        return new DiaspoGiftUserDetails(user);
    }
}
