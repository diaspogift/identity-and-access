package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.domain.model.access.RoleProvisioned;
import com.diaspogift.identityandaccess.domain.model.access.UserAssignedToRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TenantProvisioningServiceTests extends IdentityAndAccessTest {

    //TO DO more asserts to come
    @Test
    @Rollback(false)
    public void provisionTenant() {

        FullName tenantAdminFullName = new FullName(FIXTURE_FIRST_NAME_1, FIXTURE_LAST_NAME_1);
        EmailAddress tenantAdminEmailAddress = new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS_1);
        PostalAddress tenantPostalAddress = new PostalAddress(FIXTURE_STREET_ADDRESS_1, FIXTURE_CITY_1, FIXTURE_STATE_1, FIXTURE_POSTALCODE_1, FIXTURE_COUNTRY_CODE_1);
        Telephone tenantPrimaryTelephone = new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1);
        Telephone tenantSecondaryTelephone = new Telephone(FIXTURE_COUNTRY_CODE_1, FIXTURE_COUNTRY_DAILING_CODE_1, FIXTURE_PHONE_NUMBER_1_1);

        Tenant provisionedTenant =
                DomainRegistry.tenantProvisioningService()
                        .provisionTenant(
                                FIXTURE_TENANT_NAME,
                                FIXTURE_TENANT_DESCRIPTION,
                                tenantAdminFullName,
                                tenantAdminEmailAddress,
                                tenantPostalAddress,
                                tenantPrimaryTelephone,
                                tenantSecondaryTelephone);

        assertNotNull(provisionedTenant);

        this.expectedEvents(11);
        this.expectedEvent(UserRegistered.class, 2);
        this.expectedEvent(RoleProvisioned.class, 2);
        this.expectedEvent(GroupUserAdded.class, 2);
        this.expectedEvent(UserAssignedToRole.class, 2);
        this.expectedEvent(TenantAdministratorRegistered.class, 2);
        this.expectedEvent(TenantProvisioned.class, 1);
    }


}
