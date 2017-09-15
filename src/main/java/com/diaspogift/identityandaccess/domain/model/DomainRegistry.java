package com.diaspogift.identityandaccess.domain.model;


import com.diaspogift.identityandaccess.domain.model.access.AuthorizationService;
import com.diaspogift.identityandaccess.domain.model.access.RoleRepository;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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

    public static PhoneNumberValidatorService phoneNumberValidatorService() {
        return (PhoneNumberValidatorService) applicationContext.getBean(PhoneNumberValidatorService.class);
    }

    public static EntityManagerFactory entityManagerFactory() {
        return (EntityManagerFactory) applicationContext.getBean(EntityManagerFactory.class);

    }

    public static EntityManager entityManager() {
        return (EntityManager) applicationContext.getBean(EntityManager.class);

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
