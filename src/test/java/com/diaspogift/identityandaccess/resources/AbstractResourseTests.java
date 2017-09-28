package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.ProvisionTenantRepresentation;
import com.diaspogift.identityandaccess.application.representation.ProvisionedTenantRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public abstract class AbstractResourseTests {


    private Tenant bingoTenant;
    private Tenant cadeauxTenant;


    public AbstractResourseTests() {
        super();
    }

    @Before
    public void setUp() throws Exception {

        System.out.println("\n  bingoTenant, cadeauxTenant In setUp " + this.bingoTenant + " " + this.cadeauxTenant);


        this.bingoTenant = null;
        this.cadeauxTenant = null;

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Started " + AbstractResourseTests.class.getSimpleName());
    }

    protected ProvisionedTenantRepresentation cadeauxTenantAggregate(MockMvc mockMvc) throws Exception {

        ProvisionTenantRepresentation ptr1 =

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


        MvcResult reponseCadeauxTenant =

                mockMvc.perform(post("/api/v1/tenants/provisions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(ptr1).toString()))
                        .andExpect(status().isCreated()).andReturn();


        return gson.fromJson(reponseCadeauxTenant.getResponse().getContentAsString(), ProvisionedTenantRepresentation.class);
    }

    protected ProvisionedTenantRepresentation bingoTenantAggregate(MockMvc mockMvc) throws Exception {

        ProvisionTenantRepresentation ptr2 =

                new ProvisionTenantRepresentation(
                        "Bingo",
                        "Hopital Bingo de Deido",
                        "Felicien",
                        "Fotio",
                        "felicien@yahoo.fr",
                        "669264532",
                        "669264532",
                        "CM",
                        "00237",
                        "CM",
                        "00237",
                        "Vallet 3 boutiques",
                        "Douala",
                        "Littoral",
                        "80215",
                        "CM"
                );

        Gson gson = new Gson();


        MvcResult reponseBingoTenant =

                mockMvc.perform(post("/api/v1/tenants/provisions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(ptr2).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();


        return gson.fromJson(reponseBingoTenant.getResponse().getContentAsString(), ProvisionedTenantRepresentation.class);
    }


    @After
    public void tearDown() throws Exception {

        System.out.println("\n  bingoTenant, cadeauxTenant In tearDown " + this.bingoTenant + " " + this.cadeauxTenant);


        this.bingoTenant = null;
        this.cadeauxTenant = null;

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<< Done " + AbstractResourseTests.class.getSimpleName());

    }

    public Tenant bingoTenant() {
        return this.bingoTenant;
    }

    public Tenant cadeauxTenant() {
        return this.cadeauxTenant;
    }

}
