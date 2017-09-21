package com.diaspogift.identityandaccess.infrastructure.persistence.exception;


import java.util.HashMap;
import java.util.Map;

public class ExceptionMessageKeyMap {

    public static Map<String, String> map() {

        Map<String, String> mapping = new HashMap<String, String>();

        mapping.put(DiaspoGiftRepositoryException.class.getName(), "Erreur De Doublon ou record introuve.");
        mapping.put(IllegalArgumentException.class.getName(), "Arguments au format incorrect passses.");
        mapping.put(IllegalStateException.class.getName(), "Status incorrect.");


        return mapping;
    }
}