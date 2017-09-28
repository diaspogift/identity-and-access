package com.diaspogift.identityandaccess.resources;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractResourseTests {


    private Tenant bingoTenant;
    private Tenant cadeauxTenant;


    @Before
    public void setUp() throws Exception {

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

        this.bingoTenant = null;
        this.cadeauxTenant = null;
    }

}
