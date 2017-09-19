package com.diaspogift.identityandaccess.domain.model.identity;import com.diaspogift.identityandaccess.domain.model.DomainRegistry;import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;import org.junit.Test;import org.junit.runner.RunWith;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.dao.EmptyResultDataAccessException;import org.springframework.test.context.junit4.SpringRunner;import org.springframework.transaction.annotation.Transactional;import java.util.Collection;import static org.junit.Assert.*;@RunWith(SpringRunner.class)@SpringBootTest@Transactionalpublic class UserRepositoryTests extends IdentityAndAccessTest {    @Test    public void add() throws Exception {        User user = this.userAggregate();        DomainRegistry.userRepository().add(user);        assertNotNull(DomainRegistry                .userRepository()                .userWithUsername(user.tenantId(), user.username()));    }    @Test    public void userWithUsername() throws Exception {        User user = this.userAggregate();        DomainRegistry.userRepository().add(user);        assertNotNull(DomainRegistry                .userRepository()                .userWithUsername(user.tenantId(), user.username()));    }    @Test    public void remove() throws Exception {        User user = this.userAggregate();        DomainRegistry.userRepository().add(user);        assertNotNull(DomainRegistry                .userRepository()                .userWithUsername(user.tenantId(), user.username()));        DomainRegistry.userRepository().remove(user);        User removedUser = null;        boolean emptyResultDataAccessExceptionTrigger = false;        try {            removedUser =                    DomainRegistry                            .userRepository()                            .userWithUsername(user.tenantId(), user.username());            fail("Should not have got here.");        } catch (EmptyResultDataAccessException e) {            emptyResultDataAccessExceptionTrigger = true;        }        assertNull(removedUser);        assertTrue(emptyResultDataAccessExceptionTrigger);    }    @Test    public void allSimilarlyNamedUsers() throws Exception {        User user = this.userAggregate();        DomainRegistry.userRepository().add(user);        User user2 = this.userAggregate2();        DomainRegistry.userRepository().add(user2);        FullName name = user.person().name();        Collection<User> users =                DomainRegistry                        .userRepository()                        .allSimilarlyNamedUsers(                                user.tenantId(),                                "",                                name.lastName().substring(0, 2));        assertEquals(users.size(), 2);    }}