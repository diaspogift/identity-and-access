package com.diaspogift.identityandaccess.application.representation;

import org.springframework.hateoas.ResourceSupport;

public class UserPersonalNameRepresentation extends ResourceSupport {

    private String firstName;
    private String lastName;


    public UserPersonalNameRepresentation() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
