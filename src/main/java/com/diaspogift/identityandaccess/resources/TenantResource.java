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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/tenants")
@Api(value = "iam", description = "Operations pertaining to tenants in the iam System")
public class TenantResource {


    private static final Logger logger = LoggerFactory.getLogger(TenantResource.class);


    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;


    @ApiOperation(value = "Retrieve all tenants")
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

    @ApiOperation(value = "Provision a tenant")
    @PostMapping("/provisions")
    public ResponseEntity<ProvisionedTenantRepresentation> provisionTenant(@RequestBody @Valid ProvisionTenantRepresentation provisionTenantRepresentation) throws DiaspoGiftRepositoryException {

        HttpHeaders httpHeaders = new HttpHeaders();

        ProvisionedTenantRepresentation provisionedTenantRepresentation = null;

        provisionedTenantRepresentation = new ProvisionedTenantRepresentation(this.identityApplicationService().provisionTenant(new ProvisionTenantCommand(provisionTenantRepresentation)));

        Link link = linkTo(methodOn(TenantResource.class).getTenantProvision(provisionedTenantRepresentation.getTenantId())).withSelfRel();
        provisionedTenantRepresentation.add(link);

        User user = this.identityApplicationService().user(provisionedTenantRepresentation.getTenantId(), "admin");

        return new ResponseEntity<ProvisionedTenantRepresentation>(provisionedTenantRepresentation, HttpStatus.CREATED);

    }


    @ApiOperation(value = "Retrieve a provisioned tenant")
    @GetMapping("/{tenantId}/provisions")
    public ResponseEntity<ProvisionedTenantRepresentation> getTenantProvision(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {

        ProvisionedTenantRepresentation provisionedTenantRepresentation = new ProvisionedTenantRepresentation(this.identityApplicationService.tenant(tenantId));

        return new ResponseEntity<ProvisionedTenantRepresentation>(provisionedTenantRepresentation, HttpStatus.OK);

    }

    @ApiOperation(value = "Offer a registration invitation to a tenant")
    @PostMapping("/{tenantId}/registration-invitations")
    public ResponseEntity<RegistrationInvitationRepresentation> offerRegistrationInvitation(@PathVariable("tenantId") String tenantId,
                                                                                            @RequestBody RegistrationInvitationRepresentation registrationInvitationRepresentation) throws DiaspoGiftRepositoryException {

        RegistrationInvitationRepresentation offeredRegistrationInvitationRepresentation =
                new RegistrationInvitationRepresentation(
                        this.identityApplicationService().offerRegistrationInvitation(new OfferRegistrationInvitationCommand(tenantId, registrationInvitationRepresentation)));


        return new ResponseEntity<RegistrationInvitationRepresentation>(offeredRegistrationInvitationRepresentation, HttpStatus.CREATED);

    }

    @ApiOperation(value = "Retrieve a tenant with the spcified identitifier")
    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantRepresentation> getTenant(@PathVariable String tenantId) throws DiaspoGiftRepositoryException {

        TenantRepresentation tenantRepresentation = new TenantRepresentation(this.identityApplicationService().tenant(tenantId));

        return new ResponseEntity<TenantRepresentation>(tenantRepresentation, HttpStatus.OK);
    }

    @ApiOperation(value = "Change tenant's availability status")
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

    @ApiOperation(value = "Retrieve tenant's availability status")
    @GetMapping("/{tenantId}/availability-status")
    public ResponseEntity<TenantAvailabilityRepresentation> getTenantAvailability(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {

        TenantAvailabilityRepresentation tenantAvailabilityRepresentation = new TenantAvailabilityRepresentation(this.identityApplicationService().availabilityStatus(tenantId));

        return new ResponseEntity<TenantAvailabilityRepresentation>(tenantAvailabilityRepresentation, HttpStatus.OK);
    }


    private IdentityApplicationService identityApplicationService() {
        return this.identityApplicationService;
    }

    public AccessApplicationService accessApplicationService() {
        return this.accessApplicationService;
    }


}
