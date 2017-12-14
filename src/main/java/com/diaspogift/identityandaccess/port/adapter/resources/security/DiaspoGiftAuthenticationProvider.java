package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.AuthenticateUserCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.domain.model.access.RoleDescriptor;
import com.diaspogift.identityandaccess.domain.model.identity.UserDescriptor;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Component
public class DiaspoGiftAuthenticationProvider implements AuthenticationProvider {


    private final Logger logger = LoggerFactory.getLogger(DiaspoGiftAuthenticationProvider.class);

    @Autowired
    private IdentityApplicationService identityApplicationService;

    @Autowired
    private AccessApplicationService accessApplicationService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String tenantid_username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String data[] = tenantid_username.split("_");

        String tenantId = data[0];
        String username = data[1];


        logger.info("\n\n tenantid_username == " + tenantid_username + "\n\n");
        logger.info("\n\n password == " + password + "\n\n");
        logger.info("\n\n tenantId == " + tenantId + "\n\n");
        logger.info("\n\n tenantid_username == " + tenantid_username + "\n\n");
        logger.info("\n\n username == " + username + "\n\n");

        UserDescriptor authenticatedUser = null;
        Collection<RoleDescriptor> userRoles = new HashSet<RoleDescriptor>();


        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();


        try {

            authenticatedUser = identityApplicationService.authenticateUser(new AuthenticateUserCommand(tenantId, username, password));
            userRoles = accessApplicationService.allRolesForIdentifiedUser(tenantId, username);

            for (RoleDescriptor next : userRoles) {

                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + next.roleName().toUpperCase()));
            }

        } catch (DiaspoGiftRepositoryException e) {
            e.printStackTrace();
        }


        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
