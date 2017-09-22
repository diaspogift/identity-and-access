package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.GroupMember;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class GroupMemberCollectionRepresentation extends ResourceSupport {

    Collection<GroupMemberRepresentation> groupMembes = new HashSet<GroupMemberRepresentation>();

    public GroupMemberCollectionRepresentation() {
        super();
    }

    public GroupMemberCollectionRepresentation(Collection<GroupMember> groupMembers) {

        this.initialyzeFrom(groupMembers);
    }

    private void initialyzeFrom(Collection<GroupMember> groupMembers) {

        for (GroupMember next : groupMembers) {

            this.groupMembes.add(new GroupMemberRepresentation((next)));
        }
    }

    public Collection<GroupMemberRepresentation> getGroupMembes() {
        return groupMembes;
    }

    public void setGroupMembes(Collection<GroupMemberRepresentation> groupMembes) {
        this.groupMembes = groupMembes;
    }
}
