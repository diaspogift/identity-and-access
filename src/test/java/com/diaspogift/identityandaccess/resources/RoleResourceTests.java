package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.*;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class RoleResourceTests extends AbstractResourseTests {


    private MockMvc mockMvc;
    private ProvisionedTenantRepresentation bingoTenant;
    private ProvisionedTenantRepresentation cadeauxTenant;
    private GroupRepresentation groupRepresentation;
    private GroupMemberRepresentation userGroupMemberRepresentation;
    private GroupMemberRepresentation groupGroupMemberRepresentation;
    private RegistrationInvitationRepresentation registrationInvitationRepresentation;
    private RoleRepresentation roleRepresentation;


    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void getTenantRoles() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        RoleRepresentation rr = new RoleRepresentation("MEDECIN-CHEF", "Gere les medecin d'un block", true);

        this.roleRepresentation = this.provisionTenantWithARole(this.bingoTenant, rr, this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/roles"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.roles", hasSize(2)))
                        .andReturn();


        System.out.println(" \n\n roles ======== " + mvcResult.getResponse().getContentAsString());


    }

    @Test
    public void addTenantRoles() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        RoleRepresentation rr = new RoleRepresentation("MEDECIN-CHEF", "Gere les medecin d'un block", true);

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(rr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();


        System.out.println("\n\n mvcResult rr in provisionTenantWithARole ======== " + mvcResult.getResponse().getContentAsString());


        MvcResult mvcResult1 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/roles"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.roles", hasSize(2)))
                        .andReturn();


        System.out.println(" \n\n roles ======== " + mvcResult1.getResponse().getContentAsString());


    }

    @Test
    public void getTenantRole() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        RoleRepresentation rr = new RoleRepresentation("MEDECIN-CHEF", "Gere les medecin d'un block", true);

        this.roleRepresentation = this.provisionTenantWithARole(this.bingoTenant, rr, this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/roles/" + rr.getName()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is(rr.getName())))
                        .andExpect(jsonPath("$.description", is(rr.getDescription())))
                        .andExpect(jsonPath("$.supportsNesting", is(rr.isSupportsNesting())))
                        .andReturn();


        System.out.println(" \n\n role ======== " + mvcResult.getResponse().getContentAsString());

    }


    //TO DO Exception handling
    @Test
    public void removeTenantRole() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        RoleRepresentation rr = new RoleRepresentation("MEDECIN-CHEF", "Gere les medecin d'un block", true);

        this.roleRepresentation = this.provisionTenantWithARole(this.bingoTenant, rr, this.mockMvc);


        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/roles/" + rr.getName()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is(rr.getName())))
                        .andExpect(jsonPath("$.description", is(rr.getDescription())))
                        .andExpect(jsonPath("$.supportsNesting", is(rr.isSupportsNesting())))
                        .andReturn();


        System.out.println(" \n\n role ======== " + mvcResult.getResponse().getContentAsString());


        MvcResult mvcResult1 =

                mockMvc.perform(delete("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/roles/" + rr.getName()))
                        .andExpect(status().isNoContent())
                        .andReturn();


        System.out.println(" \n\n role ======== " + mvcResult1.getResponse().getContentAsString());



      /*  MvcResult mvcResult2 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/roles/" + rr.getName()))
                        .andExpect(status().isNotFound())
                        .andReturn();


        System.out.println(" \n\n deleted role ======== " + mvcResult2.getResponse().getContentAsString());*/

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
