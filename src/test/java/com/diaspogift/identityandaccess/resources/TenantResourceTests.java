package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.tenant.ProvisionTenantRepresentation;
import com.diaspogift.identityandaccess.application.representation.tenant.ProvisionedTenantRepresentation;
import com.diaspogift.identityandaccess.application.representation.tenant.RegistrationInvitationReqRepresentation;
import com.diaspogift.identityandaccess.application.representation.tenant.TenantAvailabilityRepresentation;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class TenantResourceTests extends AbstractResourseTests {


    private MockMvc mockMvc;
    private ProvisionedTenantRepresentation bingoTenant;
    private ProvisionedTenantRepresentation cadeauxTenant;


    @Autowired
    private WebApplicationContext webApplicationContext;


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
    }

    @Test
    public void getTenantProvision() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/provisions"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.tenantId", is(this.bingoTenant.getTenantId())))
                        .andExpect(jsonPath("$.tenantName", is(this.bingoTenant.getTenantName())))
                        .andExpect(jsonPath("$.tenantDescription", is(this.bingoTenant.getTenantDescription())))
                        .andReturn();
    }

    @Test
    public void getTenants() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);
        this.cadeauxTenant = this.cadeauxTenantAggregate(this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.tenants", hasSize(2)))
                        .andReturn();

    }

    @Test
    public void offerRegistrationInvitation() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        RegistrationInvitationReqRepresentation rir = new RegistrationInvitationReqRepresentation(
                "Cette invitation d'enregistrement aupres de diaspo gift est destinee a Bingo hospital",
                ZonedDateTime.now().minusDays(1).toString(),
                ZonedDateTime.now().plusDays(1).toString()
                /*""*/
        );

        Gson gson = new Gson();


        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/registration-invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(rir).toString()))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.description", is(rir.getDescription())))
                        .andExpect(jsonPath("$.startingOn", is(rir.getStartingOn())))
                        .andExpect(jsonPath("$.until", is(rir.getUntil())))
                        .andReturn();

        System.out.println(" \n\n RESULT ))))))))))))===============)))))))))))))))) " + mvcResult.getResponse());
        System.out.println(" \n\n RESULT ))))))))))))===============)))))))))))))))) " + mvcResult.getResponse().getContentAsString());


    }

    @Test
    public void getTenant() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.tenantId", is(this.bingoTenant.getTenantId())))
                        .andExpect(jsonPath("$.name", is(this.bingoTenant.getTenantName())))
                        .andExpect(jsonPath("$.description", is(this.bingoTenant.getTenantDescription())))
                        .andReturn();


    }

    @Test
    public void changeTenantAvailability() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        TenantAvailabilityRepresentation tar = new TenantAvailabilityRepresentation(false);

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/availability-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(tar).toString()))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.active", is(false)))
                        .andReturn();

    }

    @Test
    public void getTenantAvailability() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);


        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/availability-status"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.active", is(true)))
                        .andReturn();

    }

    @Before
    public void setUp() throws Exception {

        super.setUp();
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();

        this.mockMvc = null;
        this.bingoTenant = null;
        this.cadeauxTenant = null;
    }
}
