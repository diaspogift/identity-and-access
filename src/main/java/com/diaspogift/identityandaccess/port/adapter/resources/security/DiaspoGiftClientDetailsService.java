package com.diaspogift.identityandaccess.port.adapter.resources.security;

import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;


@Service
public class DiaspoGiftClientDetailsService implements ClientDetailsService {


    private static final Logger log = LoggerFactory.getLogger(DiaspoGiftClientDetailsService.class);


    @Autowired
    private IdentityApplicationService identityApplicationService;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        String data[] = clientId.split("_");
        DiaspoGiftClientDetails diaspoGiftClientDetails = null;

        try {

            User user = identityApplicationService.user(data[0], data[1]);
            DiaspoGiftGrantedAuthority diaspoGiftGrantedAuthority = new DiaspoGiftGrantedAuthority("user");
            diaspoGiftClientDetails = new DiaspoGiftClientDetails(user, diaspoGiftGrantedAuthority);

            log.info("\n\n");
            log.info("diaspoGiftClientDetails == " + diaspoGiftClientDetails.toString());
            log.info("\n\n");

        } catch (DiaspoGiftRepositoryException e) {
            e.printStackTrace();
        }

        log.info("\n\n");
        log.info("clientId == " + clientId);
        log.info("\n\n");

        return diaspoGiftClientDetails;
    }
}
