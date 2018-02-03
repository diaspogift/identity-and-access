package com.diaspogift.identityandaccess;

import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;
import com.diaspogift.identityandaccess.application.command.ProvisionTenantCommand;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.ZonedDateTime;

@SpringBootApplication
public class IdentityAndAccessApplication {



    @Value("${spring.profiles.active}")
    private static String ENVIRONMENT;



    public static void main(String[] args) {


        ConfigurableApplicationContext ctx = SpringApplication.run(IdentityAndAccessApplication.class, args);



        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");
        System.out.println("------------------------------------------------ RUNNING IN  THE " + ENVIRONMENT + " PROFILE ------------------------------------------------");


        ProvisionTenantCommand provisionDiaspoGiftTenantCommand =
                new ProvisionTenantCommand("DASPOGIFT", "DIASPORA GIFT", "Diaspo Gift", "Collaboaration", "diaspogift@gmail.com",
                        "CM", "00237", "669262655", "CM", "00237", "669262657", "Denver (derriere Laureat.)", "Douala", "Littoral", "80209",
                        "CM");


        ProvisionTenantCommand provisionBingoTenantCommand =
                new ProvisionTenantCommand("BINGO", "HOPITAL BINGO", "Bingo Admin", "Bingoun", "bingo@gmail.com",
                        "CM", "00237", "669262655", "CM", "00237", "669262657", "Valley 3 boutiques", "Douala", "Littoral", "80211",
                        "CM");


        ProvisionTenantCommand provisionCadeauxTenantCommand =
                new ProvisionTenantCommand("CADEAUX", "Super Marche Cadeaux", "Cadeaux Admin", "Cadeaux", "cadeaux@gmail.com",
                        "CM", "00237", "669262655", "CM", "00237", "669262658", "Rond Point Laureat", "Douala", "Littoral", "80210",
                        "CM");


        ZonedDateTime yesterday = ZonedDateTime.now().minusDays(1l);
        ZonedDateTime tomorow = ZonedDateTime.now().plusDays(1l);


        try {


            Tenant provisionedDiaspoGiftTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionDiaspoGiftTenantCommand);
            Tenant provisionedBingoTenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionBingoTenantCommand);

        } catch (DiaspoGiftRepositoryException e) {

            System.out.println(e.getMessage());
        }


    }


}
