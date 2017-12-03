package com.diaspogift.identityandaccess.port.adapter.resources;


import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.user.*;
import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.domain.model.identity.UserDescriptor;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/tenants/{tenantId}/users")
@Api(value = "iam", description = "Operations pertaining to users in the iam System")
public class UserResource {


    private static final Logger logger = LoggerFactory.getLogger(TenantResource.class);
    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;


    @ApiOperation(value = "Authenticate a user with username and password")
    @GetMapping("{username}/autenticated-with/{password}")
    public ResponseEntity<UserDescriptorRepresentation> getAuthenticUser(@PathVariable("tenantId") String aTenantId,
                                                                         @PathVariable("username") String aUsername,
                                                                         @PathVariable("password") String aPassword) throws DiaspoGiftRepositoryException {

        UserDescriptor userDescriptor = this.identityApplicationService().authenticateUser(
                new AuthenticateUserCommand(
                        aTenantId,
                        aUsername,
                        aPassword
                )
        );


        return new ResponseEntity<UserDescriptorRepresentation>(new UserDescriptorRepresentation(userDescriptor), HttpStatus.OK);
    }

    @ApiOperation(value = "Change a user password")
    @PostMapping("{username}/password")
    public ResponseEntity<UserChangedPasswordRepresentation> changeUserPassword(@PathVariable("tenantId") String aTenantId,
                                                                                @PathVariable("username") String username,
                                                                                @RequestBody UserChangedPasswordRepresentation userChangedPasswordRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserPassword(new ChangeUserPasswordCommand(aTenantId, username, userChangedPasswordRepresentation));

        return new ResponseEntity<UserChangedPasswordRepresentation>(userChangedPasswordRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Register a new user with username and password")
    @PostMapping("{username}/registrations")
    public ResponseEntity<UserDescriptorRepresentation> registerNewUser(@PathVariable("tenantId") String aTenantId,
                                                                        @PathVariable("username") String username,
                                                                        @RequestBody UserRegistrationReprensentation userRegistrationReprensentation) throws DiaspoGiftRepositoryException {

        if (aTenantId == null || username == null)
            throw new IllegalArgumentException("Wrong tenant or username provided");
        if (aTenantId != null &&
                username != null &&
                username.equals(userRegistrationReprensentation.getUsername()) &&
                !aTenantId.equals(userRegistrationReprensentation.getTenantId()))

            throw new IllegalArgumentException("Wrong tenant or username provided");

        User user = this.identityApplicationService().registerUser(new RegisterUserCommand(userRegistrationReprensentation));

        return new ResponseEntity<UserDescriptorRepresentation>(new UserDescriptorRepresentation(user.userDescriptor()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Change user enablement")
    @PostMapping("{username}/enablement")
    public ResponseEntity<UserEnablementReprensentation> defineUserEnablement(@PathVariable("tenantId") String aTenantId,
                                                                              @PathVariable("username") String username,
                                                                              @RequestBody UserEnablementReprensentation userEnablementReprensentation) throws DiaspoGiftRepositoryException {


        this.identityApplicationService().defineUserEnablement(new DefineUserEnablementCommand(aTenantId, username, userEnablementReprensentation));

        return new ResponseEntity<UserEnablementReprensentation>(userEnablementReprensentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieve user contact")
    @GetMapping("{username}/contact")
    public ResponseEntity<UserContactInformationRepresentation> getUserContactInformation(@PathVariable("tenantId") String aTenantId,
                                                                                          @PathVariable("username") String username) throws DiaspoGiftRepositoryException {

        UserContactInformationRepresentation userContactInformationRepresentation =
                new UserContactInformationRepresentation(this.identityApplicationService().userContactInformation(aTenantId, username));

        return new ResponseEntity<UserContactInformationRepresentation>(userContactInformationRepresentation, HttpStatus.OK);
    }

    @ApiOperation(value = "Change user contact")
    @PostMapping("{username}/contact")
    public ResponseEntity<UserContactInformationRepresentation> changeUserContactInformation(@PathVariable("tenantId") String aTenantId,
                                                                                             @PathVariable("username") String username,
                                                                                             @RequestBody UserContactInformationRepresentation userContactInformationRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserContactInformation(new ChangeContactInfoCommand(aTenantId, username, userContactInformationRepresentation));

        return new ResponseEntity<UserContactInformationRepresentation>(userContactInformationRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Change user email")
    @PostMapping("{username}/email")
    public ResponseEntity<UserEmailRepresentation> changeUserEmail(@PathVariable("tenantId") String aTenantId,
                                                                   @PathVariable("username") String username,
                                                                   @RequestBody UserEmailRepresentation userEmailRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserEmailAddress(new ChangeEmailAddressCommand(aTenantId, username, userEmailRepresentation));

        return new ResponseEntity<UserEmailRepresentation>(userEmailRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieve user email")
    @GetMapping("{username}/email")
    public ResponseEntity<UserEmailRepresentation> getUserEmail(@PathVariable("tenantId") String aTenantId,
                                                                @PathVariable("username") String username) throws DiaspoGiftRepositoryException {

        UserEmailRepresentation userEmailRepresentation = new UserEmailRepresentation(identityApplicationService().userEmailAddress(aTenantId, username));

        return new ResponseEntity<UserEmailRepresentation>(userEmailRepresentation, HttpStatus.OK);

    }

    @ApiOperation(value = "Change user name")
    @PostMapping("{username}/name")
    public ResponseEntity<UserPersonalNameRepresentation> changeUserPersonalName(@PathVariable("tenantId") String aTenantId,
                                                                                 @PathVariable("username") String username,
                                                                                 @RequestBody UserPersonalNameRepresentation userPersonalNameRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserPersonalName(new ChangeUserPersonalNameCommand(aTenantId, username, userPersonalNameRepresentation));

        return new ResponseEntity<UserPersonalNameRepresentation>(userPersonalNameRepresentation, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Retrieve user name")
    @GetMapping("{username}/name")
    public ResponseEntity<UserPersonalNameRepresentation> getUserPersonalName(@PathVariable("tenantId") String aTenantId,
                                                                              @PathVariable("username") String username) throws DiaspoGiftRepositoryException {

        UserPersonalNameRepresentation userPersonalNameRepresentation = new UserPersonalNameRepresentation(this.identityApplicationService().userPersonalName(aTenantId, username));

        return new ResponseEntity<UserPersonalNameRepresentation>(userPersonalNameRepresentation, HttpStatus.OK);

    }

    @ApiOperation(value = "Retrieve a user")
    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable("tenantId") String aTenantId,
                                        @PathVariable("username") String aUsername) throws DiaspoGiftRepositoryException {

        User user = this.identityApplicationService().user(aTenantId, aUsername);


        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }

    @ApiOperation(value = "Retrieve a user in role")
    @GetMapping("{username}/in-role/{roleName}")
    public ResponseEntity<UserRepresentation> getUserInRole(@PathVariable("tenantId") String aTenantId,
                                                            @PathVariable("username") String aUsername,
                                                            @PathVariable("roleName") String aRoleName) throws DiaspoGiftRepositoryException {


        System.out.println(" \n\n tenantId ==== " + aTenantId + " username === " + aUsername + " roleName === " + aRoleName);
        System.out.println(" \n\n tenantId ==== " + aTenantId + " username === " + aUsername + " roleName === " + aRoleName);

        System.out.println("\n\n " + "HERE THE USER I MUST FIND LATER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        User user1 = DomainRegistry.userRepository().userWithUsername(new TenantId(aTenantId), aUsername);

        System.out.println("\n\n " + "HERE THE USER I MUST FIND LATER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user1);

        UserRepresentation userRepresentation = null;

        User user = this.accessApplicationService()
                .userInRole(
                        aTenantId,
                        aUsername,
                        aRoleName);

        if (user != null) {

            userRepresentation = new UserRepresentation(user);

        }

        return new ResponseEntity<UserRepresentation>(userRepresentation, HttpStatus.OK);
    }


    @ApiOperation(value = "Retrieve all users")
    @GetMapping
    public ResponseEntity<UserDescriptorCollectionRepresentation> getUsers(@PathVariable("tenantId") String aTenantId) throws DiaspoGiftRepositoryException {

        UserDescriptorCollectionRepresentation userDescriptorCollectionRepresentation =
                new UserDescriptorCollectionRepresentation(this.identityApplicationService().allUserFor(aTenantId));

        return new ResponseEntity<UserDescriptorCollectionRepresentation>(userDescriptorCollectionRepresentation, HttpStatus.FOUND);
    }


    public IdentityApplicationService identityApplicationService() {
        return this.identityApplicationService;
    }

    public AccessApplicationService accessApplicationService() {
        return this.accessApplicationService;
    }


}
