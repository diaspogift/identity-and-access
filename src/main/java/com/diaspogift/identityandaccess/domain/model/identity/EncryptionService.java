package com.diaspogift.identityandaccess.domain.model.identity;

public interface EncryptionService {

    String encryptedValue(String aPlainTextValue);

    Boolean matchesPassword(String aPlainTextValue, String aBcryptEncryptedPassword);
}
