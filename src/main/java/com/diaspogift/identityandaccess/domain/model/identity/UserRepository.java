package com.diaspogift.identityandaccess.domain.model.identity;

import java.util.Collection;

public interface UserRepository {

    void add(User aUser);

    Collection<User> allSimilarlyNamedUsers(TenantId aTenantId, String aFirstNamePrefix, String aLastNamePrefix);

    void remove(User aUser);

    User userFromAuthenticCredentials(TenantId aTenantId, String aUsername, String anEncryptedPassword);

    User userWithUsername(TenantId aTenantId, String aUsername);

    //TO DO TEST
    Collection<User> allUserFor(TenantId aTenantId);

    ContactInformation userContactInformation(String aTenantId, String aUsername);
}
