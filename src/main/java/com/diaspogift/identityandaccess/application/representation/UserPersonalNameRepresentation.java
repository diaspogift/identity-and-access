package com.diaspogift.identityandaccess.application.representation;

import com.diaspogift.identityandaccess.domain.model.identity.FullName;
import org.springframework.hateoas.ResourceSupport;

public class UserPersonalNameRepresentation extends ResourceSupport {

    private String firstName;
    private String lastName;


    public UserPersonalNameRepresentation() {
        super();
    }

    public UserPersonalNameRepresentation(FullName fullName) {

        this.firstName = fullName.firstName();
        this.lastName = fullName.lastName();
    }

    public UserPersonalNameRepresentation(String aFirstName, String aLastName) {

        this.firstName = aFirstName;
        this.lastName = aLastName;
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
