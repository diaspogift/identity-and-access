package com.diaspogift.identityandaccess.application;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceRegistry implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static AccessApplicationService accessApplicationService() {
        return applicationContext.getBean(AccessApplicationService.class);
    }

    public static IdentityApplicationService identityApplicationService() {
        return applicationContext.getBean(IdentityApplicationService.class);
    }

    @Override
    public synchronized void setApplicationContext(
            ApplicationContext anApplicationContext)
            throws BeansException {

        if (ApplicationServiceRegistry.applicationContext == null) {
            ApplicationServiceRegistry.applicationContext = anApplicationContext;
        }
    }
}

