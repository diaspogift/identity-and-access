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

package com.diaspogift.identityandaccess.domain.model;


import com.diaspogift.identityandaccess.domain.model.access.AuthorizationService;
import com.diaspogift.identityandaccess.domain.model.access.RoleRepository;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;


@Component
public class DomainRegistry implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    public static AuthenticationService authenticationService() {
        return (AuthenticationService) applicationContext.getBean(AuthenticationService.class);
    }

    public static AuthorizationService authorizationService() {
        return (AuthorizationService) applicationContext.getBean(AuthorizationService.class);
    }

    public static EncryptionService encryptionService() {
        return (EncryptionService) applicationContext.getBean(EncryptionService.class);
    }

    public static GroupMemberService groupMemberService() {
        return (GroupMemberService) applicationContext.getBean(GroupMemberService.class);
    }

    public static GroupRepository groupRepository() {
        return (GroupRepository) applicationContext.getBean(GroupRepository.class);
    }

    public static PasswordService passwordService() {
        return (PasswordService) applicationContext.getBean(PasswordService.class);
    }

    public static RoleRepository roleRepository() {
        return (RoleRepository) applicationContext.getBean(RoleRepository.class);
    }

    public static TenantProvisioningService tenantProvisioningService() {
        return (TenantProvisioningService) applicationContext.getBean(TenantProvisioningService.class);
    }

    public static TenantRepository tenantRepository() {
        return (TenantRepository) applicationContext.getBean(TenantRepository.class);
    }

    public static UserRepository userRepository() {
        return (UserRepository) applicationContext.getBean(UserRepository.class);
    }

    public static InternationalPhoneNumberValidatorService phoneNumberValidatorService() {
        return (InternationalPhoneNumberValidatorService) applicationContext.getBean(InternationalPhoneNumberValidatorService.class);
    }

    public static EntityManagerFactory entityManagerFactory() {
        return (EntityManagerFactory) applicationContext.getBean(EntityManagerFactory.class);

    }


    @Override
    public synchronized void setApplicationContext(
            ApplicationContext anApplicationContext)
            throws BeansException {

        if (DomainRegistry.applicationContext == null) {
            DomainRegistry.applicationContext = anApplicationContext;
        }
    }
}
