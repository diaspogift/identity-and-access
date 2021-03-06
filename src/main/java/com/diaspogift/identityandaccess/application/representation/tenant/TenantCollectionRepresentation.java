package com.diaspogift.identityandaccess.application.representation.tenant;

import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.HashSet;

public class TenantCollectionRepresentation extends ResourceSupport {

    private static final Logger logger = LoggerFactory.getLogger(TenantCollectionRepresentation.class);
    ;
    Collection<TenantRepresentation> tenants = new HashSet<TenantRepresentation>();

    public TenantCollectionRepresentation() {
        super();
    }

    public TenantCollectionRepresentation(Collection<Tenant> someTenants) {

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
        return "TenantCollectionRepresentation{" +
                "tenants=" + tenants +
                '}';
    }


}
