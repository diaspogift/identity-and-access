package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
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
    public void authenticate() throws DiaspoGiftRepositoryException {

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

    @Test(expected = BadCredentialsException.class)
    public void wrongAuthenticateWithBadUsername() throws DiaspoGiftRepositoryException {

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


    @Test(expected = BadCredentialsException.class)
    public void wrongAuthenticateWithBadPassword() throws DiaspoGiftRepositoryException {


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

    @Test(expected = BadCredentialsException.class)
    public void wrongAuthenticateWithBadTenant() throws DiaspoGiftRepositoryException {

        TenantId wrongTenantId = new TenantId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
        User user = this.userAggregate();

        DomainRegistry.userRepository().add(user);

        UserDescriptor userDescriptor =
                DomainRegistry.authenticationService()
                        .authenticate(
                                wrongTenantId,
                                user.userId().username(),
                                FIXTURE_PASSWORD);

        //assertEquals(null, userDescriptor.username());
        //assertEquals(null, userDescriptor.tenantId());
        //assertEquals(null, userDescriptor.emailAddress());
        //assertTrue(userDescriptor.isNullDescriptor());
    }

}
