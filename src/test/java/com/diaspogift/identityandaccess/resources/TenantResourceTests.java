package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.ProvisionTenantRepresentation;
import com.diaspogift.identityandaccess.application.representation.RegistrationInvitationRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TenantResourceTests extends AbstractResourseTests {


    @Autowired
    private MockMvc mockMvc;


    public TenantResourceTests() {
        super();
    }

    @Test
    public void provisionTenant() throws Exception {


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


        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/provisions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(ptr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        System.out.println(" mvcResult.getResponse().getContentAsString() ====================== " + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void getTenants() throws Exception {

        Tenant bingoTenant = this.bingoTenantAggregate();
        Tenant cadeauxTenant = this.cadeauxTenantAggregate();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.tenants", hasSize(2)))
                        .andReturn();


    }

    @Test
    public void offerRegistrationInvitation() throws Exception {

        Tenant bingoTenant = this.bingoTenantAggregate();


        RegistrationInvitationRepresentation rir = new RegistrationInvitationRepresentation(
                "Cette invitation d'enregistrement aupres de diaspo gift est destinee a Bingo hospital",
                "",
                ZonedDateTime.now().minusDays(1),
                ZonedDateTime.now().plusDays(1)
        );

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.tenantId().id() + "/registration-invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(rir).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        System.out.println(" \n\n");
        System.out.println(" mvcResult.getResponse().getContentAsString() ====================== " + mvcResult.getResponse().getContentAsString());
        System.out.println(" \n\n");

    }

}
