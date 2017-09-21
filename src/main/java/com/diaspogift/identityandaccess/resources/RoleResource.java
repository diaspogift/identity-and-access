package com.diaspogift.identityandaccess.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.ProvisionRoleCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.RoleCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.RoleRepresentation;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.infrastructure.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/tenants/{tenantId}/roles")
public class RoleResource {

    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;


    @GetMapping
    public ResponseEntity<RoleCollectionRepresentation> getTenantRoles(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {


        Collection<Role> roles = this.accessApplicationService().allRoles(tenantId);

        RoleCollectionRepresentation roleCollectionRepresentation = new RoleCollectionRepresentation(roles);

        Collection<RoleRepresentation> allRolesReps = roleCollectionRepresentation.getRoles();

        for (RoleRepresentation next : allRolesReps) {

            Link link = linkTo(methodOn(RoleResource.class).getTenantRole(next.getTenantId(), next.getName())).withSelfRel();

            next.add(link);

        }

        return new ResponseEntity<RoleCollectionRepresentation>(roleCollectionRepresentation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleRepresentation> addTenantRoles(@PathVariable("tenantId") String tenantId,
                                                             @RequestBody RoleRepresentation roleRepresentation) throws DiaspoGiftRepositoryException {

        if (tenantId != null && !tenantId.equals(roleRepresentation.getTenantId()))
            throw new IllegalArgumentException("Wrong teanant");

        this.accessApplicationService().provisionRole(new ProvisionRoleCommand(roleRepresentation));

        return new ResponseEntity<RoleRepresentation>(roleRepresentation, HttpStatus.CREATED);
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<RoleRepresentation> getTenantRole(@PathVariable("tenantId") String tenantId,
                                                            @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {

        RoleRepresentation roleRepresentation =
                new RoleRepresentation(this.accessApplicationService().roleNamed(tenantId, roleName));

        return new ResponseEntity<RoleRepresentation>(roleRepresentation, HttpStatus.OK);
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity removeTenantRole(@PathVariable("tenantId") String tenantId,
                                           @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {
        this.accessApplicationService().removeRoleNamed(tenantId, roleName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
