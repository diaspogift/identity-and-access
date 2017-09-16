package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;

import java.util.UUID;

public class Person extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Identify a unique person
     */
    private String personId;
    /**
     * Contact of the person
     */
    private ContactInformation contactInformation;

    /**
     * <h3>Is constitated of first name and last name</h3>
     */
    private FullName name;


    public Person(
            FullName aName,
            ContactInformation aContactInformation) {

        this();

        this.setContactInformation(aContactInformation);
        this.setName(aName);
        this.setPersonId(UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase());
    }

    protected Person() {
        super();
    }

    private void setPersonId(String aPersonId) {
        this.personId = aPersonId;
    }

    /**
     * @param aContactInformation A person can change his contact information by providing a new one. <br />
     *                            When a contactInformation is changed a domain Event is sent and change is save.
     */
    public void changeContactInformation(ContactInformation aContactInformation) {
        this.setContactInformation(aContactInformation);
    }

    /**
     * @param aName A person can change his name by  providing a new one.
     */

    public void changeName(FullName aName) {
        this.setName(aName);
    }

    public ContactInformation contactInformation() {
        return this.contactInformation;
    }

    public EmailAddress emailAddress() {
        return this.contactInformation().emailAddress();
    }

    public FullName name() {
        return this.name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return personId != null ? personId.equals(person.personId) : person.personId == null;
    }

    @Override
    public int hashCode() {
        return personId != null ? personId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Person [ personId=" + personId + ", name=" + name + ", contactInformation=" + contactInformation + "]";
    }

    protected void setContactInformation(ContactInformation aContactInformation) {
        this.assertArgumentNotNull(aContactInformation, "The person contact information is required.");

        this.contactInformation = aContactInformation;
    }

    protected void setName(FullName aName) {
        this.assertArgumentNotNull(aName, "The person name is required.");

        this.name = aName;
    }

}
