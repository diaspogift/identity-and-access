package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService extends AssertionConcern {

    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserRepository userRepository;
    //Should it really be here????
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDescriptor authenticate(TenantId aTenantId, String aUsername, String aPassword) throws DiaspoGiftRepositoryException {

        this.assertArgumentNotNull(aTenantId, "TenantId must not be null.");
        this.assertArgumentNotEmpty(aUsername, "Username must be provided.");
        this.assertArgumentNotEmpty(aPassword, "Password must be provided.");

        try {

            UserDescriptor userDescriptor = UserDescriptor.nullDescriptorInstance();

            Tenant tenant = this.tenantRepository().tenantOfId(aTenantId);

            if (tenant != null && tenant.isActive()) {


                String encryptedPassword = this.encryptionService.encryptedValue(aPassword);


                User user =
                        this.userRepository
                                .userWithUsername(
                                        aTenantId,
                                        aUsername);


                if (user != null && user.isEnabled() && passwordEncoder.matches(aPassword, user.password())) {
                    userDescriptor = user.userDescriptor();
                }
            }

            return userDescriptor;

        } catch (EmptyResultDataAccessException e) {
            throw new BadCredentialsException("Bad Crredentials provided.");
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new BadCredentialsException("Bad Crredentials provided.");

        }
    }


    public EncryptionService encryptionService() {
        return this.encryptionService;
    }

    public TenantRepository tenantRepository() {
        return this.tenantRepository;
    }

    public UserRepository userRepository() {
        return this.userRepository;
    }
}
