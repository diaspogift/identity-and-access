package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.group.GroupMemberRepresentation;
import com.diaspogift.identityandaccess.application.representation.group.GroupRepresentation;
import com.diaspogift.identityandaccess.application.representation.tenant.ProvisionedTenantRepresentation;
import com.diaspogift.identityandaccess.application.representation.tenant.RegistrationInvitationRespRepresentation;
import com.diaspogift.identityandaccess.application.representation.user.UserRegistrationReprensentation;
import com.diaspogift.identityandaccess.domain.model.identity.GroupMemberType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class GroupResourceTests extends AbstractResourseTests {


    private MockMvc mockMvc;
    private ProvisionedTenantRepresentation bingoTenant;
    private ProvisionedTenantRepresentation cadeauxTenant;
    private GroupRepresentation groupRepresentation;
    private GroupMemberRepresentation userGroupMemberRepresentation;
    private GroupMemberRepresentation groupGroupMemberRepresentation;
    private RegistrationInvitationRespRepresentation registrationInvitationRespRepresentation;


    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void getGroups() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.groups", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();
    }

    @Test
    public void createGroup() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        GroupRepresentation gr = new GroupRepresentation("INFIRMIERS-STAGAIRES", "Tous infirmiers venant ici d'ailleur pour une formation quelconque");

        Gson gson = new Gson();


        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(gr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();

    }

    @Test
    public void getGroup() throws Exception {

        GroupRepresentation gr = new GroupRepresentation("INFIRMIERS-STAGAIRES", "Tous infirmiers venant ici d'ailleur pour une formation quelconque");

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);
        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr, this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + gr.getName()))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.groups", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();


        System.out.println("\n\n mvcResult ===== " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void removeGroup() throws Exception {

        GroupRepresentation gr = new GroupRepresentation("INFIRMIERS-STAGAIRES", "Tous infirmiers venant ici d'ailleur pour une formation quelconque");

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);
        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr, this.mockMvc);

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + gr.getName()))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.groups", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();


        System.out.println("\n\n mvcResult ===== " + mvcResult.getResponse().getContentAsString());


        MvcResult mvcResult1 =

                mockMvc.perform(delete("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + gr.getName()))
                        .andExpect(status().isNoContent())
                        //.andExpect(jsonPath("$.groups", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();

        System.out.println("\n\n mvcResult1 ===== " + mvcResult1.getResponse().getContentAsString());


        MvcResult mvcResult2 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + gr.getName()))
                        .andExpect(status().isNotFound())
                        //.andExpect(jsonPath("$.groups", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();


        System.out.println("\n\n mvcResult2 ===== " + mvcResult2.getResponse().getContentAsString());

    }

    @Test
    public void getGroupMembers() throws Exception {

        GroupRepresentation gr1 = new GroupRepresentation("INFIRMIERS", "Tous infirmiers d'ici comme d'ailleur.");
        GroupRepresentation gr2 = new GroupRepresentation("INFIRMIERS-STAGAIRES", "Tous infirmiers venant ici d'ailleur pour une formation quelconque");


        GroupMemberRepresentation ggmr = new GroupMemberRepresentation("INFIRMIERS-STAGAIRES", GroupMemberType.Group.name());
        GroupMemberRepresentation ugmr = new GroupMemberRepresentation("elberto2008", GroupMemberType.User.name());

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        UserRegistrationReprensentation urr =
                new UserRegistrationReprensentation(
                        this.bingoTenant.getTenantId(),
                        null,
                        "elberto2008",
                        "Secret@@2008Password",
                        "Felicien",
                        "Fotio",
                        true,
                        ZonedDateTime.now().minusDays(1).toString(),
                        ZonedDateTime.now().plusDays(1).toString(),
                        "felicien@yahoo.fr",
                        "669262656",
                        "669262656",
                        "CM",
                        "00237",
                        "CM",
                        "00237",
                        "Denver Rond Point Laureat",
                        "Douala",
                        "Littoral",
                        "80209",
                        "CM");
        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr1, this.mockMvc);
        this.provisionTenantWithAGroup(this.bingoTenant, gr2, this.mockMvc);
        this.registrationInvitationRespRepresentation = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        this.userGroupMemberRepresentation = this.addGroupMemberToGroup(this.bingoTenant, this.groupRepresentation, ugmr, this.mockMvc);
        this.groupGroupMemberRepresentation = this.addGroupMemberToGroup(this.bingoTenant, this.groupRepresentation, ggmr, this.mockMvc);


        System.out.println("\n\n url ===== " + "/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + gr1.getName() + "/members");


        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + gr1.getName() + "/members"))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.groupMembers", hasSize(2)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();


        System.out.println("\n\n mvcResult ===== " + mvcResult.getResponse().getContentAsString());


    }

    @Test
    public void createGroupMember_of_type_group() throws Exception {

        GroupRepresentation gr1 = new GroupRepresentation("INFIRMIERS", "Tous infirmiers d'ici comme d'ailleur.");
        GroupRepresentation gr2 = new GroupRepresentation("INFIRMIERS-STAGAIRES", "Tous infirmiers venant ici d'ailleur pour une formation quelconque");

        Gson gson = new Gson();


        GroupMemberRepresentation ggmr = new GroupMemberRepresentation(gr2.getName(), GroupMemberType.Group.name());

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);


        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr1, this.mockMvc);
        this.provisionTenantWithAGroup(this.bingoTenant, gr2, this.mockMvc);
        // this.userDescriptorRepresentation = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);


        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(ggmr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();


        System.out.println("\n\n mvcResult ===== " + mvcResult.getResponse().getContentAsString());


        MvcResult mvcResult1 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.groupMembers", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();

        System.out.println("\n\n mvcResult1 ===== " + mvcResult1.getResponse().getContentAsString());


    }

    @Test
    public void createGroupMember_of_type_user() throws Exception {

        GroupRepresentation gr1 = new GroupRepresentation("INFIRMIERS", "Tous infirmiers d'ici comme d'ailleur.");

        Gson gson = new Gson();

        GroupMemberRepresentation ugmr = new GroupMemberRepresentation("elberto2008", GroupMemberType.User.name());


        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        UserRegistrationReprensentation urr =
                new UserRegistrationReprensentation(
                        this.bingoTenant.getTenantId(),
                        null,
                        "elberto2008",
                        "Secret@@2008Password",
                        "Felicien",
                        "Fotio",
                        true,
                        ZonedDateTime.now().minusDays(1).toString(),
                        ZonedDateTime.now().plusDays(1).toString(),
                        "felicien@yahoo.fr",
                        "669262656",
                        "669262656",
                        "CM",
                        "00237",
                        "CM",
                        "00237",
                        "Denver Rond Point Laureat",
                        "Douala",
                        "Littoral",
                        "80209",
                        "CM");
        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr1, this.mockMvc);
        this.registrationInvitationRespRepresentation = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(this.registrationInvitationRespRepresentation.getInvitationId());


        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(ugmr).toString()))
                        .andExpect(status().isCreated())
                        .andReturn();


        System.out.println("\n\n mvcResult ===== " + mvcResult.getResponse().getContentAsString());


        MvcResult mvcResult1 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.groupMembers", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();

        System.out.println("\n\n mvcResult1 ===== " + mvcResult1.getResponse().getContentAsString());


    }


    @Test
    public void removeGroupMember_of_type_group() throws Exception {

        GroupRepresentation gr1 = new GroupRepresentation("INFIRMIERS", "Tous infirmiers d'ici comme d'ailleur.");
        GroupRepresentation gr2 = new GroupRepresentation("INFIRMIERS-STAGAIRES", "Tous infirmiers venant ici d'ailleur pour une formation quelconque");

        Gson gson = new Gson();


        GroupMemberRepresentation ggmr = new GroupMemberRepresentation(gr2.getName(), GroupMemberType.Group.name());

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);


        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr1, this.mockMvc);
        this.provisionTenantWithAGroup(this.bingoTenant, gr2, this.mockMvc);
        this.groupGroupMemberRepresentation = this.addGroupMemberToGroup(this.bingoTenant, this.groupRepresentation, ggmr, this.mockMvc);


        MvcResult mvcResult1 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.groupMembers", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();

        System.out.println("\n\n mvcResult1 ===== " + mvcResult1.getResponse().getContentAsString());


        MvcResult mvcResult2 =

                mockMvc.perform(delete("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members/" + ggmr.getName() + "?type=Group"))
                        .andExpect(status().isNoContent())
                        //.andExpect(jsonPath("$.groups", hasSize(1)))
                        //.andExpect(jsonPath("$.groups[0].name", is("Administrator")))
                        //.andExpect(jsonPath("$.groups[0].name", is("")))
                        .andReturn();

        System.out.println("\n\n mvcResult2 ===== " + mvcResult2.getResponse().getContentAsString());


        MvcResult mvcResult3 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.groupMembers", hasSize(0)))
                        .andReturn();

        System.out.println("\n\n mvcResult3 ===== " + mvcResult3.getResponse().getContentAsString());


    }

    @Test
    public void removeGroupMember_of_type_user() throws Exception {

        GroupRepresentation gr1 = new GroupRepresentation("INFIRMIERS", "Tous infirmiers d'ici comme d'ailleur.");

        Gson gson = new Gson();

        GroupMemberRepresentation ugmr = new GroupMemberRepresentation("elberto2008", GroupMemberType.User.name());


        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);

        UserRegistrationReprensentation urr =
                new UserRegistrationReprensentation(
                        this.bingoTenant.getTenantId(),
                        null,
                        "elberto2008",
                        "Secret@@2008Password",
                        "Felicien",
                        "Fotio",
                        true,
                        ZonedDateTime.now().minusDays(1).toString(),
                        ZonedDateTime.now().plusDays(1).toString(),
                        "felicien@yahoo.fr",
                        "669262656",
                        "669262656",
                        "CM",
                        "00237",
                        "CM",
                        "00237",
                        "Denver Rond Point Laureat",
                        "Douala",
                        "Littoral",
                        "80209",
                        "CM");
        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr1, this.mockMvc);
        this.registrationInvitationRespRepresentation = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        this.userGroupMemberRepresentation = this.addGroupMemberToGroup(this.bingoTenant, this.groupRepresentation, ugmr, this.mockMvc);
        urr.setInvitationIdentifier(this.registrationInvitationRespRepresentation.getInvitationId());


        MvcResult mvcResult1 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.groupMembers", hasSize(1)))
                        .andReturn();

        System.out.println("\n\n mvcResult1 ===== " + mvcResult1.getResponse().getContentAsString());


        MvcResult mvcResult2 =

                mockMvc.perform(delete("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members/" + ugmr.getName() + "?type=User"))
                        .andExpect(status().isNoContent())
                        .andReturn();

        System.out.println("\n\n mvcResult2 ===== " + mvcResult2.getResponse().getContentAsString());


        MvcResult mvcResult3 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/groups/" + this.groupRepresentation.getName() + "/members"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.groupMembers", hasSize(0)))
                        .andReturn();

        System.out.println("\n\n mvcResult3 ===== " + mvcResult3.getResponse().getContentAsString());


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
