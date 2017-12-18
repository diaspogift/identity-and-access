package com.diaspogift.identityandaccess.application.representation.services;

import org.springframework.hateoas.ResourceSupport;

public class StringRepresentation extends ResourceSupport {

    private String value;

    public StringRepresentation(String value) {
        this.value = value;
    }


    public StringRepresentation() {
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
