package com.diaspogift.identityandaccess.application.representation.user;

import com.diaspogift.identityandaccess.domain.model.identity.UserDescriptor;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class UserDescriptorCollectionRepresentation extends ResourceSupport {

    Collection<UserDescriptorRepresentation> users = new HashSet<UserDescriptorRepresentation>();

    public UserDescriptorCollectionRepresentation() {
        super();
    }

    public UserDescriptorCollectionRepresentation(Collection<UserDescriptor> someUserDescriptors) {

        this.initializeFrom(someUserDescriptors);
    }

    private void initializeFrom(Collection<UserDescriptor> someUserDescriptors) {

        for (UserDescriptor next : someUserDescriptors) {

            this.users.add(new UserDescriptorRepresentation(next));
        }
    }


    public Collection<UserDescriptorRepresentation> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserDescriptorRepresentation> users) {
        this.users = users;
    }
}
