package com.diaspogift.identityandaccess.infrastructure.exception;

import java.util.HashMap;
import java.util.Map;

public class MessageKeyMapping {
    //public static final String DataIntegrityViolationException = "Erreur De Doublon";
    public static Map<String, String> map(){
        Map<String, String> mapping = new HashMap<String, String>();

        mapping.put("DataIntegrityViolationException", "Erreur De Doublon");
        mapping.put("EmptyResultDataAccessException", "Entite Absente");
        mapping.put("USER_MUST_EXISTS", "L'utilisateur doit exister au prealable.");

        return mapping;
    }
}
