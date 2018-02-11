package com.diaspogift.identityandaccess.port.adapter.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.AddGroupToGroupCommand;
import com.diaspogift.identityandaccess.application.command.AddUserToGroupCommand;
import com.diaspogift.identityandaccess.application.command.ProvisionGroupCommand;
import com.diaspogift.identityandaccess.application.command.RemoveGroupMembersFromGroupCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.group.GroupCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.group.GroupMemberCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.group.GroupMemberRepresentation;
import com.diaspogift.identityandaccess.application.representation.group.GroupRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.Group;
import com.diaspogift.identityandaccess.domain.model.identity.GroupMember;
import com.diaspogift.identityandaccess.domain.model.identity.GroupMemberType;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/tenants/{tenantId}/groups")
@Api(value = "iam", description = "Operations pertaining to groups in the iam System")
public class GroupResource {

    private static final Logger logger = LoggerFactory.getLogger(TenantResource.class);
    @Autowired
    private IdentityApplicationService identityApplicationService;
    @Autowired
    private AccessApplicationService accessApplicationService;

    @ApiOperation(value = "Retrieve all groups")
    @GetMapping
    public ResponseEntity<GroupCollectionRepresentation> getGroups(@PathVariable("tenantId") String tenantId) throws DiaspoGiftRepositoryException {

        Collection<Group> groups = this.identityApplicationService().allTenantGroups(tenantId);


        GroupCollectionRepresentation groupCollectionRepresentation = new GroupCollectionRepresentation(groups);


        Collection<GroupRepresentation> allGroupReps = groupCollectionRepresentation.getGroups();


        for (GroupRepresentation next : allGroupReps) {

            Link link1 = linkTo(methodOn(GroupResource.class).getGroup(tenantId, next.getName())).withSelfRel();
            Link link2 = linkTo(methodOn(GroupResource.class).getGroupMembers(tenantId, next.getName())).withRel("members");
            Link link3 = linkTo(methodOn(GroupResource.class).getNotGroupMembers(tenantId, next.getName())).withRel("notMembers");

            next.add(link1);
            next.add(link2);
            next.add(link3);

        }

        groupCollectionRepresentation.setGroups(allGroupReps);

        return new ResponseEntity<GroupCollectionRepresentation>(groupCollectionRepresentation, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new group")
    @PostMapping
    public ResponseEntity<GroupRepresentation> createGroup(@PathVariable("tenantId") String tenantId,
                                                           @RequestBody GroupRepresentation groupRepresentation) throws DiaspoGiftRepositoryException {


        this.identityApplicationService().provisionGroup(new ProvisionGroupCommand(tenantId, groupRepresentation));


        return new ResponseEntity<GroupRepresentation>(groupRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieve a group")
    @GetMapping("{groupName}")
    public ResponseEntity<GroupRepresentation> getGroup(@PathVariable("tenantId") String tenantId,
                                                        @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        GroupRepresentation groupRepresentation = new GroupRepresentation(this.identityApplicationService().group(tenantId, groupName));

        Link link = linkTo(methodOn(GroupResource.class).getGroupMembers(tenantId, groupName)).withRel("members");

        groupRepresentation.add(link);

        return new ResponseEntity<GroupRepresentation>(groupRepresentation, HttpStatus.OK);
    }


    @ApiOperation(value = "Delete a group")
    @DeleteMapping("{groupName}")
    public ResponseEntity removeGroup(@PathVariable("tenantId") String tenantId,
                                      @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        this.identityApplicationService().removeGroup(tenantId, groupName);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Retrieve groups members")
    @GetMapping("{groupName}/members")
    public ResponseEntity<GroupMemberCollectionRepresentation> getGroupMembers(@PathVariable("tenantId") String tenantId,
                                                                               @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        Collection<GroupMember> groupMembers = this.identityApplicationService().groupMembers(tenantId, groupName);

        GroupMemberCollectionRepresentation groupMemberCollectionRepresentation = new GroupMemberCollectionRepresentation(groupMembers);

        for (GroupMemberRepresentation next : groupMemberCollectionRepresentation.getGroupMembers()) {

            if (next.getType().endsWith(GroupMemberType.Group.name())) {
                Link link = linkTo(methodOn(GroupResource.class).getGroup(tenantId, next.getName())).withSelfRel();
                next.add(link);
            } else if (next.getType().endsWith(GroupMemberType.User.name())) {

                Link link = linkTo(methodOn(UserResource.class).getUser(tenantId, next.getName())).withSelfRel();
                next.add(link);
            }


        }

        return new ResponseEntity<GroupMemberCollectionRepresentation>(groupMemberCollectionRepresentation, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve not groups members")
    @GetMapping("{groupName}/not-members")
    public ResponseEntity<GroupMemberCollectionRepresentation> getNotGroupMembers(@PathVariable("tenantId") String tenantId,
                                                                                  @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        Collection<GroupMember> groupMembers = this.identityApplicationService().notGroupMembers(tenantId, groupName);

        GroupMemberCollectionRepresentation groupMemberCollectionRepresentation = new GroupMemberCollectionRepresentation(groupMembers);


        return new ResponseEntity<GroupMemberCollectionRepresentation>(groupMemberCollectionRepresentation, HttpStatus.OK);
    }

    @ApiOperation(value = "Add one or multiple group member(s)")
    @PostMapping("{groupName}/members")
    public ResponseEntity<GroupMemberCollectionRepresentation> createGroupMember(@PathVariable("tenantId") String tenantId,
                                                                                 @PathVariable("groupName") String groupName,
                                                                                 @RequestBody GroupMemberCollectionRepresentation groupMembersCollectionRepresentation) throws DiaspoGiftRepositoryException {

        System.out.println("\n\n GROUPMEMBERS START");
        groupMembersCollectionRepresentation.getGroupMembers().stream().forEach(s -> System.out.println("GROUP MEMBER == " + s));
        System.out.println("\n\n GROUPMEMBERS END");
        Collection<GroupMemberRepresentation> allGroupMembers = groupMembersCollectionRepresentation.getGroupMembers();

        for (GroupMemberRepresentation next : allGroupMembers) {

            if (next.getType().equals(GroupMemberType.User.name())) {

                this.identityApplicationService().addUserToGroup(
                        new AddUserToGroupCommand(
                                tenantId,
                                groupName,
                                next.getName()));


            } else if (next.getType().equals(GroupMemberType.Group.name())) {

                this.identityApplicationService().addGroupToGroup(
                        new AddGroupToGroupCommand(
                                tenantId,
                                groupName,
                                next.getName()));


            } else {
                //Do nothing
            }
        }


        return new ResponseEntity<GroupMemberCollectionRepresentation>(groupMembersCollectionRepresentation, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete one or several group member(s)")
    @DeleteMapping("{groupName}/members")
    public ResponseEntity<GroupMemberCollectionRepresentation> removeGroupMember(@PathVariable("tenantId") String tenantId,
                                                                                 @PathVariable("groupName") String groupName,
                                                                                 @RequestBody GroupMemberCollectionRepresentation groupMemberCollectionRepresentation) throws DiaspoGiftRepositoryException {


        this.identityApplicationService().removeGroupMembersFromGroup(new RemoveGroupMembersFromGroupCommand(tenantId, groupName, groupMemberCollectionRepresentation.getGroupMembers()));


        return new ResponseEntity<GroupMemberCollectionRepresentation>(groupMemberCollectionRepresentation, HttpStatus.CREATED);
    }


    public IdentityApplicationService identityApplicationService() {
        return this.identityApplicationService;
    }

    public AccessApplicationService accessApplicationService() {
        return this.accessApplicationService;
    }


}
