package com.diaspogift.identityandaccess.infrastructure.repository;

import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import com.diaspogift.identityandaccess.domain.model.identity.User;
import com.diaspogift.identityandaccess.domain.model.identity.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class MockUserRepository implements UserRepository{

    private Set<User> users;

    @Override
    public void add(User aUser) {
        if (users == null){
            users = new HashSet<>();
        }
        users.add(aUser);
    }

    @Override
    public Collection<User> allSimilarlyNamedUsers(TenantId aTenantId, String aFirstNamePrefix, String aLastNamePrefix) {
        Set<User> retVal = new HashSet<>();
        for (User next : users){
            if (aTenantId.equals(next.tenantId()) && next.person().name().firstName().startsWith(aFirstNamePrefix) &&
                    next.person().name().lastName().startsWith(aLastNamePrefix)){

                retVal.add(next);

            }
        }
        return retVal;
    }

    @Override
    public void remove(User aUser) {
        users.remove(aUser);
    }

    @Override
    public User userFromAuthenticCredentials(TenantId aTenantId, String aUsername, String anEncryptedPassword) {

        //System.out.println("\n\n\nusers == " + this.users + "\n\n");

        for (User next : users){
            /*System.out.println("\n\n\naTenantId == " + aTenantId.id()+ "\n\n");
            System.out.println("\n\n\naUsername == " + aUsername+ "\n\n");
            System.out.println("\n\n\nanEncryptedPassword == " + anEncryptedPassword+ "\n\n");

            System.out.println("\n\n\nnext == " + next + "\n\n");
            System.out.println("\n\n\nnext.username() == " + next.username() + "\n\n");
            System.out.println("\n\n\nnext.internalAccessOnlyEncryptedPassword() == " + next.internalAccessOnlyEncryptedPassword()+ "\n\n");
            */
            if (aTenantId.equals(next.tenantId()) && next.username().equals(aUsername) &&
                    next.internalAccessOnlyEncryptedPassword().equals(anEncryptedPassword)){
                return next;
            }
        }
        return null;
    }

    @Override
    public User userWithUsername(TenantId aTenantId, String aUsername) {
        for (User next : users){
            if (aTenantId.equals(next.tenantId()) && next.username().equals(aUsername)){
                return next;
            }
        }
        return null;
    }

    public Set<User> users(){
        return users;
    }
}
