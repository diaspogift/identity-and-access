package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.*;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.domain.model.identity.UserDescriptor;
import com.diaspogift.identityandaccess.infrastructure.persistence.exception.DiaspoGiftRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(path = "/api/v1/tenants/{tenantId}/users")
public class UserResource {


    private static final Logger logger = LoggerFactory.getLogger(TenantResource.class);
    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;

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


        return new ResponseEntity<UserDescriptorRepresentation>(new UserDescriptorRepresentation(userDescriptor), HttpStatus.FOUND);
    }


    @PostMapping("{username}/password")
    public ResponseEntity<UserChangedPasswordRepresentation> changeUserPassword(@PathVariable("tenantId") String aTenantId,
                                                                                 @PathVariable("username") String username,
                                                                                 @RequestBody UserChangedPasswordRepresentation userChangedPasswordRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserPassword(new ChangeUserPasswordCommand(aTenantId, username, userChangedPasswordRepresentation));

        return new ResponseEntity<UserChangedPasswordRepresentation>(userChangedPasswordRepresentation, HttpStatus.CREATED);
    }

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

        return new ResponseEntity<UserDescriptorRepresentation>(new UserDescriptorRepresentation(user.userDescriptor()), HttpStatus.FOUND);
    }

    @PostMapping("{username}/enablement")
    public ResponseEntity<UserEnablementReprensentation> defineUserEnablement(@PathVariable("tenantId") String aTenantId,
                                                                              @PathVariable("username") String username,
                                                                              @RequestBody UserEnablementReprensentation userEnablementReprensentation) throws DiaspoGiftRepositoryException {

        if (aTenantId == null || username == null)
            throw new IllegalArgumentException("Wrong tenant or username provided");
        if (aTenantId != null &&
                username != null &&
                username.equals(userEnablementReprensentation.getUsername()) &&
                !aTenantId.equals(userEnablementReprensentation.getTenantId()))

            throw new IllegalArgumentException("Wrong tenant or username provided");

        this.identityApplicationService().defineUserEnablement(new DefineUserEnablementCommand(userEnablementReprensentation));

        return new ResponseEntity<UserEnablementReprensentation>(userEnablementReprensentation, HttpStatus.CREATED);
    }


    @GetMapping("{username}/contact")
    public ResponseEntity<UserContactInformationRepresentation> changeUserContactInformation(@PathVariable("tenantId") String aTenantId,
                                                                              @PathVariable("username") String username) throws DiaspoGiftRepositoryException {

        UserContactInformationRepresentation userContactInformationRepresentation =
                new UserContactInformationRepresentation(this.identityApplicationService().userContactInformation(aTenantId, username));

        userContactInformationRepresentation.setTenantId(aTenantId);
        userContactInformationRepresentation.setUsername(username);

        return new ResponseEntity<UserContactInformationRepresentation>(userContactInformationRepresentation, HttpStatus.OK);
    }



    @PostMapping("{username}/contact")
    public ResponseEntity<UserContactInformationRepresentation> getUserContactInformation(@PathVariable("tenantId") String aTenantId,
                                                                                             @PathVariable("username") String username,
                                                                                             @RequestBody UserContactInformationRepresentation userContactInformationRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserContactInformation(new ChangeContactInfoCommand(userContactInformationRepresentation));

        return new ResponseEntity<UserContactInformationRepresentation>(userContactInformationRepresentation, HttpStatus.CREATED);
    }









    @PostMapping("{username}/email")
    public ResponseEntity<UserEmailRepresentation> changeUserEmail(@PathVariable("tenantId") String aTenantId,
                                                                                          @PathVariable("username") String username,
                                                                                          @RequestBody UserEmailRepresentation userEmailRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserEmailAddress(new ChangeEmailAddressCommand(userEmailRepresentation));

        return new ResponseEntity<UserEmailRepresentation>(userEmailRepresentation, HttpStatus.CREATED);
    }

    @GetMapping("{username}/email")
    public ResponseEntity<UserEmailRepresentation> getUserEmail(@PathVariable("tenantId") String aTenantId,
                                                                    @PathVariable("username") String username) throws DiaspoGiftRepositoryException {

        //this.identityApplicationService().userEmailAddress(aTenantId, username));

        //return new ResponseEntity<UserEmailRepresentation>(userEmailRepresentation, HttpStatus.CREATED);

        return null;
    }


    @PostMapping("{username}/name")
    public ResponseEntity<UserPersonalNameRepresentation> changeUserPersonalName(@PathVariable("tenantId") String aTenantId,
                                                                   @PathVariable("username") String username,
                                                                   @RequestBody UserPersonalNameRepresentation userPersonalNameRepresentation) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().changeUserPersonalName(new ChangeUserPersonalNameCommand(aTenantId, username, userPersonalNameRepresentation));

        return new ResponseEntity<UserPersonalNameRepresentation>(userPersonalNameRepresentation, HttpStatus.CREATED);
    }

    @GetMapping("{username}/name")
    public ResponseEntity<UserPersonalNameRepresentation> getUserPersonalName(@PathVariable("tenantId") String aTenantId,
                                                                             @PathVariable("username") String username) throws DiaspoGiftRepositoryException {

        //this.identityApplicationService().userPersonalName(aTenantId, username);

        //return new ResponseEntity<UserPersonalNameRepresentation>(userPersonalNameRepresentation, HttpStatus.CREATED);

        return null;

    }

    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable("tenantId") String aTenantId,
                                        @PathVariable("username") String aUsername) throws DiaspoGiftRepositoryException {

        User user = this.identityApplicationService().user(aTenantId, aUsername);


        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }

    @GetMapping("{username}/in-role/{roleName}")
    public ResponseEntity<User> getUserInRole(@PathVariable("tenantId") String aTenantId,
                                              @PathVariable("username") String aUsername,
                                              @PathVariable("roleName") String aRoleName) throws DiaspoGiftRepositoryException {


        User user = this.accessApplicationService()
                .userInRole(
                        aTenantId,
                        aUsername,
                        aRoleName);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<UserDescriptorCollectionRepresentation> getUsers(@PathVariable("tenantId") String aTenantId) throws DiaspoGiftRepositoryException {

        UserDescriptorCollectionRepresentation userDescriptorCollectionRepresentation =
                new UserDescriptorCollectionRepresentation(this.identityApplicationService().allUserFor(aTenantId));

        return new ResponseEntity<UserDescriptorCollectionRepresentation>(userDescriptorCollectionRepresentation, HttpStatus.FOUND);
    }

    /**
     * Exception handling
     */

    @ExceptionHandler(DiaspoGiftRepositoryException.class)
    public ResponseEntity rulesForTenantNotFound(Exception e, HttpServletRequest req) {
        ClientErrorInformation errorInformation = new ClientErrorInformation(e.getClass().getName(), req.getRequestURI());
        return new ResponseEntity(errorInformation, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity rulesForIllegalArgument(Exception e, HttpServletRequest req) {
        ClientErrorInformation errorInformation = new ClientErrorInformation(e.getClass().getName(), req.getRequestURI());
        return new ResponseEntity(errorInformation, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity rulesForIllegalState(Exception e, HttpServletRequest req) {
        ClientErrorInformation errorInformation = new ClientErrorInformation(e.getClass().getName(), req.getRequestURI());
        return new ResponseEntity(errorInformation, HttpStatus.BAD_REQUEST);
    }

    public IdentityApplicationService identityApplicationService() {
        return this.identityApplicationService;
    }

    public AccessApplicationService accessApplicationService() {
        return this.accessApplicationService;
    }


}
