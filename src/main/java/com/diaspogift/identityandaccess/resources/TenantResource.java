package com.diaspogift.identityandaccess.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.ActivateTenantCommand;
import com.diaspogift.identityandaccess.application.command.DeactivateTenantCommand;
import com.diaspogift.identityandaccess.application.command.OfferRegistrationInvitationCommand;
import com.diaspogift.identityandaccess.application.command.ProvisionTenantCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.*;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.infrastructure.persistence.exception.DiaspoGiftRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/tenants")
public class TenantResource {


    private static final Logger logger = LoggerFactory.getLogger(TenantResource.class);
    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;

    @GetMapping
    public ResponseEntity<TenantCollectionRepresentation> getTenants(@RequestParam(required = false) Integer first, @RequestParam(required = false) Integer rangeSize) throws DiaspoGiftRepositoryException {

        Collection<Tenant> tenants = new HashSet<Tenant>();


        if (first != null && rangeSize > 1)
            tenants = this.identityApplicationService().allTenants(first, rangeSize);
        else {
            tenants = this.identityApplicationService().allTenants();
        }


        TenantCollectionRepresentation tenantCollectionRepresentation = new TenantCollectionRepresentation(tenants);


        Collection<TenantRepresentation> allTenantsRep = tenantCollectionRepresentation.getTenants();

        for (TenantRepresentation next : allTenantsRep) {

            Link link1 = linkTo(methodOn(TenantResource.class).getTenant(next.getTenantId())).withSelfRel();
            Link link2 = linkTo(methodOn(GroupResource.class).getGroups(next.getTenantId())).withRel("groups");
            Link link3 = linkTo(methodOn(UserResource.class).getUsers(next.getTenantId())).withRel("users");

            next.add(link1);
            next.add(link2);
            next.add(link3);

        }

        tenantCollectionRepresentation.setTenants(allTenantsRep);


        if (first != null && rangeSize > 1) {

            tenantCollectionRepresentation.add(linkTo(methodOn(TenantResource.class).getTenants(first + rangeSize, rangeSize)).withRel("next"));
        }


        return new ResponseEntity<TenantCollectionRepresentation>(tenantCollectionRepresentation, HttpStatus.OK);
    }

    @PostMapping("/provisions")
    public ResponseEntity<TenantRepresentation> provisionTenant(@RequestBody ProvisionTenantRepresentation provisionTenantRepresentation) throws DiaspoGiftRepositoryException {

        HttpHeaders httpHeaders = new HttpHeaders();

        TenantRepresentation tenantRepresentation = null;

        tenantRepresentation =
                new TenantRepresentation(
                        this.identityApplicationService()
                                .provisionTenant(new ProvisionTenantCommand(provisionTenantRepresentation)));

        Link link = linkTo(methodOn(TenantResource.class).getTenantProvision(tenantRepresentation.getTenantId())).withSelfRel();
        tenantRepresentation.add(link);

        return new ResponseEntity<TenantRepresentation>(tenantRepresentation, HttpStatus.CREATED);

    }

    @PostMapping("/{tenantId}/registration-invitations")
    public ResponseEntity<RegistrationInvitationRepresentation> offerRegistrationInvitation(@PathVariable("tenantId") String tenantId,
                                                                                            @RequestBody RegistrationInvitationRepresentation registrationInvitationRepresentation) throws DiaspoGiftRepositoryException {


        RegistrationInvitationRepresentation registrationInvitationRepresentation1 =
                new RegistrationInvitationRepresentation(
                        this.identityApplicationService()
                                .offerRegistrationInvitation(
                                        new OfferRegistrationInvitationCommand(
                                                registrationInvitationRepresentation.getDescription(),
                                                registrationInvitationRepresentation.getStartingOn(),
                                                registrationInvitationRepresentation.getTenantId(),
                                                registrationInvitationRepresentation.getUntil()
                                        )));

        return new ResponseEntity<RegistrationInvitationRepresentation>(registrationInvitationRepresentation, HttpStatus.CREATED);

    }

    @PostMapping("/{tenantId}/provisions")
    public ResponseEntity<ProvisionTenantRepresentation> getTenantProvision(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {

        HttpHeaders httpHeaders = new HttpHeaders();

        return null;

    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantRepresentation> getTenant(@PathVariable String tenantId) throws DiaspoGiftRepositoryException {

        TenantRepresentation tenantRepresentation = new TenantRepresentation(this.identityApplicationService().tenant(tenantId));

        return new ResponseEntity<TenantRepresentation>(tenantRepresentation, HttpStatus.FOUND);
    }

    @PostMapping("/{tenantId}/status")
    public ResponseEntity<TenantRepresentation> changeTenantAvailability(@PathVariable("tenantId") String tenantId, @RequestBody TenantAvailabilityRepresentation tenantAvailabilityRepresentation) throws DiaspoGiftRepositoryException {


        if (tenantId != null && !tenantId.equals(tenantAvailabilityRepresentation.getTenantId())) {

            throw new IllegalArgumentException("Wrong tenant.");
        }

        if (tenantAvailabilityRepresentation.isActive()) {


            this.identityApplicationService().activateTenant(new ActivateTenantCommand(tenantId));
        } else {

            logger.info(" in else tenantId == " + tenantId + " tenantAvailabilityRepresentation =  " + tenantAvailabilityRepresentation.toString());


            this.identityApplicationService().deactivateTenant(new DeactivateTenantCommand(tenantId));
        }

        return new ResponseEntity<TenantRepresentation>(new TenantRepresentation(this.identityApplicationService().tenant(tenantId)), HttpStatus.OK);
    }


    @GetMapping("/{tenantId}/status")
    public ResponseEntity<TenantRepresentation> getTenantAvailability(@PathVariable("tenantId") String tenantId, @RequestBody TenantAvailabilityRepresentation tenantAvailabilityRepresentation) throws DiaspoGiftRepositoryException {

        //this.identityApplicationService().availabilityStatus();

        return null;

        //new ResponseEntity<TenantRepresentation>(new TenantRepresentation(this.identityApplicationService().tenant(tenantId)), HttpStatus.OK);
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

    private IdentityApplicationService identityApplicationService() {
        return this.identityApplicationService;
    }

    public AccessApplicationService accessApplicationService() {
        return this.accessApplicationService;
    }


}
