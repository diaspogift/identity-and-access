package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleId;
import com.diaspogift.identityandaccess.domain.model.access.RoleProvisioned;
import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;

import java.util.*;

public class Tenant extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Specify wether the a tenant is active or not
     */
    private boolean active;

    /**
     * Adescription for a tenant
     */
    private String description;

    /**
     * A name for a tenant
     */
    private String name;

    /**
     * A set of registrations invitations sent out by a tenant to its users
     */
    private Set<RegistrationInvitation> registrationInvitations;

    /**
     * A tenant's unique identifier
     */
    private TenantId tenantId;


    public Tenant(TenantId aTenantId, String aName, String aDescription, boolean anActive) {
        this();

        this.setActive(anActive);
        this.setDescription(aDescription);
        this.setName(aName);
        this.setTenantId(aTenantId);
    }

    protected Tenant() {
        super();

        this.setRegistrationInvitations(new HashSet<RegistrationInvitation>(0));
    }

    /**
     * Allows to activate a tenant (Make sure the given tenant is not active)
     * We then publish a TenantActivated domain event
     */
    public void activate() {
        if (!this.isActive()) {

            this.setActive(true);

            DomainEventPublisher
                    .instance()
                    .publish(new TenantActivated(this.tenantId()));
        }
    }

    /**
     * Allows to deactivate a tenant (Make sure the given tenant is active)
     * We then publish a TenantDeactivated domain event
     */
    public void deactivate() {
        if (this.isActive()) {

            this.setActive(false);

            DomainEventPublisher
                    .instance()
                    .publish(new TenantDeactivated(this.tenantId()));
        }
    }

    /**
     * Return a collection of invitation descriptor for available registration invitations
     * base on the startingOn and end date attributes for the the RegistrationInvitation class
     *
     * @return
     */
    public Collection<InvitationDescriptor> allAvailableRegistrationInvitations() {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        return this.allRegistrationInvitationsFor(true);
    }

    /**
     * Return a collection of invitation descriptor for available registration invitations
     * base on the startingOn and end date attributes for the the RegistrationInvitation class
     *
     * @return
     */
    public Collection<InvitationDescriptor> allUnavailableRegistrationInvitations() {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        return this.allRegistrationInvitationsFor(false);
    }

    /**
     * This method uses anInvitationIdentifier to verify wether there is a RegistrationInvitation in the collection
     * of Registration invitations with that invitation identifier.
     *
     * @param anInvitationIdentifier
     * @return
     */
    public boolean isRegistrationAvailableThrough(String anInvitationIdentifier) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        RegistrationInvitation invitation =
                this.invitation(anInvitationIdentifier);

        return invitation == null ? false : invitation.isAvailable();
    }

    /**
     * This method creates a registration invitation and adds it to the tenant's collection
     * of registration invitations.
     * It takes the following parameter:
     *
     * @param aDescription for the Registration invitation
     * @return
     */
    public RegistrationInvitation offerRegistrationInvitation(String aDescription) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        this.assertStateFalse(
                this.isRegistrationAvailableThrough(aDescription),
                "Invitation already exists.");

        RegistrationInvitation invitation =
                new RegistrationInvitation(
                        this.tenantId(),
                        UUID.randomUUID().toString().toUpperCase(),
                        aDescription);

        boolean added = this.registrationInvitations().add(invitation);

        this.assertStateTrue(added, "The invitation should have been added.");

        return invitation;
    }

    /**
     * This method creates a group for a given tenant accepting the following
     * parameters and return type:
     *
     * @param aName        for the group to be provision
     * @param aDescription for the group to be provisioned
     * @return Group (the provisioned group)
     * It then publishes a GroupProvisioned domain event
     */
    public Group provisionGroup(String aName, String aDescription) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        Group group = new Group(new GroupId(this.tenantId(), aName), aDescription);

        DomainEventPublisher
                .instance()
                .publish(new GroupProvisioned(
                        this.tenantId(),
                        aName));

        return group;
    }

    /**
     * This method creates a role that does not support nesting groups for a given tenant accepting the following
     * parameters and return type:
     *
     * @param aName        for the role to be provisioned
     * @param aDescription for the role to be provisioned
     * @return Role (the provisioned role)
     * It then publishes a RoleProvisioned domain event
     */
    public Role provisionRole(String aName, String aDescription) {
        return this.provisionRole(aName, aDescription, false);
    }

    /**
     * This method creates a role for a given tenant accepting the following
     * parameters and return type:
     *
     * @param aName
     * @param aDescription
     * @param aSupportsNesting
     * @return Role (the provisioned role)
     * It then publishes a RoleProvisioned domain event
     */
    public Role provisionRole(String aName, String aDescription, boolean aSupportsNesting) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        Role role = new Role(new RoleId(this.tenantId(), aName), aDescription, aSupportsNesting);

        DomainEventPublisher
                .instance()
                .publish(new RoleProvisioned(
                        this.tenantId(),
                        aName));

        return role;
    }

    /**
     * This method creates a tenant's user accepting as parameters the following:
     *
     * @param anInvitationIdentifier generated by the tenant and sent to the user
     * @param aUsername              for the user to register
     * @param aPassword              for the user to register
     * @param anEnablement           to state wether the user is enabled or not
     * @param aPerson                behind the user to register
     * @return User (user to be registered)
     */
    public User registerUser(
            String anInvitationIdentifier,
            String aUsername,
            String aPassword,
            Enablement anEnablement,
            Person aPerson) {

        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        User user = null;


        if (this.isRegistrationAvailableThrough(anInvitationIdentifier)) {
            UserId userId = new UserId(this.tenantId(), aUsername);
            user = new User(userId, aPassword, anEnablement, aPerson);
        }

        return user;
    }

    /**
     * Reset starting and until dates for a given invitation making it open ended
     *
     * @param anInvitationIdentifier for the invitation to be find
     * @return
     */
    public RegistrationInvitation redefineRegistrationInvitationAsOpenEnded(String anInvitationIdentifier) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        RegistrationInvitation invitation =
                this.invitation(anInvitationIdentifier);

        if (invitation != null) {
            invitation.redefineAs().openEnded();
        }

        return invitation;
    }

    /**
     * This method withdraws and invitation that the tenant offered to a user.
     * it accepts the following parameters
     *
     * @param anInvitationIdentifier for identifying the specific invitation to be removed from the set of invitations
     */
    public void withdrawInvitation(String anInvitationIdentifier) {
        RegistrationInvitation invitation =
                this.invitation(anInvitationIdentifier);

        if (invitation != null) {
            this.registrationInvitations().remove(invitation);
        }
    }

    /**
     * This method return invitation descriptors for all invitation based upon the value passed for isVailable
     * and the startingOn and until date attributes
     *
     * @param isAvailable
     * @return
     */
    protected Collection<InvitationDescriptor> allRegistrationInvitationsFor(boolean isAvailable) {
        Set<InvitationDescriptor> allInvitations = new HashSet<InvitationDescriptor>();

        for (RegistrationInvitation invitation : this.registrationInvitations()) {
            if (invitation.isAvailable() == isAvailable) {
                allInvitations.add(invitation.toDescriptor());
            }
        }

        return Collections.unmodifiableSet(allInvitations);
    }

    /**
     * This method find the corresponding Invivtation from the set of invitations given its identifier
     *
     * @param anInvitationIdentifier
     * @return
     */
    protected RegistrationInvitation invitation(String anInvitationIdentifier) {
        for (RegistrationInvitation invitation : this.registrationInvitations()) {
            if (invitation.isIdentifiedBy(anInvitationIdentifier)) {
                return invitation;
            }
        }

        return null;
    }

    public String description() {
        return this.description;
    }

    public boolean isActive() {
        return this.active;
    }

    protected void setActive(boolean anActive) {
        this.active = anActive;
    }

    public String name() {
        return this.name;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "The tenant description is required.");
        this.assertArgumentLength(aDescription, 1, 100, "The tenant description must be 100 characters or less.");

        this.description = aDescription;
    }

    protected void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "The tenant name is required.");
        this.assertArgumentLength(aName, 1, 100, "The name must be 100 characters or less.");

        this.name = aName;
    }

    protected Set<RegistrationInvitation> registrationInvitations() {
        return this.registrationInvitations;
    }

    protected void setRegistrationInvitations(Set<RegistrationInvitation> aRegistrationInvitations) {
        this.registrationInvitations = aRegistrationInvitations;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "TenentId is required.");

        this.tenantId = aTenantId;
    }


    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Tenant typedObject = (Tenant) anObject;
            equalObjects =
                    this.tenantId().equals(typedObject.tenantId()) &&
                            this.name().equals(typedObject.name());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(48123 * 257)
                        + this.tenantId().hashCode()
                        + this.name().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Tenant [active=" + active + ", description=" + description
                + ", name=" + name + ", tenantId=" + tenantId + "]";
    }

}
