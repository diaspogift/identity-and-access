package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.infrastructure.exception.DiaspogiftRipositoryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTest extends IdentityAndAccessTest {


    public UserTest() {
        super();
    }

    @Test
    public void createUser() throws DiaspogiftRipositoryException {
            Tenant tenant = this.actifTenantAggregate();
            User user = this.userAggregate();
            DomainRegistry.userRepository().add(user);
            assertNotNull(user);
            assertEquals(FIXTURE_USERNAME_1, user.username());
            assertTrue(user.isEnabled());
            assertEquals(new Enablement(true, null, null), user.enablement());
            assertEquals(DomainRegistry.encryptionService().encryptedValue(FIXTURE_PASSWORD), user.password());
            User foundUser = DomainRegistry.userRepository().userWithUsername(tenant.tenantId(), user.username());
            assertEquals(user, foundUser);

    }

    @Test(expected = DiaspogiftRipositoryException.class)
    public void createUserDuplicate() throws DiaspogiftRipositoryException {

        //boolean dataIntegrityViolationExceptionRaised = false;
        try {
            Tenant tenant = this.actifTenantAggregate();
            User user = this.userAggregate();
            User user1 = this.userAggregate();
            DomainRegistry.userRepository().add(user);
            //User getUser = DomainRegistry.userRepository().userWithUsername(user.tenantId(), user.username());
            DomainRegistry.userRepository().add(user1);
            assertNotNull(user);
            assertEquals(FIXTURE_USERNAME_1, user.username());
            assertTrue(user.isEnabled());
            assertEquals(new Enablement(true, null, null), user.enablement());
            assertEquals(DomainRegistry.encryptionService().encryptedValue(FIXTURE_PASSWORD), user.password());
            User foundUser = DomainRegistry.userRepository().userWithUsername(tenant.tenantId(), user.username());
            assertEquals(user, foundUser);
        }catch (DataIntegrityViolationException e){
            throw new DiaspogiftRipositoryException("Duplication de cle", e, DataIntegrityViolationException.class.getSimpleName());
        }
        //assertTrue(dataIntegrityViolationExceptionRaised);
    }


    @Test
    public void changePassword() {

        User user = this.userAggregate();
        assertEquals(DomainRegistry.encryptionService().encryptedValue(FIXTURE_PASSWORD), user.password());
        user.changePassword(FIXTURE_PASSWORD, "Ange__1308");
        assertEquals(DomainRegistry.encryptionService().encryptedValue("Ange__1308"), user.password());
    }

    @Test
    public void changePersonalContactInformation() {

        User user = this.userAggregate();
        ContactInformation newContactInformation = new ContactInformation(
                new EmailAddress("email.new@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CMR", "00237", "691178154"),
                new Telephone("USA", "001", "123-456-7899")
        );
        assertNotEquals(newContactInformation, user.person().contactInformation());
        user.changePersonalContactInformation(newContactInformation);
        assertEquals(newContactInformation, user.person().contactInformation());
    }


    @Test
    public void changePersonalName() {

        User user = this.userAggregate();
        FullName newFullname = new FullName("Mboh Tom", "Hilaire");
        assertNotEquals(newFullname, user.person().name());
        user.changePersonalName(newFullname);
        assertEquals(newFullname, user.person().name());
    }

    @Test
    public void defineEnablement() {

        User user = this.userAggregate();
        Enablement enablement = new Enablement(true, ZonedDateTime.now().minusDays(5l), ZonedDateTime.now().plusDays(5l));
        assertNotEquals(enablement, user.enablement());
        user.defineEnablement(enablement);
        assertEquals(enablement, user.enablement());
    }


    @Test
    public void changePasswordEvent() {

        User user = this.userAggregate();
        assertEquals(DomainRegistry.encryptionService().encryptedValue(FIXTURE_PASSWORD), user.password());
        user.changePassword(FIXTURE_PASSWORD, "Ange__1308");
        assertEquals(DomainRegistry.encryptionService().encryptedValue("Ange__1308"), user.password());
        this.expectedEvents(2);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(UserPasswordChanged.class, 1);
    }

    @Test
    public void defineEnablementEvent() {
        User user = this.userAggregate();
        Enablement enablement = new Enablement(true, ZonedDateTime.now().minusDays(5l), ZonedDateTime.now().plusDays(5l));
        assertNotEquals(enablement, user.enablement());
        user.defineEnablement(enablement);
        assertEquals(enablement, user.enablement());
        this.expectedEvents(2);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(UserEnablementChanged.class, 1);
    }

    @Test
    public void changePersonalContactInformationEvent() {

        User user = this.userAggregate();
        ContactInformation newContactInformation = new ContactInformation(
                new EmailAddress("email.new@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province", "Postal code", "US"),
                new Telephone("CMR", "00237", "691178154"),
                new Telephone("USA", "001", "123-456-7899")
        );
        assertNotEquals(newContactInformation, user.person().contactInformation());
        user.changePersonalContactInformation(newContactInformation);
        assertEquals(newContactInformation, user.person().contactInformation());
        this.expectedEvents(2);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(PersonContactInformationChanged.class, 1);
    }

    @Test
    public void changePersonalNameEvent() {

        User user = this.userAggregate();
        FullName newFullname = new FullName("Mboh Tom", "Hilaire");
        assertNotEquals(newFullname, user.person().name());
        user.changePersonalName(newFullname);
        assertEquals(newFullname, user.person().name());
        this.expectedEvents(2);
        this.expectedEvent(UserRegistered.class, 1);
        this.expectedEvent(PersonNameChanged.class, 1);
    }

}
