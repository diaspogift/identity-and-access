package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.access.Role;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class RoleCollectionRepresentation extends ResourceSupport {

    Collection<RoleRepresentation> roles = new HashSet<RoleRepresentation>();

    public RoleCollectionRepresentation(Collection<Role> roles) {

        this.initializeFrom(roles);
    }

    private void initializeFrom(Collection<Role> someRoles) {

        for (Role next : someRoles) {

            this.roles.add(new RoleRepresentation(next));
        }

    }


    public Collection<RoleRepresentation> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleRepresentation> roles) {
        this.roles = roles;
    }
}
