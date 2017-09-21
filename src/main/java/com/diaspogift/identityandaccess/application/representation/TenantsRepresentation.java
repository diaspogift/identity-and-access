package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class TenantsRepresentation extends ResourceSupport {

    private static final Logger logger = LoggerFactory.getLogger(TenantsRepresentation.class);
    ;
    Collection<TenantRepresentation> tenants = new HashSet<TenantRepresentation>();

    public TenantsRepresentation() {
        super();
    }

    public TenantsRepresentation(Collection<Tenant> someTenants) {

        this.initializeFrom(someTenants);
    }

    private void initializeFrom(Collection<Tenant> someTenants) {

        for (Tenant next : someTenants) {

            this.tenants.add(new TenantRepresentation(next));
        }

    }

    public Collection<TenantRepresentation> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<TenantRepresentation> tenants) {
        this.tenants = tenants;
    }

    @Override
    public String toString() {
        return "TenantsRepresentation{" +
                "tenants=" + tenants +
                '}';
    }


}
