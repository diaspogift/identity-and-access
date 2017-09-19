package com.diaspogift.identityandaccess.infrastructure.persistence.jpa;

import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.domain.model.identity.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class JPAUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void add(User aUser) {
        this.entityManager().persist(aUser);
    }

    public Collection<User> allSimilarlyNamedUsers(TenantId aTenantId, String aFirstNamePrefix, String aLastNamePrefix) {

        if (aFirstNamePrefix.endsWith("%") || aLastNamePrefix.endsWith("%")) {
            throw new IllegalArgumentException("Name prefixes must not include %.");
        }
        return this.entityManager()
                .createNamedQuery("selectAllSimilarlyNamedUsers", User.class)
                .setParameter("tenantId", aTenantId)
                .setParameter("firstNamePrefix", aFirstNamePrefix + "%")
                .setParameter("lastNamePrefix", aLastNamePrefix + "%")
                .getResultList();
    }

    public void remove(User aUser) {
        this.entityManager().remove(aUser);
    }

    public User userFromAuthenticCredentials(TenantId aTenantId, String aUsername, String anEncryptedPassword) {

        return this.entityManager()
                .createNamedQuery("selectUserFromAuthenticCredentials", User.class)
                .setParameter("tenantId", aTenantId)
                .setParameter("username", aUsername)
                .setParameter("password", anEncryptedPassword)
                .getSingleResult();
    }

    public User userWithUsername(TenantId aTenantId, String aUsername) {

        return this.entityManager()
                .createNamedQuery("selectUserWithUsername", User.class)
                .setParameter("tenantId", aTenantId)
                .setParameter("username", aUsername)
                .getSingleResult();
    }

    private EntityManager entityManager() {
        return this.entityManager;
    }

}