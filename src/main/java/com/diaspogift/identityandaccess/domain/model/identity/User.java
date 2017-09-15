package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;

public class User extends AssertionConcern /*extends ConcurrencySafeEntity*/ {

    private static final long serialVersionUID = 1L;

    /**
     * To enable the user
     */
    private Enablement enablement;

    /**
     * The user encrypted password
     */
    private String password;

    /**
     * The person behind the user
     */
    private Person person;

    /**
     * A User unique identifier
     */
    private UserId userId;


    protected User() {
        super();
    }

    protected User(
            UserId aUserId,
            String aPassword,
            Enablement anEnablement,
            Person aPerson) {

        this();
        this.setEnablement(anEnablement);
        this.setPerson(aPerson);
        this.setUserId(aUserId);

        this.protectPassword("", aPassword);

        this.setPassword(this.asEncryptedValue(aPassword));

        //aPerson.internalOnlySetUser(this);

        DomainEventPublisher
                .instance()
                .publish(new UserRegistered(
                        this.userId().tenantId(),
                        this.userId().username(),
                        aPerson.name(),
                        aPerson.contactInformation().emailAddress()));
    }

    /**
     * @param aCurrentPassword
     * @param aNewPassword     To change the user password
     * @param aNewPassword     To change the user password by providing current password and the new one.
     */

    public void changePassword(String aCurrentPassword, String aNewPassword) {
        this.assertArgumentNotEmpty(
                aCurrentPassword,
                "Current and new password must be provided.");

        this.assertArgumentEquals(
                this.password(),
                this.asEncryptedValue(aCurrentPassword),
                "Current password not confirmed.");

        this.protectPassword(aCurrentPassword, aNewPassword);

        this.setPassword(this.asEncryptedValue(aNewPassword));

        DomainEventPublisher
                .instance()
                .publish(new UserPasswordChanged(
                        this.userId().tenantId(),
                        this.userId().username()));
    }

    public void changePersonalContactInformation(ContactInformation aContactInformation) {
        this.person().changeContactInformation(aContactInformation);

        DomainEventPublisher
                .instance()
                .publish(new PersonContactInformationChanged(
                        this.userId().tenantId(),
                        this.userId().username(),
                        aContactInformation));
    }

    public void changePersonalName(FullName aPersonalName) {

        this.person().changeName(aPersonalName);

        DomainEventPublisher
                .instance()
                .publish(new PersonNameChanged(
                        this.userId().tenantId(),
                        this.userId().username(),
                        aPersonalName));
    }

    public void defineEnablement(Enablement anEnablement) {
        this.setEnablement(anEnablement);

        DomainEventPublisher
                .instance()
                .publish(new UserEnablementChanged(
                        this.userId().tenantId(),
                        this.userId().username(),
                        this.enablement()));
    }

    /**
     * @return
     */
    public boolean isEnabled() {
        return this.enablement().isEnablementEnabled();
    }

    public Person person() {
        return this.person;
    }

/*
    public TenantId tenantId() {
        return this.tenantId;
    }
*/

    public UserDescriptor userDescriptor() {
        return new UserDescriptor(
                this.userId().tenantId(),
                this.userId().username(),
                this.person().emailAddress().address());
    }

/*
    public String username() {
        return this.username;
    }
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId != null ? userId.equals(user.userId) : user.userId == null;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User [tenantId=" + this.userId().tenantId() + ", username=" + this.userId().username()
                + ", person=" + person + ", enablement=" + enablement + "]";
    }

    protected String asEncryptedValue(String aPlainTextPassword) {
        String encryptedValue =
                DomainRegistry
                        .encryptionService()
                        .encryptedValue(aPlainTextPassword);

        return encryptedValue;
    }

    protected void assertPasswordsNotSame(String aCurrentPassword, String aChangedPassword) {
        this.assertArgumentNotEquals(
                aCurrentPassword,
                aChangedPassword,
                "The password is unchanged.");
    }

    protected void assertPasswordNotWeak(String aPlainTextPassword) {
        this.assertArgumentFalse(
                DomainRegistry.passwordService().isWeak(aPlainTextPassword),
                "The password must be stronger.");
    }

    protected void assertUsernamePasswordNotSame(String aPlainTextPassword) {

        System.out.println("\n\n\n ????????????????????  in assertUsernamePasswordNotSame this.userId() ????????????? " + this.userId());
        this.assertArgumentNotEquals(
                this.userId().username(),
                aPlainTextPassword,
                "The username and password must not be the same.");
    }

    protected Enablement enablement() {
        return this.enablement;
    }

    protected void setEnablement(Enablement anEnablement) {
        this.assertArgumentNotNull(anEnablement, "The enablement is required.");

        this.enablement = anEnablement;
    }

    public String internalAccessOnlyEncryptedPassword() {
        return this.password();
    }

    protected String password() {
        return this.password;
    }

    protected void setPassword(String aPassword) {
        this.password = aPassword;
    }

    protected void setPerson(Person aPerson) {
        this.assertArgumentNotNull(aPerson, "The person is required.");

        this.person = aPerson;
    }

    protected void protectPassword(String aCurrentPassword, String aChangedPassword) {
        this.assertPasswordsNotSame(aCurrentPassword, aChangedPassword);

        this.assertPasswordNotWeak(aChangedPassword);

        this.assertUsernamePasswordNotSame(aChangedPassword);

    }

/*    protected void setTenantId(TenantId aTenantId) {


        this.tenantId = aTenantId;
    }*/

    protected GroupMember toGroupMember() {
        GroupMember groupMember =
                new GroupMember(
                        this.userId().tenantId(),
                        this.userId().username(),
                        GroupMemberType.User);

        return groupMember;
    }

 /*   protected void setUsername(String aUsername) {

        this.username = aUsername;
    }*/

    protected void setUserId(UserId aUserId) {

        this.assertArgumentNotNull(aUserId, "User Identifier is required.");
        this.assertArgumentNotNull(aUserId.tenantId(), "The tenantId is required.");
        this.assertArgumentNotEmpty(aUserId.username(), "The username is required.");
        this.assertArgumentLength(aUserId.username(), 3, 250, "The username must be 3 to 250 characters.");

        this.userId = aUserId;
    }

    public UserId userId() {
        return this.userId;
    }
}
