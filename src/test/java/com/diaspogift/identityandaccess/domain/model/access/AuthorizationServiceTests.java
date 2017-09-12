package com.diaspogift.identityandaccess.domain.model.access;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthorizationServiceTests extends IdentityAndAccessTest {

    public AuthorizationServiceTests() {
        super();
    }



    @Test
    public void isUserInRole() throws Exception {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Role medecinChefRole = tenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        Role infirmierChefRole = tenant.provisionRole(FIXTURE_ROLE_NAME_1, FIXTURE_ROLE_DESCRIPTION_1, true);
        medecinChefRole.assignUser(user);
        DomainRegistry.roleRepository().add(medecinChefRole);
        DomainRegistry.roleRepository().add(infirmierChefRole);
        boolean authorized = DomainRegistry.authorizationService().isUserInRole(user, FIXTURE_ROLE_NAME);
        assertTrue(authorized);
        authorized = DomainRegistry.authorizationService().isUserInRole(user, FIXTURE_ROLE_NAME_1);
        assertFalse(authorized);
    }

    @Test
    public void isUsernameInRole() throws Exception {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Role medecinChefRole = tenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION, true);
        Role infirmierChefRole = tenant.provisionRole(FIXTURE_ROLE_NAME_1, FIXTURE_ROLE_DESCRIPTION_1, true);
        medecinChefRole.assignUser(user);

        DomainRegistry.roleRepository().add(medecinChefRole);
        DomainRegistry.roleRepository().add(infirmierChefRole);
        boolean authorized = DomainRegistry.authorizationService().isUserInRole(tenant.tenantId(), user.username(), FIXTURE_ROLE_NAME);

        assertTrue(authorized);
        authorized = DomainRegistry.authorizationService().isUserInRole(tenant.tenantId(), user.username(), FIXTURE_ROLE_NAME_1);
        assertFalse(authorized);
    }




}
