//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.infrastructure.exception.DiaspogiftRipositoryException;
import com.diaspogift.identityandaccess.infrastructure.exception.MessageKeyMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService extends AssertionConcern {

    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserRepository userRepository;

    public UserDescriptor authenticate(TenantId aTenantId, String aUsername, String aPassword) throws DiaspogiftRipositoryException {

        this.assertArgumentNotNull(aTenantId, "TenantId must not be null.");
        this.assertArgumentNotEmpty(aUsername, "Username must be provided.");
        this.assertArgumentNotEmpty(aPassword, "Password must be provided.");

        UserDescriptor userDescriptor = UserDescriptor.nullDescriptorInstance();
        Tenant tenant = null;
        try {
            tenant = this.tenantRepository().tenantOfId(aTenantId);
        }catch (EmptyResultDataAccessException e){
            throw new DiaspogiftRipositoryException("Partenaire avec l'identifiant " + aTenantId + " inexistant!", e,
                    EmptyResultDataAccessException.class.getSimpleName());

        }
        if (tenant != null && tenant.isActive()) {
            String encryptedPassword = this.encryptionService.encryptedValue(aPassword);

            User user = null;
            try {
                //System.out.println("\n\n aTenantId: " + aTenantId + "\n\naUsername: " + aUsername);
                user = this.userRepository
                        .userFromAuthenticCredentials(
                                aTenantId,
                                aUsername,
                                encryptedPassword);
            }catch (EmptyResultDataAccessException e){
               /*
                System.out.println("\n\n\n\nVoila: " + MessageKeyMapping.map().get(EmptyResultDataAccessException.class.getSimpleName()));
                throw new DiaspogiftRipositoryException(MessageKeyMapping.map().get(EmptyResultDataAccessException.class.getSimpleName()), e,
                        EmptyResultDataAccessException.class.getSimpleName());*/


                throw new DiaspogiftRipositoryException("Acun utilisateur trouve avec le nom d'utilisateur "+
                        aUsername + " appartenant au partenaire " + aTenantId , e,
                        EmptyResultDataAccessException.class.getSimpleName());
            }

            if (user != null && user.isEnabled()) {
                userDescriptor = user.userDescriptor();
            }
        }

        return userDescriptor;
    }


    public EncryptionService encryptionService() {
        return this.encryptionService;
    }

    public TenantRepository tenantRepository() {
        return this.tenantRepository;
    }

    public UserRepository userRepository() {
        return this.userRepository;
    }
}
