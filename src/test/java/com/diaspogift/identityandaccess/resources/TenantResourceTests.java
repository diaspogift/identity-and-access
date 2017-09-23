package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.ProvisionTenantRepresentation;
import com.diaspogift.identityandaccess.application.representation.TenantRepresentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TenantResourceTests {


    @LocalServerPort
    private int port;

    @Test
    @Rollback(false)
    public void provisionTenant() {

        RestTemplate template = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();

        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        httpHeaders.setAccept(acceptableMediaTypes);

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


        HttpEntity<ProvisionTenantRepresentation> requestEntity = new HttpEntity<ProvisionTenantRepresentation>(provisionTenantRepresentation, httpHeaders);


        ResponseEntity<TenantRepresentation> responseEntity
                = template.exchange("http://localhost:" + port + "/api/v1/tenants/provisions", HttpMethod.POST, requestEntity, TenantRepresentation.class);


        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        //Mor asserts to come


    }
}
