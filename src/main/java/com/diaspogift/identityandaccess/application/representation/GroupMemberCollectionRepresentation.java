package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.GroupMember;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class GroupMemberCollectionRepresentation extends ResourceSupport {

    Collection<GroupMemberRepresentation> groupMembers = new HashSet<GroupMemberRepresentation>();

    public GroupMemberCollectionRepresentation() {
        super();
    }

    public GroupMemberCollectionRepresentation(Collection<GroupMember> groupMembers) {

        this.initialyzeFrom(groupMembers);
    }

    private void initialyzeFrom(Collection<GroupMember> groupMembers) {

        for (GroupMember next : groupMembers) {

            this.groupMembers.add(new GroupMemberRepresentation((next)));
        }
    }

    public Collection<GroupMemberRepresentation> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Collection<GroupMemberRepresentation> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
