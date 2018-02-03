package com.diaspogift.identityandaccess.port.adapter.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.group.GroupCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.group.GroupRepresentation;
import com.diaspogift.identityandaccess.application.representation.roles.RoleCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.roles.RoleRepresentation;
import com.diaspogift.identityandaccess.application.representation.user.UserCollectionRepresentation;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
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

            Link link1 = linkTo(methodOn(RoleResource.class).getTenantRole(tenantId, next.getName())).withSelfRel();
            Link link2 = linkTo(methodOn(RoleResource.class).getGroupsPlayingRole(tenantId, next.getName())).withRel("groups");
            Link link3 = linkTo(methodOn(RoleResource.class).getGroupsNotPlayingRole(tenantId, next.getName())).withRel("not-groups");
            Link link4 = linkTo(methodOn(RoleResource.class).getUsersInRole(tenantId, next.getName())).withRel("users");
            Link link5 = linkTo(methodOn(RoleResource.class).getUsersNotInRole(tenantId, next.getName())).withRel("not-users");

            next.add(link1);
            next.add(link2);
            next.add(link3);
            next.add(link4);
            next.add(link5);

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


    @ApiOperation(value = "Assign a group or a collection of groups to a role")
    @PostMapping(value = "/{roleName}/groups")
    public ResponseEntity<GroupCollectionRepresentation> assignGroupsToRole(@PathVariable("tenantId") String tenantId,
                                                                            @PathVariable("roleName") String roleName,
                                                                            @RequestBody GroupCollectionRepresentation groupCollectionRepresentation) throws DiaspoGiftRepositoryException {


        Collection<GroupRepresentation> allGroups = groupCollectionRepresentation.getGroups();


        this.accessApplicationService().assignGroupsToRole(new AssignGroupsToRoleCommand(tenantId, roleName, allGroups));


        return new ResponseEntity<GroupCollectionRepresentation>(groupCollectionRepresentation, HttpStatus.CREATED);
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


    @ApiOperation(value = "Unassign a group or a collection of groups from role")
    @DeleteMapping(value = "/{roleName}/groups")
    public ResponseEntity<GroupCollectionRepresentation> unassignGroupsFrom(@PathVariable("tenantId") String tenantId,
                                                                            @PathVariable("roleName") String roleName,
                                                                            @RequestBody GroupCollectionRepresentation groupCollectionRepresentation) throws DiaspoGiftRepositoryException {


        Collection<GroupRepresentation> allGroups = groupCollectionRepresentation.getGroups();


        this.accessApplicationService().unassignGroupsFromRole(new UnassignGroupsFromRoleCommand(tenantId, roleName, allGroups));


        return new ResponseEntity<GroupCollectionRepresentation>(groupCollectionRepresentation, HttpStatus.CREATED);
    }
    ///Read model


    //TODO implement the get for the resource above
    //TODO implement the get for the resource above
    //TODO implement the get for the resource above

    @ApiOperation(value = "Retrieve groups playing a given role")
    @GetMapping(value = "/{roleName}/groups")
    public ResponseEntity<GroupCollectionRepresentation> getGroupsPlayingRole(@PathVariable("tenantId") String tenantId,
                                                                              @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {

        GroupCollectionRepresentation allGroups = this.accessApplicationService().getGroupsPlayingRole(tenantId, roleName);


        return new ResponseEntity<GroupCollectionRepresentation>(allGroups, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Retrieve groups not in a given role")
    @GetMapping(value = "/{roleName}/not-groups")
    public ResponseEntity<GroupCollectionRepresentation> getGroupsNotPlayingRole(@PathVariable("tenantId") String tenantId,
                                                                                 @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {

        GroupCollectionRepresentation allGroups = this.accessApplicationService().getGroupsNotPlayingRole(tenantId, roleName);


        return new ResponseEntity<GroupCollectionRepresentation>(allGroups, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Retrieve users playing a given role")
    @GetMapping(value = "/{roleName}/users")
    public ResponseEntity<UserCollectionRepresentation> getUsersInRole(@PathVariable("tenantId") String tenantId,
                                                                       @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {

        UserCollectionRepresentation allUsers = this.accessApplicationService().getUsersInRole(tenantId, roleName);


        return new ResponseEntity<UserCollectionRepresentation>(allUsers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Assign users to a given role")
    @PostMapping(value = "/{roleName}/users")
    public ResponseEntity<UserCollectionRepresentation> assignUsersFromRole(@PathVariable("tenantId") String tenantId,
                                                                            @PathVariable("roleName") String roleName,
                                                                            @RequestBody UserCollectionRepresentation userCollectionRepresentation) throws DiaspoGiftRepositoryException {

        this.accessApplicationService().assignUsersToRole(new AssignUsersToRoleCommand(tenantId, roleName, userCollectionRepresentation.usernamesList()));


        return new ResponseEntity<UserCollectionRepresentation>(userCollectionRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Unassign users from a given role")
    @DeleteMapping(value = "/{roleName}/users")
    public ResponseEntity<UserCollectionRepresentation> UanssignUsersFromRole(@PathVariable("tenantId") String tenantId,
                                                                              @PathVariable("roleName") String roleName,
                                                                              @RequestBody UserCollectionRepresentation userCollectionRepresentation) throws DiaspoGiftRepositoryException {

        this.accessApplicationService().unassignUsersFromRole(new UnassignUsersFromRoleCommand(tenantId, roleName, userCollectionRepresentation.usernamesList()));


        return new ResponseEntity<UserCollectionRepresentation>(userCollectionRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieve users not playing a given role")
    @GetMapping(value = "/{roleName}/not-users")
    public ResponseEntity<UserCollectionRepresentation> getUsersNotInRole(@PathVariable("tenantId") String tenantId,
                                                                          @PathVariable("roleName") String roleName) throws DiaspoGiftRepositoryException {

        UserCollectionRepresentation allUsers = this.accessApplicationService().getNotUsersNotInRole(tenantId, roleName);


        return new ResponseEntity<UserCollectionRepresentation>(allUsers, HttpStatus.CREATED);
    }


    public IdentityApplicationService identityApplicationService() {
        return this.identityApplicationService;
    }

    public AccessApplicationService accessApplicationService() {
        return this.accessApplicationService;
    }
}
