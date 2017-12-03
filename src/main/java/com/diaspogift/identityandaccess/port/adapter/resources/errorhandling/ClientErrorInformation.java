package com.diaspogift.identityandaccess.port.adapter.resources.errorhandling;

import com.diaspogift.identityandaccess.port.adapter.persistence.exception.ExceptionMessageKeyMap;

public class ClientErrorInformation {

    private String message;
    private String uri;

    public ClientErrorInformation() {
    }

    public ClientErrorInformation(String exceptionFullyQualifiedClassName, String uri) {

        this.message = ExceptionMessageKeyMap.map().get(exceptionFullyQualifiedClassName);
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
