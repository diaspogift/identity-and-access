package com.diaspogift.identityandaccess.infrastructure.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DiaspogiftRipositoryException extends Exception {

    private String exceptionMessage;
    private Throwable cause;
    private String messageKey;

    public DiaspogiftRipositoryException(String exceptionMessage, Exception cause, String messageKey) {
        super(exceptionMessage,cause);
        setExceptionMessage(exceptionMessage);
        setCause(cause);
        setMessageKey(messageKey);
    }

    public Class<? extends Throwable> causeExceptionClass(){
        if (cause == null){
            return super.getClass();
        }
        return Throwable.class;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    private void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    private void setCause(Throwable cause) {
        this.cause = cause;
    }

    public String getMessageKey() {
        return messageKey;
    }

    private void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}


/**
 * Message key
 *
 *DataIntegrityViolationException
 *
 * EmptyResultDataAccessException
 *
 * USER_MUST_EXISTS "L'utilisateur doit exister au prealable."
 *
 */