package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthenticationServiceTests extends IdentityAndAccessTest {

    @Test
    public void authenticate() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        UserDescriptor userDescriptor =
                DomainRegistry.authenticationService()
                        .authenticate(
                                tenant.tenantId(),
                                user.userId().username(),
                                FIXTURE_PASSWORD);

        assertNotNull(userDescriptor);
        assertEquals(user.userDescriptor(), userDescriptor);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void wrongAuthenticateWithBadUsername() {

        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        UserDescriptor userDescriptor =
                DomainRegistry.authenticationService()
                        .authenticate(
                                tenant.tenantId(),
                                user.userId().username() + "BAD USERNAME",
                                FIXTURE_PASSWORD);
    }


    @Test(expected = EmptyResultDataAccessException.class)
    public void wrongAuthenticateWithBadPassword() {


        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        UserDescriptor userDescriptor =
                DomainRegistry.authenticationService()
                        .authenticate(
                                tenant.tenantId(),
                                user.userId().username(),
                                FIXTURE_PASSWORD + "BAD PASSWORD");
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void wrongAuthenticateWithBadTenant() {

        TenantId tenantId1 = new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
        Tenant tenant = this.actifTenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        UserDescriptor userDescriptor =
                DomainRegistry.authenticationService()
                        .authenticate(
                                tenantId1,
                                user.userId().username(),
                                FIXTURE_PASSWORD + "BAD PASSWORD");
    }

}
