package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.AuthenticateUserCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.UserDescriptorCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.UserDescriptorRepresentation;
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
@RequestMapping(path = "/api/tenants/{tenantId}/users")
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

        return new ResponseEntity<User>(user, HttpStatus.FOUND);
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
