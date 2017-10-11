package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;
import com.diaspogift.identityandaccess.domain.model.identity.*;

import java.util.UUID;

public class Role extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;


    /**
     * A role identifier
     */
    private RoleId roleId;

    /**
     * A    bn description for the Role
     */
    private String description;

    /**
     * A group consisting of group members (of type User or Group)
     * playing this role
     */
    private Group group;

    /**
     * Specify wheter or not this role support nesting groups/roles
     */
    private boolean supportsNesting = true;


    public Role(RoleId aRoleIdId, String aDescription) {

        this(aRoleIdId, aDescription, false);
    }

    public Role(
            RoleId aRoleIdId,
            String aDescription,
            boolean aSupportsNesting) {

        this();

        this.setRoleIdId(aRoleIdId);
        this.setDescription(aDescription);
        this.setSupportsNesting(aSupportsNesting);
        this.createInternalGroup();

    }

    protected Role() {
        super();
    }

    public void assignGroup(Group aGroup, GroupMemberService aGroupMemberService) {
        this.assertStateTrue(this.supportsNesting(), "This role does not support group nesting.");
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");

        System.out.println("ASSIGNING THIS GROUP: " + aGroup.toString() + "TO THIS ROLE: " + this.toString());

        this.group().addGroup(aGroup, aGroupMemberService);

        DomainEventPublisher
                .instance()
                .publish(new GroupAssignedToRole(
                        this.tenantId(),
                        this.name(),
                        aGroup.name()));
    }

    public void assignUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this user.");

        this.group().addUser(aUser);

        // NOTE: Consider what a consuming Bounded Context would
        // need to do if this event was not enriched with the
        // last three user person properties. (Hint: A lot.)
        DomainEventPublisher
                .instance()
                .publish(new UserAssignedToRole(
                        this.tenantId(),
                        this.name(),
                        aUser.username(),
                        aUser.person().name().firstName(),
                        aUser.person().name().lastName(),
                        aUser.person().emailAddress().address()));
    }

    public String description() {
        return this.description;
    }

    public boolean isInRole(User aUser, GroupMemberService aGroupMemberService) {
        return this.group().isMember(aUser, aGroupMemberService);
    }


    public String name() {
        return this.roleId().name();
    }


    public boolean supportsNesting() {
        return this.supportsNesting;
    }


    public TenantId tenantId() {
        return this.roleId().tenantId();
    }


    public void unassignGroup(Group aGroup) {
        this.assertStateTrue(this.supportsNesting(), "This role does not support group nesting.");
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");

        this.group().removeGroup(aGroup);

        DomainEventPublisher
                .instance()
                .publish(new GroupUnassignedFromRole(
                        this.tenantId(),
                        this.name(),
                        aGroup.name()));
    }

    public void unassignUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this user.");

        this.group().removeUser(aUser);

        DomainEventPublisher
                .instance()
                .publish(new UserUnassignedFromRole(
                        this.tenantId(),
                        this.name(),
                        aUser.username()));
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Role typedObject = (Role) anObject;
            equalObjects =
                    this.tenantId().equals(typedObject.tenantId()) &&
                            this.name().equals(typedObject.name());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(18723 * 233)
                        + this.tenantId().hashCode()
                        + this.name().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", description='" + description + '\'' +
                ", group=" + group +
                ", supportsNesting=" + supportsNesting +
                '}';
    }

    protected void createInternalGroup() {
        String groupName =
                Group.ROLE_GROUP_PREFIX + UUID.randomUUID().toString().toUpperCase();

        this.setGroup(new Group(new GroupId(this.tenantId(), groupName), "Role backing group for: " + this.name()));
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "Role description is required.");
        this.assertArgumentLength(aDescription, 1, 250, "Role description must be 250 characters or less.");

        this.description = aDescription;
    }

    protected Group group() {
        return this.group;
    }

    protected void setGroup(Group aGroup) {
        this.group = aGroup;
    }

/*    protected void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "Role name must be provided.");
        this.assertArgumentLength(aName, 1, 250, "Role name must be 100 characters or less.");

        this.name = aName;
    }*/

    protected void setSupportsNesting(boolean aSupportsNesting) {
        this.supportsNesting = aSupportsNesting;
    }

   /* protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId is required.");

        this.tenantId = aTenantId;
    }*/

    public RoleId roleId() {
        return roleId;
    }

    protected void setRoleIdId(RoleId aRoleId) {

        this.assertArgumentNotNull(aRoleId.tenantId(), "The tenantId is required.");
        this.assertArgumentNotEmpty(aRoleId.name(), "Role name must be provided.");
        this.assertArgumentLength(aRoleId.name(), 1, 250, "Role name must be 100 characters or less.");

        this.roleId = aRoleId;
    }
}
