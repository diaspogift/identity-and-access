package com.diaspogift.identityandaccess.port.adapter.persistence.exception;

public class DiaspoGiftRepositoryException extends Exception {

    private String exceptionMessage;
    private Throwable cause;
    private String messageKey;

    public DiaspoGiftRepositoryException(String exceptionMessage, Exception cause, String messageKey) {

        super(exceptionMessage, cause);
        setExceptionMessage(exceptionMessage);
        setCause(cause);
        setMessageKey(messageKey);
    }

    public Class<? extends Throwable> causeExceptionClass() {
        if (cause == null) {
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
