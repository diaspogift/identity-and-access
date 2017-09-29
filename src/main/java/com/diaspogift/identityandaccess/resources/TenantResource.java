package com.diaspogift.identityandaccess.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.ActivateTenantCommand;
import com.diaspogift.identityandaccess.application.command.DeactivateTenantCommand;
import com.diaspogift.identityandaccess.application.command.OfferRegistrationInvitationCommand;
import com.diaspogift.identityandaccess.application.command.ProvisionTenantCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.*;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.domain.model.identity.User;
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
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/tenants")
public class TenantResource {


    private static final Logger logger = LoggerFactory.getLogger(TenantResource.class);


    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;

    @GetMapping
    public ResponseEntity<TenantCollectionRepresentation> getTenants(@RequestParam(required = false) Integer first,
                                                                     @RequestParam(required = false) Integer rangeSize) throws DiaspoGiftRepositoryException {

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
    public ResponseEntity<ProvisionedTenantRepresentation> provisionTenant(@RequestBody @Valid ProvisionTenantRepresentation provisionTenantRepresentation) throws DiaspoGiftRepositoryException {

        HttpHeaders httpHeaders = new HttpHeaders();

        ProvisionedTenantRepresentation provisionedTenantRepresentation = null;

        provisionedTenantRepresentation = new ProvisionedTenantRepresentation(this.identityApplicationService().provisionTenant(new ProvisionTenantCommand(provisionTenantRepresentation)));

        Link link = linkTo(methodOn(TenantResource.class).getTenantProvision(provisionedTenantRepresentation.getTenantId())).withSelfRel();
        provisionedTenantRepresentation.add(link);

        User user = this.identityApplicationService().user(provisionedTenantRepresentation.getTenantId(), "admin");


        System.out.println(" \n\n tenant admin user ====== " + user);
        System.out.println(" \n\n tenant admin user ====== " + user);
        System.out.println(" \n\n provisionedTenantRepresentationr ====== " + provisionedTenantRepresentation);

        return new ResponseEntity<ProvisionedTenantRepresentation>(provisionedTenantRepresentation, HttpStatus.CREATED);

    }


    @GetMapping("/{tenantId}/provisions")
    public ResponseEntity<ProvisionedTenantRepresentation> getTenantProvision(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {

        ProvisionedTenantRepresentation provisionedTenantRepresentation = new ProvisionedTenantRepresentation(this.identityApplicationService.tenant(tenantId));

        return new ResponseEntity<ProvisionedTenantRepresentation>(provisionedTenantRepresentation, HttpStatus.OK);

    }

    @PostMapping("/{tenantId}/registration-invitations")
    public ResponseEntity<RegistrationInvitationRepresentation> offerRegistrationInvitation(@PathVariable("tenantId") String tenantId,
                                                                                            @RequestBody RegistrationInvitationRepresentation registrationInvitationRepresentation) throws DiaspoGiftRepositoryException {

        RegistrationInvitationRepresentation offeredRegistrationInvitationRepresentation =
                new RegistrationInvitationRepresentation(
                        this.identityApplicationService().offerRegistrationInvitation(new OfferRegistrationInvitationCommand(tenantId, registrationInvitationRepresentation)));


        return new ResponseEntity<RegistrationInvitationRepresentation>(offeredRegistrationInvitationRepresentation, HttpStatus.CREATED);

    }


    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantRepresentation> getTenant(@PathVariable String tenantId) throws DiaspoGiftRepositoryException {

        TenantRepresentation tenantRepresentation = new TenantRepresentation(this.identityApplicationService().tenant(tenantId));

        return new ResponseEntity<TenantRepresentation>(tenantRepresentation, HttpStatus.OK);
    }

    @PostMapping("/{tenantId}/availability-status")
    public ResponseEntity<TenantAvailabilityRepresentation> changeTenantAvailability(@PathVariable("tenantId") String tenantId,
                                                                                     @RequestBody TenantAvailabilityRepresentation tenantAvailabilityRepresentation) throws DiaspoGiftRepositoryException {

        if (tenantAvailabilityRepresentation.isActive()) {
            this.identityApplicationService().activateTenant(new ActivateTenantCommand(tenantId));

        } else {
            this.identityApplicationService().deactivateTenant(new DeactivateTenantCommand(tenantId));
        }

        return new ResponseEntity<TenantAvailabilityRepresentation>(tenantAvailabilityRepresentation, HttpStatus.CREATED);
    }


    @GetMapping("/{tenantId}/availability-status")
    public ResponseEntity<TenantAvailabilityRepresentation> getTenantAvailability(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {

        TenantAvailabilityRepresentation tenantAvailabilityRepresentation = new TenantAvailabilityRepresentation(this.identityApplicationService().availabilityStatus(tenantId));

        return new ResponseEntity<TenantAvailabilityRepresentation>(tenantAvailabilityRepresentation, HttpStatus.OK);
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
