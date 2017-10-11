package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.representation.*;
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
public class UserResourceTests extends AbstractResourseTests {


    private MockMvc mockMvc;
    private ProvisionedTenantRepresentation bingoTenant;
    private ProvisionedTenantRepresentation cadeauxTenant;
    private RegistrationInvitationRepresentation registrationInvitationRepresentation;
    private GroupRepresentation groupRepresentation;
    private RoleRepresentation roleRepresentation;


    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void registerNewUser() throws Exception {

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

        RegistrationInvitationRepresentation rir = this.offerRegistrationInvitation(this.bingoTenant, urr, this.mockMvc);

        urr.setInvitationIdentifier(rir.getInvitationId());

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(urr).toString()))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        .andExpect(jsonPath("$.username", is(urr.getUsername())))
                        .andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult === " + mvcResult.getResponse().getContentAsString());


    }


    @Test
    public void getUserNotFound() throws Exception {

        this.bingoTenant = this.bingoTenantAggregate(this.mockMvc);


        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + "nonRegisterUsername"))
                        .andExpect(status().isNotFound())
                        .andReturn();

        System.out.println(" \n\n mvcResult === " + mvcResult.getResponse().getContentAsString());


    }

    @Test
    public void getAuthenticUser() throws Exception {

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

        RegistrationInvitationRepresentation rir = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(rir.getInvitationId());

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/autenticated-with/" + urr.getPassword())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(urr).toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        .andExpect(jsonPath("$.username", is(urr.getUsername())))
                        .andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult === " + mvcResult.getResponse().getContentAsString());


    }


    @Test
    public void changeUserPassword() throws Exception {

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

        RegistrationInvitationRepresentation rir = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(rir.getInvitationId());

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/autenticated-with/" + urr.getPassword())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(urr).toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        .andExpect(jsonPath("$.username", is(urr.getUsername())))
                        .andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult === " + mvcResult.getResponse().getContentAsString());

        UserChangedPasswordRepresentation ucp = new UserChangedPasswordRepresentation(urr.getPassword(), "NewSecret@@2008Password");


        MvcResult mvcResult1 =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(ucp).toString()))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.currentPassword", is(urr.getPassword())))
                        .andExpect(jsonPath("$.changedPassword", is(ucp.getChangedPassword())))
                        .andReturn();

        System.out.println(" \n\n mvcResult1 === " + mvcResult1.getResponse().getContentAsString());


        MvcResult mvcResult2 =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/autenticated-with/" + urr.getPassword())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(urr).toString()))
                        .andExpect(status().isNotFound())
                        .andReturn();

        System.out.println(" \n\n mvcResult2 === " + mvcResult2.getResponse().getContentAsString());


        MvcResult mvcResult3 =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/autenticated-with/" + ucp.getChangedPassword())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(urr).toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        .andExpect(jsonPath("$.username", is(urr.getUsername())))
                        .andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult3 === " + mvcResult3.getResponse().getContentAsString());


    }

    @Test
    public void defineUserEnablement() throws Exception {

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

        RegistrationInvitationRepresentation rir = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(rir.getInvitationId());

        Gson gson = new Gson();


        String startDate = ZonedDateTime.now().minusDays(2l).toString();
        String endDateDate = ZonedDateTime.now().minusDays(2l).toString();


        UserEnablementReprensentation uep = new UserEnablementReprensentation(false, startDate, endDateDate);


        MvcResult mvcResult1 =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/enablement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(uep).toString()))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.enabled", is(uep.isEnabled())))
                        .andExpect(jsonPath("$.startDate", is(uep.getStartDate())))
                        .andExpect(jsonPath("$.endDate", is(uep.getEndDate())))
                        .andReturn();

        System.out.println(" \n\n mvcResult1 === " + mvcResult1.getResponse().getContentAsString());

    }

    @Test
    public void changeUserContactInformation() throws Exception {

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

        RegistrationInvitationRepresentation rir = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(rir.getInvitationId());

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/contact"))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        //.andExpect(jsonPath("$.username", is(urr.getUsername())))
                        //.andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult in changeUserContactInformation === " + mvcResult.getResponse().getContentAsString());

        UserContactInformationRepresentation ucir =

                new UserContactInformationRepresentation(
                        "feliciennew@yahoo.fr", "Yaounde", "CM",
                        "80220", "Centre", "Carrefour Obili",
                        "CM", "00237", "675676752",
                        "CM", "00237", "675676752");


        MvcResult mvcResult1 =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(ucir).toString()))
                        .andExpect(status().isCreated())
                        //.andExpect(jsonPath("$.currentPassword", is(urr.getPassword())))
                        //.andExpect(jsonPath("$.changedPassword", is(ucp.getChangedPassword())))
                        .andReturn();

        System.out.println(" \n\n mvcResult1 in changeUserContactInformation === " + mvcResult1.getResponse().getContentAsString());


        MvcResult mvcResult3 =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/contact"))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        //.andExpect(jsonPath("$.username", is(urr.getUsername())))
                        //.andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult3 in changeUserContactInformation === " + mvcResult3.getResponse().getContentAsString());


    }

    @Test
    public void changeUserEmail() throws Exception {

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

        RegistrationInvitationRepresentation rir = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(rir.getInvitationId());

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/email"))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        //.andExpect(jsonPath("$.username", is(urr.getUsername())))
                        //.andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult in changeUserContactInformation === " + mvcResult.getResponse().getContentAsString());

        UserEmailRepresentation uer = new UserEmailRepresentation("feliciennew@yahoo.fr");


        MvcResult mvcResult1 =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(uer).toString()))
                        .andExpect(status().isCreated())
                        //.andExpect(jsonPath("$.currentPassword", is(urr.getPassword())))
                        //.andExpect(jsonPath("$.changedPassword", is(ucp.getChangedPassword())))
                        .andReturn();

        System.out.println(" \n\n mvcResult1 in changeUserContactInformation === " + mvcResult1.getResponse().getContentAsString());


        MvcResult mvcResult3 =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/email"))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        //.andExpect(jsonPath("$.username", is(urr.getUsername())))
                        //.andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult3 in changeUserContactInformation === " + mvcResult3.getResponse().getContentAsString());


    }

    @Test
    public void changeUserPersonalName() throws Exception {

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

        RegistrationInvitationRepresentation rir = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(rir.getInvitationId());

        Gson gson = new Gson();

        MvcResult mvcResult =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/name"))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        //.andExpect(jsonPath("$.username", is(urr.getUsername())))
                        //.andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult in changeUserContactInformation === " + mvcResult.getResponse().getContentAsString());

        UserPersonalNameRepresentation upnr = new UserPersonalNameRepresentation("Newfelicien", "Newfotio");


        MvcResult mvcResult1 =

                mockMvc.perform(post("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(upnr).toString()))
                        .andExpect(status().isCreated())
                        //.andExpect(jsonPath("$.currentPassword", is(urr.getPassword())))
                        //.andExpect(jsonPath("$.changedPassword", is(ucp.getChangedPassword())))
                        .andReturn();

        System.out.println(" \n\n mvcResult1 in changeUserContactInformation === " + mvcResult1.getResponse().getContentAsString());


        MvcResult mvcResult3 =

                mockMvc.perform(get("/api/v1/tenants/" + bingoTenant.getTenantId() + "/users/" + urr.getUsername() + "/name"))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.tenantId", is(bingoTenant.getTenantId())))
                        //.andExpect(jsonPath("$.username", is(urr.getUsername())))
                        //.andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andReturn();

        System.out.println(" \n\n mvcResult3 in changeUserContactInformation === " + mvcResult3.getResponse().getContentAsString());


    }


    @Test
    public void getUserInRole2() throws Exception {

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
        RoleRepresentation rr = new RoleRepresentation("SOIGNANT", "Tute personne pouvant soigner", true);

        this.groupRepresentation = this.provisionTenantWithAGroup(this.bingoTenant, gr1, this.mockMvc);
        this.roleRepresentation = this.provisionTenantWithARole(this.bingoTenant, rr, this.mockMvc);
        this.registrationInvitationRepresentation = this.provisionTenantWithAUser(this.bingoTenant, urr, this.mockMvc);
        urr.setInvitationIdentifier(this.registrationInvitationRepresentation.getInvitationId());
        this.addGroupMemberToGroup(this.bingoTenant, gr1, ugmr, this.mockMvc);
        this.addGroupToRole(this.bingoTenant, gr1, this.roleRepresentation, this.mockMvc);


        MvcResult mvcResult1 =

                mockMvc.perform(get("/api/v1/tenants/" + this.bingoTenant.getTenantId() + "/users/" + ugmr.getName() + "/in-role/" + rr.getName()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.emailAddress", is(urr.getEmailAddress())))
                        .andExpect(jsonPath("$.enabled", is(urr.isEnabled())))
                        .andExpect(jsonPath("$.firstName", is(urr.getFirstName())))
                        .andExpect(jsonPath("$.lastName", is(urr.getLastName())))
                        .andExpect(jsonPath("$.tenantId", is(urr.getTenantId())))
                        .andReturn();

        System.out.println(" \n\n mvcResult1 === " + mvcResult1.getResponse().getContentAsString());


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
        this.cadeauxTenant = null;
        this.registrationInvitationRepresentation = null;
    }
}
