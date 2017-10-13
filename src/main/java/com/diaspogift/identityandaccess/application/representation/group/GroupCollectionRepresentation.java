package com.diaspogift.identityandaccess.application.representation.group;

import com.diaspogift.identityandaccess.domain.model.identity.Group;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class GroupCollectionRepresentation extends ResourceSupport {


    private Collection<GroupRepresentation> groups = new HashSet<GroupRepresentation>();

    public GroupCollectionRepresentation() {
        super();
    }

    public GroupCollectionRepresentation(Collection<Group> groups) {

        this.initialyzeFrom(groups);
    }

    private void initialyzeFrom(Collection<Group> groups) {

        for (Group next : groups) {

            this.groups.add(new GroupRepresentation(next));
        }
    }

    public Collection<GroupRepresentation> getGroups() {
        return groups;
    }

    public void setGroups(Collection<GroupRepresentation> groups) {
        this.groups = groups;
    }


    @Override
    public String toString() {
        return "GroupCollectionRepresentation{" +
                "groups=" + groups +
                '}';
    }
}
