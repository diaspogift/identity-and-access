package com.diaspogift.identityandaccess.infrastructure;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.domain.model.identity.EncryptionService;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;

@Component
public class SHA5012EncryptionService extends AssertionConcern implements EncryptionService {


    public SHA5012EncryptionService() {
        super();
    }

    @Override
    public String encryptedValue(String aPlainTextValue) {

        // ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
        this.assertArgumentNotEmpty(
                aPlainTextValue,
                "Plain text value to encrypt must be provided.");

        String encryptedValue = null;

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            messageDigest.update(aPlainTextValue.getBytes("UTF-8"));

            BigInteger bigInt = new BigInteger(1, messageDigest.digest());

            encryptedValue = bigInt.toString(16);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return encryptedValue;
    }
}
