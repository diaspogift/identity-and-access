package com.diaspogift.identityandaccess.application.representation.user;

import com.diaspogift.identityandaccess.domain.model.identity.User;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class UserCollectionRepresentation extends ResourceSupport {

    private Collection<UserRepresentation> users = new HashSet<UserRepresentation>();

    public UserCollectionRepresentation() {
        super();
    }

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

    public List<String> usernamesList() {

        List<String> usernames = new ArrayList<>();

        for (UserRepresentation next : users) {

            usernames.add(next.getUsername());
        }

        return usernames;
    }


}
