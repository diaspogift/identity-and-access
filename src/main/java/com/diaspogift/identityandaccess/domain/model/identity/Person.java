package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;

public class Person extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Contact of the person
     */
    private ContactInformation contactInformation;

    /**
     * <h3>Is constitated of first name and last name</h3>
     */
    private FullName name;

    /**
     * The id of the tenant
     */
    private TenantId tenantId;

    /**
     * User associated to this person
     */
    private User user;

    public Person(
            TenantId aTenantId,
            FullName aName,
            ContactInformation aContactInformation) {

        this();

        this.setContactInformation(aContactInformation);
        this.setName(aName);
        this.setTenantId(aTenantId);
    }

    protected Person() {
        super();
    }

    /**
     * @param aContactInformation A person ca change his contact information by providing a new one. <br />
     *                            When a contactInformation is change a domain Event is sended and change is save.
     */
    public void changeContactInformation(ContactInformation aContactInformation) {
        this.setContactInformation(aContactInformation);

        DomainEventPublisher
                .instance()
                .publish(new PersonContactInformationChanged(
                        this.tenantId(),
                        this.user().username(),
                        this.contactInformation()));
    }

    /**
     * @param aName A person can change his name by  providing a new one.
     */

    public void changeName(FullName aName) {
        this.setName(aName);

        DomainEventPublisher
                .instance()
                .publish(new PersonNameChanged(
                        this.tenantId(),
                        this.user().username(),
                        this.name()));
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
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Person typedObject = (Person) anObject;
            equalObjects =
                    this.tenantId().equals(typedObject.tenantId()) &&
                            this.user().username().equals(typedObject.user().username());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(90113 * 223)
                        + this.tenantId().hashCode()
                        + this.user().username().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Person [tenantId=" + tenantId + ", name=" + name + ", contactInformation=" + contactInformation + "]";
    }

    protected void setContactInformation(ContactInformation aContactInformation) {
        this.assertArgumentNotNull(aContactInformation, "The person contact information is required.");

        this.contactInformation = aContactInformation;
    }

    protected void setName(FullName aName) {
        this.assertArgumentNotNull(aName, "The person name is required.");

        this.name = aName;
    }

    protected TenantId tenantId() {
        return this.tenantId;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId is required.");

        this.tenantId = aTenantId;
    }

    protected User user() {
        return this.user;
    }

    public void internalOnlySetUser(User aUser) {
        this.user = aUser;
    }
}
