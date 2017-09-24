package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.IdentityAndAccessApplication;
import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;
import com.diaspogift.identityandaccess.application.representation.ProvisionTenantRepresentation;
import com.diaspogift.identityandaccess.application.representation.RegistrationInvitationRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = IdentityAndAccessApplication.class)
@Transactional
public class TenantResourceTests extends AbstractResourseTests {


    @LocalServerPort
    private int SERVER_PORT;


    public TenantResourceTests() {
        super();
    }

    @Test
    public void provisionTenant() {


        ProvisionTenantRepresentation ptr =
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


        HttpEntity requestEntity = new HttpEntity(ptr, this.httpHeaders());


        HttpEntity responseEntity
                = this.template().exchange(
                "http://localhost:" + SERVER_PORT + "/api/v1/tenants/provisions",
                HttpMethod.POST, requestEntity,
                String.class);


        System.out.println("  \n\n ");
        System.out.println("  \n\n responseEntity =================== " + responseEntity);
        System.out.println("  \n\n ");


        assertNotNull(responseEntity.getBody());
        //More asserts to come
    }

    @Test
    public void getTenants() throws Exception {


        HttpEntity requestEntity = new HttpEntity(this.httpHeaders());
        HttpEntity responseEntity = this.template().exchange("http://localhost:" + SERVER_PORT + "/api/v1/tenants", HttpMethod.GET, requestEntity, String.class);

        System.out.println("  \n\n requestEntity =================== " + requestEntity.getBody());
        System.out.println("  \n\n requestEntity =================== " + requestEntity.getBody());


        Collection<Tenant> tenants = ApplicationServiceRegistry.identityApplicationService().allTenants();


        System.out.println("  \n\n tenants =================== " + tenants);
        System.out.println("  \n\n tenants =================== " + tenants);

        //assertNotNull(responseEntity.getBody());
        //More asserts to come
    }

    @Test
    public void offerRegistrationInvitation() throws Exception {

        Tenant bingoTenant = this.bingoTenantAggregate();


        RegistrationInvitationRepresentation rir = new RegistrationInvitationRepresentation(
                "Cette invitation d'enregistrement aupres de diaspo gift est destinee a Bingo hospital",
                "",
                ZonedDateTime.now().minusDays(1),
                bingoTenant.tenantId().id(),
                ZonedDateTime.now().plusDays(1)
        );

        System.out.println("\n\n bingoTenant ================================== " + bingoTenant);
        System.out.println("\n\n rir ================================== " + rir);


        HttpEntity requestEntity = new HttpEntity(rir, this.httpHeaders());


       /* HttpEntity responseEntity = this.template().exchange("http://localhost:" + SERVER_PORT + "/api/v1/tenants/" +bingoTenant.tenantId()+ "/registration-invitations", HttpMethod.POST, requestEntity, String.class);


        System.out.println("\n\n responseEntity ================================== " + responseEntity);
        System.out.println("\n\n responseEntity.toString() ================================== " + responseEntity.toString());
        System.out.println("\n\n responseEntity.getBody() ================================== " + responseEntity.getBody());
        System.out.println("\n\n responseEntity.getHeaders ================================== " + responseEntity.getHeaders());

        assertNotNull(responseEntity.getBody());*/
        //More asserts to come
    }

}
