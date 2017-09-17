package com.diaspogift.identityandaccess;

import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;
import com.diaspogift.identityandaccess.application.command.ProvisionTenantCommand;
import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IdentityAndAccessApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext ctx = SpringApplication.run(IdentityAndAccessApplication.class, args);

        ProvisionTenantCommand provisionTenantCommand =
                new ProvisionTenantCommand("BINGO", "HOPITAL BINGO", "Bingo Admin", "Bingo", "didier@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques", "Douala", "Littoral", "80209",
                        "US");


        ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand);

        TenantId tenantId =
                DomainRegistry.tenantRepository().nextIdentity();


    }


}
