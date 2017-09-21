package com.diaspogift.identityandaccess.resources;

import com.diaspogift.identityandaccess.application.access.AccessApplicationService;
import com.diaspogift.identityandaccess.application.command.ProvisionGroupCommand;
import com.diaspogift.identityandaccess.application.identity.IdentityApplicationService;
import com.diaspogift.identityandaccess.application.representation.GroupCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.GroupRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.Group;
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
@RequestMapping(path = "/api/tenants/{tenantId}/groups")
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

            Link link = linkTo(methodOn(GroupResource.class).getGroup(next.getTenantId(), next.getName())).withSelfRel();

            next.add(link);

        }

        groupCollectionRepresentation.setGroups(allGroupReps);

        return new ResponseEntity<GroupCollectionRepresentation>(groupCollectionRepresentation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupRepresentation> createGroup(@PathVariable("tenantId") String tenantId,
                                                           @RequestBody GroupRepresentation groupRepresentation) throws DiaspoGiftRepositoryException {

        if (tenantId != null && !tenantId.equals(groupRepresentation.getTenantId())) {

            throw new IllegalArgumentException("Wrong tenant.");
        }

        this.identityApplicationService().provisionGroup(new ProvisionGroupCommand(groupRepresentation));


        return new ResponseEntity<GroupRepresentation>(groupRepresentation, HttpStatus.CREATED);
    }

    @GetMapping("{groupName}")
    public ResponseEntity<GroupRepresentation> getGroup(@PathVariable("tenantId") String tenantId,
                                                        @PathVariable("groupName") String groupName) throws DiaspoGiftRepositoryException {

        return new ResponseEntity<GroupRepresentation>(new GroupRepresentation(this.identityApplicationService().group(tenantId, groupName)), HttpStatus.FOUND);
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
