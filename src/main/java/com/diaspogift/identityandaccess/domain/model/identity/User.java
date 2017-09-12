//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;

import javax.persistence.*;

@Entity
public class User extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer _id;

    /**
     * To enable the user
     */
    @Embedded
    private Enablement enablement;

    /**
     * The user encrypted password
     */
    private String password;

    /**
     * The person behind the user
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private Person person;

    /**
     * Tenant to which belong the user
     */
    @AttributeOverride(name = "id", column = @Column(name = "tenant_id"))
    @Embedded
    private TenantId tenantId;

    /**
     * The username (That uniquelly identify the user)
     */
    @Column(unique = true)
    private String username;


    protected User() {
        super();
    }

    protected User(
            TenantId aTenantId,
            String aUsername,
            String aPassword,
            Enablement anEnablement,
            Person aPerson) {

        this();

        this.setEnablement(anEnablement);
        this.setPerson(aPerson);
        this.setTenantId(aTenantId);
        this.setUsername(aUsername);

        this.protectPassword("", aPassword);

        this.setPassword(this.asEncryptedValue(aPassword));

        aPerson.internalOnlySetUser(this);

        DomainEventPublisher
                .instance()
                .publish(new UserRegistered(
                        this.tenantId(),
                        aUsername,
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
                        this.tenantId(),
                        this.username()));
    }

    public void changePersonalContactInformation(ContactInformation aContactInformation) {
        this.person().changeContactInformation(aContactInformation);
    }

    public void changePersonalName(FullName aPersonalName) {
        this.person().changeName(aPersonalName);
    }

    public void defineEnablement(Enablement anEnablement) {
        this.setEnablement(anEnablement);

        DomainEventPublisher
                .instance()
                .publish(new UserEnablementChanged(
                        this.tenantId(),
                        this.username(),
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

    public TenantId tenantId() {
        return this.tenantId;
    }

    public UserDescriptor userDescriptor() {
        return new UserDescriptor(
                this.tenantId(),
                this.username(),
                this.person().emailAddress().address());
    }

    public String username() {
        return this.username;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            User typedObject = (User) anObject;
            equalObjects =
                    this.tenantId().equals(typedObject.tenantId()) &&
                            this.username().equals(typedObject.username());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(45217 * 269)
                        + this.tenantId().hashCode()
                        + this.username().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "User [tenantId=" + tenantId + ", username=" + username
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
        this.assertArgumentNotEquals(
                this.username(),
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

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId is required.");

        this.tenantId = aTenantId;
    }

    protected GroupMember toGroupMember() {
        GroupMember groupMember =
                new GroupMember(
                        this.tenantId(),
                        this.username(),
                        GroupMemberType.User);

        return groupMember;
    }

    protected void setUsername(String aUsername) {
        this.assertArgumentNotEmpty(aUsername, "The username is required.");
        this.assertArgumentLength(aUsername, 3, 250, "The username must be 3 to 250 characters.");

        this.username = aUsername;
    }
}
