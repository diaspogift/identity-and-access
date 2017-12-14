package com.diaspogift.identityandaccess.port.adapter.service;

import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.domain.model.identity.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SHA3BCryptEncryptionService extends AssertionConcern implements EncryptionService {

    private static final Logger log = LoggerFactory.getLogger(SHA3BCryptEncryptionService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;


    public SHA3BCryptEncryptionService() {
        super();
    }

    @Override
    public String encryptedValue(String aPlainTextValue) {

        this.assertArgumentNotEmpty(aPlainTextValue, "Plain text value to encrypt must be provided.");

        String encryptedValue = null;


        try {

            encryptedValue = passwordEncoder.encode(aPlainTextValue);

            log.info("\n\n plain text password in SHA3BCryptEncryptionService  ==  " + aPlainTextValue + "\n\n");
            log.info("\n\n encryptedValue password in SHA3BCryptEncryptionService  ==  " + encryptedValue + "\n\n");

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return encryptedValue;
    }
}
