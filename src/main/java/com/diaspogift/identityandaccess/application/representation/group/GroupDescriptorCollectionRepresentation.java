package com.diaspogift.identityandaccess.application.representation.group;

import com.diaspogift.identityandaccess.domain.model.identity.GroupDescriptor;

import java.util.Collection;
import java.util.HashSet;

public class GroupDescriptorCollectionRepresentation {


    private Collection<GroupDescriptor> groups = new HashSet<GroupDescriptor>();

    public GroupDescriptorCollectionRepresentation() {
        super();
    }

    public GroupDescriptorCollectionRepresentation(Collection<GroupDescriptor> groups) {

        this.initialyzeFrom(groups);
    }

    private void initialyzeFrom(Collection<GroupDescriptor> groups) {

        for (GroupDescriptor next : groups) {

            this.groups.add(next);
        }
    }

    public Collection<GroupDescriptor> getGroups() {
        return groups;
    }

    public void setGroups(Collection<GroupDescriptor> groups) {
        this.groups = groups;
    }
}
