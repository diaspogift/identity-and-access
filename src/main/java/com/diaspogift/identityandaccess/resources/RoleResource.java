package com.diaspogift.identityandaccess.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.ProvisionRoleCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.RoleCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.RoleRepresentation;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.infrastructure.persistence.exception.DiaspoGiftRepositoryException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/tenants/{tenantId}/roles")
@Api(value = "iam", description = "Operations pertaining to roles in the iam System")
public class RoleResource {

    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;

    @ApiOperation(value = "Retrieve all tenants roles")
    @GetMapping
    public ResponseEntity<RoleCollectionRepresentation> getTenantRoles(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {


        Collection<Role> roles = this.accessApplicationService().allRoles(tenantId);

        RoleCollectionRepresentation roleCollectionRepresentation = new RoleCollectionRepresentation(roles);

        Collection<RoleRepresentation> allRolesReps = roleCollectionRepresentation.getRoles();

        for (RoleRepresentation next : allRolesReps) {

            Link link = linkTo(methodOn(RoleResource.class).getTenantRole(tenantId, next.getName())).withSelfRel();

            next.add(link);

        }

        return new ResponseEntity<RoleCollectionRepresentation>(roleCollectionRepresentation, HttpStatus.OK);
    }

    @ApiOperation(value = "Create new tenants roles")
    @PostMapping
    public ResponseEntity<RoleRepresentation> addTenantRoles(@PathVariable("tenantId") String tenantId,
                                                             @RequestBody RoleRepresentation roleRepresentation) throws DiaspoGiftRepositoryException {

        this.accessApplicationService().provisionRole(new ProvisionRoleCommand(tenantId, roleRepresentation));

        return new ResponseEntity<RoleRepresentation>(roleRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieve a tenants role")
    @GetMapping("/{roleName}")
    public ResponseEntity<RoleRepresentation> getTenantRole(@PathVariable("tenantId") String tenantId,
                                                            @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {

        RoleRepresentation roleRepresentation =
                new RoleRepresentation(this.accessApplicationService().roleNamed(tenantId, roleName));

        return new ResponseEntity<RoleRepresentation>(roleRepresentation, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a tenants role")
    @DeleteMapping("/{roleName}")
    public ResponseEntity removeTenantRole(@PathVariable("tenantId") String tenantId,
                                           @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {
        this.accessApplicationService().removeRoleNamed(tenantId, roleName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    public IdentityApplicationService identityApplicationService() {
        return this.identityApplicationService;
    }

    public AccessApplicationService accessApplicationService() {
        return this.accessApplicationService;
    }
}
