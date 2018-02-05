package com.diaspogift.identityandaccess;

import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;
import com.diaspogift.identityandaccess.application.command.ProvisionTenantCommand;
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


        ProvisionTenantCommand provisionDiaspoGiftTenantCommand =
                new ProvisionTenantCommand("DASPOGIFT", "DIASPORA GIFT", "Diaspo Admin", "Diaspo", "diaspogift@gmail.com",
                        "CM", "00237", "669262655", "CM", "00237", "669262657", "Denver (Derriere Laureat.)", "Douala", "Littoral", "80209",
                        "CM");


        ProvisionTenantCommand provisionBingoTenantCommand =
                new ProvisionTenantCommand("BINGO", "BINGO HOPSITAL", "Bingo Admin", "Bingo", "bingo@gmail.com",
                        "CM", "00237", "655262955", "CM", "00237", "655262955", "Mbopi (Rond point)", "Douala", "Littoral", "80211",
                        "CM");


        ProvisionTenantCommand provisionCadeauxTenantCommand =
                new ProvisionTenantCommand("CADEAUX", "Super Marche Cadeaux", "Cadeaux Admin", "Cadeaux", "cadeaux@gmail.com",
                        "CM", "00237", "678676755", "CM", "00237", "678676755", "Rond Point Laureat", "Douala", "Littoral", "80210",
                        "CM");

        ProvisionTenantCommand provisionWafoTenantCommand =
                new ProvisionTenantCommand("WAFO", "Groupe Scolaire Bilingue Wafo", "Wafo Admin", "Wafo", "wafo@gmail.com",
                        "CM", "00237", "657676543", "CM", "00237", "657676543", "Tradex Bonamoussadi", "Douala", "Littoral", "80213",
                        "CM");


        ZonedDateTime yesterday = ZonedDateTime.now().minusDays(1l);
        ZonedDateTime tomorow = ZonedDateTime.now().plusDays(1l);


        try {


            Tenant provisionedDiaspoGiftTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionDiaspoGiftTenantCommand);
            Tenant provisionedBingoTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionBingoTenantCommand);
            Tenant provisionedCadeauxTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionCadeauxTenantCommand);
            Tenant provisionedWafoTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionWafoTenantCommand);

        } catch (DiaspoGiftRepositoryException e) {

            System.out.println(e.getMessage());
        }


    }


}
