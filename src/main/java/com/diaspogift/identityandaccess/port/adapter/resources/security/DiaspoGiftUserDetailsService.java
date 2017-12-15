package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.domain.model.access.RoleDescriptor;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.domain.model.identity.UserDescriptor;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DiaspoGiftUserDetailsService implements UserDetailsService {


    @Autowired
    private IdentityApplicationService identityApplicationService;

    @Autowired
    private AccessApplicationService accessApplicationService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        String data[] = username.split("_");
        String tempTenantId = data[0];
        String tempUsername = data[1];


        User user = null;
        UserDescriptor userDescriptor = null;

        try {


            user = identityApplicationService.user(tempTenantId, tempUsername);

            Collection<RoleDescriptor> roleDescriptors = accessApplicationService.allRolesForIdentifiedUser(tempTenantId, tempUsername);


            userDescriptor = user.userDescriptor();

            userDescriptor.setRoleDescriptorList(roleDescriptors);


        } catch (DiaspoGiftRepositoryException e) {
            throw new UsernameNotFoundException("Bad credentials were provided.");
        }


        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }

        return new DiaspoGiftUserDetails(user, userDescriptor);
    }


}
