package com.diaspogift.identityandaccess.application.representation.user;

import com.diaspogift.identityandaccess.domain.model.identity.User;

import java.util.Collection;
import java.util.HashSet;

public class UserCollectionRepresentation {

    private Collection<UserRepresentation> users = new HashSet<UserRepresentation>();

    public UserCollectionRepresentation(Collection<User> someUsers) {

        this.initializeFrom(someUsers);
    }

    private void initializeFrom(Collection<User> someUsers) {

        for (User next : someUsers) {

            this.users.add(new UserRepresentation(next));
        }
    }


    public Collection<UserRepresentation> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserRepresentation> users) {
        this.users = users;
    }


}
