package com.diaspogift.identityandaccess.application.representation.roles;

import com.diaspogift.identityandaccess.domain.model.access.RoleDescriptor;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class RoleDescriptorCollectionRepresentation extends ResourceSupport {

    Collection<RoleDescriptor> roles = new HashSet<RoleDescriptor>();

    public RoleDescriptorCollectionRepresentation(Collection<RoleDescriptor> roles) {

        this.initializeFrom(roles);
    }


    private void initializeFrom(Collection<RoleDescriptor> someRoles) {

        for (RoleDescriptor next : someRoles) {

            this.roles.add(next);
        }

    }


    public Collection<RoleDescriptor> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleDescriptor> roles) {
        this.roles = roles;
    }
}
