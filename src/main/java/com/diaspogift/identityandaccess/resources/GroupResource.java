package com.diaspogift.identityandaccess.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.GroupCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.GroupMemberCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.GroupMemberRepresentation;
import com.diaspogift.identityandaccess.application.representation.GroupRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.Group;
import com.diaspogift.identityandaccess.domain.model.identity.GroupMember;
import com.diaspogift.identityandaccess.domain.model.identity.GroupMemberType;
import com.diaspogift.identityandaccess.infrastructure.persistence.exception.DiaspoGiftRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(path = "/api/v1/tenants/{tenantId}/groups")
public class GroupResource {

    private static final Logger logger = LoggerFactory.getLogger(TenantResource.class);
    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;

    @GetMapping
    public ResponseEntity<GroupCollectionRepresentation> getGroups(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {

        Collection<Group> groups = this.identityApplicationService().allTenantGroups(tenantId);


        GroupCollectionRepresentation groupCollectionRepresentation = new GroupCollectionRepresentation(groups);


        Collection<GroupRepresentation> allGroupReps = groupCollectionRepresentation.getGroups();


        for (GroupRepresentation next : allGroupReps) {

            Link link1 = linkTo(methodOn(GroupResource.class).getGroup(tenantId, next.getName())).withSelfRel();
            Link link2 = linkTo(methodOn(GroupResource.class).getGroupMembers(tenantId, next.getName())).withRel("members");

            next.add(link1);
            next.add(link2);

        }

        groupCollectionRepresentation.setGroups(allGroupReps);

        return new ResponseEntity<GroupCollectionRepresentation>(groupCollectionRepresentation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupRepresentation> createGroup(@PathVariable("tenantId") String tenantId,
                                                           @RequestBody GroupRepresentation groupRepresentation) throws DiaspoGiftRepositoryException {


        this.identityApplicationService().provisionGroup(new ProvisionGroupCommand(tenantId, groupRepresentation));


        return new ResponseEntity<GroupRepresentation>(groupRepresentation, HttpStatus.CREATED);
    }

    @GetMapping("{groupName}")
    public ResponseEntity<GroupRepresentation> getGroup(@PathVariable("tenantId") String tenantId,
                                                        @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        GroupRepresentation groupRepresentation = new GroupRepresentation(this.identityApplicationService().group(tenantId, groupName));

        Link link = linkTo(methodOn(GroupResource.class).getGroupMembers(tenantId, groupName)).withRel("members");

        groupRepresentation.add(link);

        return new ResponseEntity<GroupRepresentation>(groupRepresentation, HttpStatus.FOUND);
    }

    @DeleteMapping("{groupName}")
    public ResponseEntity removeGroup(@PathVariable("tenantId") String tenantId,
                                      @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().removeGroup(tenantId, groupName);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping("{groupName}/members")
    public ResponseEntity<GroupMemberCollectionRepresentation> getGroupMembers(@PathVariable("tenantId") String tenantId,
                                                                               @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        Collection<GroupMember> groupMembers = this.identityApplicationService().groupMembers(tenantId, groupName);

        GroupMemberCollectionRepresentation groupMemberCollectionRepresentation = new GroupMemberCollectionRepresentation(groupMembers);

        for (GroupMemberRepresentation next : groupMemberCollectionRepresentation.getGroupMembes()) {

            Link link1 = linkTo(methodOn(GroupResource.class).getGroup(tenantId, next.getName())).withSelfRel();

            next.add(link1);

        }

        return new ResponseEntity<GroupMemberCollectionRepresentation>(groupMemberCollectionRepresentation, HttpStatus.FOUND);
    }


    @PostMapping("{groupName}/members")
    public ResponseEntity<GroupMemberRepresentation> createGroupMember(@PathVariable("tenantId") String tenantId,
                                                                       @PathVariable("groupName") String groupName,
                                                                       @RequestBody GroupMemberRepresentation groupMemberRepresentation) throws DiaspoGiftRepositoryException {


        if (groupMemberRepresentation.getType().equals(GroupMemberType.User.name())) {

            this.identityApplicationService().addUserToGroup(
                    new AddUserToGroupCommand(
                            tenantId,
                            groupName,
                            groupMemberRepresentation.getName()));


        } else if (groupMemberRepresentation.getType().equals(GroupMemberType.Group.name())) {

            this.identityApplicationService().addGroupToGroup(
                    new AddGroupToGroupCommand(
                            tenantId,
                            groupName,
                            groupMemberRepresentation.getName()));


        } else {
            //Do nothhing
        }

        return new ResponseEntity<GroupMemberRepresentation>(groupMemberRepresentation, HttpStatus.CREATED);
    }


    @DeleteMapping("{groupName}/members/{name}")
    public ResponseEntity removeGroupMember(@PathVariable("tenantId") String tenantId,
                                            @PathVariable("groupName") String groupName,
                                            @PathVariable("name") String name,
                                            @RequestParam("type") String type) throws DiaspoGiftRepositoryException {


        if (type.equals(GroupMemberType.User.name())) {

            this.identityApplicationService().removeUserFromGroup(new RemoveUserFromGroupCommand(tenantId, groupName, name));


        } else if (type.equals(GroupMemberType.Group.name())) {

            this.identityApplicationService().removeGroupFromGroup(new RemoveGroupFromGroupCommand(tenantId, groupName, name));


        } else {
            //Do nothhing
        }

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
