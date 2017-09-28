package com.diaspogift.identityandaccess;

import com.diaspogift.identityandaccess.application.command.ProvisionTenantCommand;
import com.diaspogift.identityandaccess.application.representation.ProvisionTenantRepresentation;
import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IdentityAndAccessApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext ctx = SpringApplication.run(IdentityAndAccessApplication.class, args);

        ProvisionTenantCommand provisionTenantCommand1 =
                new ProvisionTenantCommand("BINGO1", "HOPITAL BINGO 1", "Bingo Adminun", "Bingoun", "didier1@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 1", "Douala 1", "Littoral 1", "80209",
                        "US");

        ProvisionTenantCommand provisionTenantCommand2 =
                new ProvisionTenantCommand("BINGO2", "HOPITAL BINGO 2", "Bingo Admindeux", "Bingodeux", "didier2@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 2", "Douala 2", "Littoral 2", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand3 =
                new ProvisionTenantCommand("BINGO3", "HOPITAL BINGO 3", "Bingo Admintrois", "Bingotrois", "didier3@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 3", "Douala 3", "Littoral 3", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand4 =
                new ProvisionTenantCommand("BINGO4", "HOPITAL BINGO 4", "Bingo Adminquatre", "Bingoquatre", "didier4@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 4", "Douala 4", "Littoral 4", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand5 =
                new ProvisionTenantCommand("BINGO5", "HOPITAL BINGO 5", "Bingo Adminquatre", "Bingoquatre", "didier5@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 5", "Douala 5", "Littoral 5", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand6 =
                new ProvisionTenantCommand("BINGO6", "HOPITAL BINGO 6", "Bingo Adminquatre", "Bingoquatre", "didier6@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 6", "Douala 6", "Littoral 6", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand7 =
                new ProvisionTenantCommand("BINGO7", "HOPITAL BINGO 7", "Bingo Adminquatre", "Bingoquatre", "didier7@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 7", "Douala 7", "Littoral 7", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand8 =
                new ProvisionTenantCommand("BINGO8", "HOPITAL BINGO 4", "Bingo Adminquatre", "Bingoquatre", "didier8@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 8", "Douala 8", "Littoral 8", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand9 =
                new ProvisionTenantCommand("BINGO9", "HOPITAL BINGO 9", "Bingo Adminquatre", "Bingoquatre", "didier9@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 9", "Douala 9", "Littoral 9", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand10 =
                new ProvisionTenantCommand("BINGO10", "HOPITAL BINGO 10", "Bingo Adminquatre", "Bingoquatre", "didier10@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 10", "Douala 10", "Littoral 10", "80209",
                        "US");


        ProvisionTenantCommand provisionTenantCommand11 =
                new ProvisionTenantCommand("BINGO11", "HOPITAL BINGO 11", "Bingo Adminquatre", "Bingoquatre", "didier11@gmail.com",
                        "US", "001", "303-807-3573", "US", "001", "303-807-3573", "3 boutiques 11", "Douala 11", "Littoral 11", "80209",
                        "US");


        ZonedDateTime yesterday = ZonedDateTime.now().minusDays(1l);
        ZonedDateTime tomorow = ZonedDateTime.now().plusDays(1l);

        RestTemplate template = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(acceptableMediaTypes);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);


        ProvisionTenantRepresentation provisionTenantRepresentation =
                new ProvisionTenantRepresentation(
                        "Cadeaux",
                        "Super marche de bonamoussadi",
                        "Didier",
                        "Nkalla",
                        "didier@yahoo.fr",
                        "669262656",
                        "669262656",
                        "CM",
                        "00237",
                        "CM",
                        "00237",
                        "Rond point laureat",
                        "Douala",
                        "Littoral",
                        "80209",
                        "CM"
                );


        // TenantRepresentation tr = template.postForObject("http://localhost:8083/api/v1/tenants/provisions", provisionTenantRepresentation, TenantRepresentation.class);


        HttpEntity requestEntity1 = new HttpEntity(httpHeaders);


        //HttpEntity result = template.exchange("http://localhost:8083/api/v1/tenants", HttpMethod.GET, requestEntity1, String.class);


        // System.out.println("\n\n\n result  == " + result);
        //System.out.println("\n\n\n result body == " + result.getBody());


        //System.out.println(" \n\n tr ============================== " + tr);
        //System.out.println(" \n\n tr ============================== " + tr);
        //System.out.println(" \n\n tr ============================== " + tr);


        //System.out.println(" \n\n\n\n yesterday " + yesterday + " tomorow " + tomorow);


        /*try {
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand1);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand2);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand3);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand4);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand5);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand6);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand7);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand8);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand9);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand10);
            ApplicationServiceRegistry.identityApplicationService().provisionTenant(provisionTenantCommand11);
        } catch (DiaspoGiftRepositoryException e) {

            System.out.println(e.getMessage());
        }
*/
        TenantId tenantId =
                DomainRegistry.tenantRepository().nextIdentity();


    }


}
