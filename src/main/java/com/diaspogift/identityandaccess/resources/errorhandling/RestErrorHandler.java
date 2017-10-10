package com.diaspogift.identityandaccess.resources.errorhandling;

import com.diaspogift.identityandaccess.infrastructure.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestErrorHandler {


    /**
     * Exception handling
     */

    @ExceptionHandler(DiaspoGiftRepositoryException.class)
    public ResponseEntity rulesForTenantNotFound(Exception e, HttpServletRequest req) {


        System.out.println("\n\n IRANNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        System.out.println("\n\n IRANNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        System.out.println("\n\n IRANNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        System.out.println("\n\n IRANNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");

        ClientErrorInformation errorInformation = new ClientErrorInformation(e.getClass().getName(), req.getRequestURI());
        return new ResponseEntity(errorInformation, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity rulesForIllegalArgument(Exception e, HttpServletRequest req) {
        ClientErrorInformation errorInformation = new ClientErrorInformation(e.getClass().getName(), req.getRequestURI());
        return new ResponseEntity(errorInformation, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity rulesForIllegalState(Exception e, HttpServletRequest req) {
        ClientErrorInformation errorInformation = new ClientErrorInformation(e.getClass().getName(), req.getRequestURI());
        return new ResponseEntity(errorInformation, HttpStatus.BAD_REQUEST);
    }

}
