package com.diaspogift.identityandaccess.infrastructure.persistence;

import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.domain.model.identity.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

public class JPAUserRepository implements UserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public void add(User aUser) {
        this.entityManager().persist(aUser);
    }

    public Collection<User> allSimilarlyNamedUsers(TenantId aTenantId, String aFirstNamePrefix, String aLastNamePrefix) {
        return null;
    }

    public void remove(User aUser) {

    }

    public User userFromAuthenticCredentials(TenantId aTenantId, String aUsername, String anEncryptedPassword) {
        return null;
    }

    public User userWithUsername(TenantId aTenantId, String aUsername) {
        return null;
    }

    public EntityManager entityManager() {
        return this.entityManager;
    }

}
