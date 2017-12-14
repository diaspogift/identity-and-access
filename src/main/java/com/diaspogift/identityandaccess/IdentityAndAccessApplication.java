package com.diaspogift.identityandaccess;

import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;
import com.diaspogift.identityandaccess.application.command.ProvisionGroupCommand;
import com.diaspogift.identityandaccess.application.command.ProvisionRoleCommand;
import com.diaspogift.identityandaccess.application.command.ProvisionTenantCommand;
import com.diaspogift.identityandaccess.application.representation.group.GroupRepresentation;
import com.diaspogift.identityandaccess.application.representation.roles.RoleRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.Group;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.ZonedDateTime;

@SpringBootApplication
public class IdentityAndAccessApplication {


    public static void main(String[] args) {


        ConfigurableApplicationContext ctx = SpringApplication.run(IdentityAndAccessApplication.class, args);


        ProvisionTenantCommand provisionBingoTenantCommand =
                new ProvisionTenantCommand("BINGO", "HOPITAL BINGO", "Bingo Admin", "Bingoun", "bingo@gmail.com",
                        "CM", "00237", "669262655", "CM", "00237", "669262657", "Valley 3 boutiques", "Douala", "Littoral", "80209",
                        "CM");


        ProvisionTenantCommand provisionCadeauxTenantCommand =
                new ProvisionTenantCommand("CADEAUX", "Super Marche Cadeaux", "Cadeaux Admin", "Cadeaux", "cadeaux@gmail.com",
                        "CM", "00237", "669262655", "CM", "00237", "669262658", "Rond Point Laureat", "Douala", "Littoral", "80210",
                        "CM");


        ZonedDateTime yesterday = ZonedDateTime.now().minusDays(1l);
        ZonedDateTime tomorow = ZonedDateTime.now().plusDays(1l);


        try {

            Tenant provisionedBingoTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionBingoTenantCommand);


            ProvisionRoleCommand provisionBingoRoleCommand =
                    new ProvisionRoleCommand(provisionedBingoTenant.tenantId().id(), new RoleRepresentation("DG_BINGO_REP", "Groupe des representant de diaspogift aupres de Bingo.", true));


            ApplicationServiceRegistry.accessApplicationService().provisionRole(provisionBingoRoleCommand);


            Tenant provisionedCadeauxTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionCadeauxTenantCommand);


            ProvisionGroupCommand provisionCadeauxGroupCommand =
                    new ProvisionGroupCommand(provisionedBingoTenant.tenantId().id(), new GroupRepresentation("DG_CADEAUX_REP", "Groupe des representant de diaspogift aupres de Cadeaux."));


            Group dgCadeauxRep = ApplicationServiceRegistry.identityApplicationService().provisionGroup(provisionCadeauxGroupCommand);


        } catch (DiaspoGiftRepositoryException e) {

            System.out.println(e.getMessage());
        }


    }


}
