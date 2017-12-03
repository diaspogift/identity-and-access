package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;


@Service
public class DiaspoGiftClientDetailsService implements ClientDetailsService {

    @Autowired
    private IdentityApplicationService identityApplicationService;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {


        String tenantId = clientId.split("_")[0];
        String username = clientId.split("_")[1];


        System.out.println("\n\n");
        System.out.println("IN DiaspoGiftClientDetailsService  loadClientByClientId  tenantId = " + tenantId);
        System.out.println("IN DiaspoGiftClientDetailsService  loadClientByClientId  username = " + username);
        System.out.println("\n\n");


        DiaspoGiftClientDetails diaspoGiftClientDetails = null;


        try {

            User user = identityApplicationService.user(tenantId, username);

            System.out.println("IN DiaspoGiftClientDetailsService  found user  = " + user.toString());


            diaspoGiftClientDetails = new DiaspoGiftClientDetails(user);

        } catch (DiaspoGiftRepositoryException e) {
            e.printStackTrace();
        }


        return diaspoGiftClientDetails;
    }
}
