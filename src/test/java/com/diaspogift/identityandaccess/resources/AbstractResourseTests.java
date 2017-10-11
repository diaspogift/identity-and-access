package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.*;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;

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

    protected GroupRepresentation provisionTenantWithAGroup(ProvisionedTenantRepresentation aProvisionedTenantRepresentation, GroupRepresentation aGroupRepresentation, MockMvc mockMvc) throws Exception {

        Gson gson = new Gson();


        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + aProvisionedTenantRepresentation.getTenantId() + "/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(aGroupRepresentation).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        return gson.fromJson(mvcResult.getResponse().getContentAsString(), GroupRepresentation.class);
    }

    protected GroupMemberRepresentation addGroupMemberToGroup(ProvisionedTenantRepresentation bingoTenant, GroupRepresentation gr, GroupMemberRepresentation gmr, MockMvc mockMvc) throws Exception {

        Gson gson = new Gson();


        System.out.println("\n\n url ===== " + "/api/v1/tenants/" + bingoTenant.getTenantId() + "/groups/" + gr.getName() + "/members");


        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/groups/" + gr.getName() + "/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(gmr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        return gson.fromJson(mvcResult.getResponse().getContentAsString(), GroupMemberRepresentation.class);
    }


    public RegistrationInvitationRepresentation provisionTenantWithAUser(ProvisionedTenantRepresentation bingoTenant, UserRegistrationReprensentation urr, MockMvc mockMvc) throws Exception {


        RegistrationInvitationRepresentation rir = new RegistrationInvitationRepresentation(
                "Cette invitation d'enregistrement aupres de diaspo gift est destinee a Mrs. " + urr.getLastName() + " " + urr.getFirstName(),
                "",
                ZonedDateTime.parse(urr.getStartDate()),
                ZonedDateTime.parse(urr.getEndDate())
        );

        Gson gson = new Gson();


        MvcResult mvcResult1 =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/registration-invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(rir).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        RegistrationInvitationRepresentation registrationInvitationRepresentation =
                gson.fromJson(mvcResult1.getResponse().getContentAsString(), RegistrationInvitationRepresentation.class);

        System.out.println("\n\n registrationInvitationRepresentation  ===== " + registrationInvitationRepresentation);
        System.out.println("\n\n registrationInvitationRepresentation  ===== " + registrationInvitationRepresentation);
        System.out.println("\n\n registrationInvitationRepresentation  ===== " + registrationInvitationRepresentation);
        System.out.println("\n\n registrationInvitationRepresentation  ===== " + registrationInvitationRepresentation);

        urr.setInvitationIdentifier(registrationInvitationRepresentation.getInvitationId());
        /////////


        System.out.println("\n\n url ===== " + "/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/registrations");


        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(urr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        return gson.fromJson(mvcResult1.getResponse().getContentAsString(), RegistrationInvitationRepresentation.class);
    }


    public RegistrationInvitationRepresentation offerRegistrationInvitation(ProvisionedTenantRepresentation bingoTenant, UserRegistrationReprensentation urr, MockMvc mockMvc) throws Exception {


        RegistrationInvitationRepresentation rir = new RegistrationInvitationRepresentation(
                "Cette invitation d'enregistrement aupres de diaspo gift est destinee a Mrs. " + urr.getLastName() + " " + urr.getFirstName(),
                "",
                ZonedDateTime.parse(urr.getStartDate()),
                ZonedDateTime.parse(urr.getEndDate())
        );

        Gson gson = new Gson();


        MvcResult mvcResult1 =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/registration-invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(rir).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        return gson.fromJson(mvcResult1.getResponse().getContentAsString(), RegistrationInvitationRepresentation.class);


    }

    public RoleRepresentation provisionTenantWithARole(ProvisionedTenantRepresentation bingoTenant, RoleRepresentation rr, MockMvc mockMvc) throws Exception {

        System.out.println("\n\n rr in provisionTenantWithARole======== " + rr);
        System.out.println("\n\n rr in provisionTenantWithARole ======== " + rr);

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(rr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();


        System.out.println("\n\n mvcResult rr in provisionTenantWithARole ======== " + mvcResult.getResponse().getContentAsString());


        return gson.fromJson(mvcResult.getResponse().getContentAsString(), RoleRepresentation.class);

    }

    protected void addGroupToRole(ProvisionedTenantRepresentation bingoTenant, GroupRepresentation gr1, RoleRepresentation roleRepresentation, MockMvc mockMvc) throws Exception {


        Gson gson = new Gson();

        System.out.println("\n URL ======== /api/v1/tenants/" + bingoTenant.getTenantId() + "/roles/" + roleRepresentation.getName() + "/groups");

        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/roles/" + roleRepresentation.getName() + "/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(gr1).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

        System.out.println("\n mvcResult ======== " + mvcResult);

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
