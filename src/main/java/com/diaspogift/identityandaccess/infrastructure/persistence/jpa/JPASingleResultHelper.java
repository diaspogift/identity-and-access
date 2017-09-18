package com.diaspogift.identityandaccess.infrastructure.persistence.jpa;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.persistence.Query;
import java.util.List;

public class JPASingleResultHelper {

    public static Object getSingleResultOrNull(Query query) {

        List results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        }

        throw new IncorrectResultSizeDataAccessException(results.size());

    }
}
