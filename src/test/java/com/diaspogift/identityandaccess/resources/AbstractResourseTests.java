package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractResourseTests {


    private RestTemplate template;
    private HttpHeaders httpHeaders;
    private List<MediaType> acceptableMediaTypes;
    private Tenant bingoTenant;
    private Tenant cadeauxTenant;


    @Before
    public void setUp() throws Exception {

        this.template = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
        acceptableMediaTypes = new ArrayList<MediaType>();
        this.acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        this.httpHeaders.setAccept(acceptableMediaTypes);
        this.bingoTenant = null;
        this.cadeauxTenant = null;


    }

    protected Tenant bingoTenantAggregate() {

        if (this.bingoTenant == null) {

            TenantId tenantId =
                    DomainRegistry.tenantRepository().nextIdentity();

            this.bingoTenant =
                    new Tenant(
                            tenantId,
                            "Bingo Hospital",
                            "Hopital Bingo de Deido",
                            true);

            DomainRegistry.tenantRepository().add(this.bingoTenant);


        }

        return this.bingoTenant;
    }

    protected Tenant cadeauxTenantAggregate() {

        if (this.cadeauxTenant == null) {

            TenantId tenantId =
                    DomainRegistry.tenantRepository().nextIdentity();

            this.cadeauxTenant =
                    new Tenant(
                            tenantId,
                            "Super marche Cadeaux",
                            "Super marche Cadeaux de Bonamoussadi",
                            true);

            DomainRegistry.tenantRepository().add(this.cadeauxTenant);
        }

        return this.cadeauxTenant;
    }


    @After
    public void tearDown() throws Exception {

        this.template = null;
        this.httpHeaders = null;
        this.acceptableMediaTypes = null;
        this.httpHeaders = null;
        this.bingoTenant = null;
        this.cadeauxTenant = null;
    }


    protected RestTemplate template() {
        return this.template;
    }

    protected HttpHeaders httpHeaders() {
        return this.httpHeaders;
    }

    protected List<MediaType> acceptableMediaTypes() {
        return this.acceptableMediaTypes;
    }
}
