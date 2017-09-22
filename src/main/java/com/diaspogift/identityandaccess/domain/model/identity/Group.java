package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.common.ConcurrencySafeEntity;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Group extends ConcurrencySafeEntity {

    public static final String ROLE_GROUP_PREFIX = "ROLE-INTERNAL-GROUP:";
    private static final long serialVersionUID = 1L;
    private GroupId groupId;
    private String description;
    private Set<GroupMember> groupMembers;


    public Group(GroupId aGroupId, String aDescription) {
        this();

        this.setDescription(aDescription);
        this.setGroupId(aGroupId);
    }

    protected Group() {
        super();

        this.setGroupMembers(new HashSet<GroupMember>(0));
    }

    public void addGroup(Group aGroup, GroupMemberService aGroupMemberService) {
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");
        this.assertArgumentFalse(aGroupMemberService.isMemberGroup(aGroup, this.toGroupMember()), "Group recurrsion.");


        if (this.groupMembers().add(aGroup.toGroupMember()) /*&& !this.isInternalGroup()*/) {

            DomainEventPublisher
                    .instance()
                    .publish(new GroupGroupAdded(
                            this.tenantId(),
                            this.name(),
                            aGroup.name()));
        }

    }


    public void addUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this group.");
        this.assertArgumentTrue(aUser.isEnabled(), "User is not enabled.");

        if (this.groupMembers().add(aUser.toGroupMember()) /*&& !this.isInternalGroup()*/) {
            DomainEventPublisher
                    .instance()
                    .publish(new GroupUserAdded(
                            this.tenantId(),
                            this.name(),
                            aUser.username()));
        }
    }

    public String description() {
        return this.description;
    }

    public Set<GroupMember> groupMembers() {
        return this.groupMembers;
    }

    public boolean isMember(User aUser, GroupMemberService aGroupMemberService) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this group.");
        this.assertArgumentTrue(aUser.isEnabled(), "User is not enabled.");

        boolean isMember =
                this.groupMembers().contains(aUser.toGroupMember());

        if (isMember) {
            isMember = aGroupMemberService.confirmUser(this, aUser);
        } else {
            isMember = aGroupMemberService.isUserInNestedGroup(this, aUser);
        }

        return isMember;
    }


    public void removeGroup(Group aGroup) {
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");
        // not a nested remove, only direct member
        if (this.groupMembers().remove(aGroup.toGroupMember()) /*&& !this.isInternalGroup()*/) {
            DomainEventPublisher
                    .instance()
                    .publish(new GroupGroupRemoved(
                            this.tenantId(),
                            this.name(),
                            aGroup.name()));
        }
    }

    public void removeUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this group.");

        // not a nested remove, only direct member
        if (this.groupMembers().remove(aUser.toGroupMember()) && !this.isInternalGroup()) {
            DomainEventPublisher
                    .instance()
                    .publish(new GroupUserRemoved(
                            this.tenantId(),
                            this.name(),
                            aUser.username()));
        }
    }


    public GroupId groupId() {
        return this.groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return groupId != null ? groupId.equals(group.groupId) : group.groupId == null;
    }

    @Override
    public int hashCode() {
        return groupId != null ? groupId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", description='" + description + '\'' +
                ", groupMembers=" + groupMembers +
                '}';
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "Group description is required.");
        this.assertArgumentLength(aDescription, 1, 250, "Group description must be 250 characters or less.");

        this.description = aDescription;
    }

    protected void setGroupMembers(Set<GroupMember> aGroupMembers) {
        this.groupMembers = aGroupMembers;
    }

    protected boolean isInternalGroup() {
        return this.isInternalGroup(this.name());
    }

    protected boolean isInternalGroup(String aName) {
        return aName.startsWith(ROLE_GROUP_PREFIX);
    }


    protected GroupMember toGroupMember() {
        GroupMember groupMember =
                new GroupMember(
                        this.tenantId(),
                        this.name(),
                        GroupMemberType.Group);


        return groupMember;
    }

    public TenantId tenantId() {
        return this.groupId().tenantId();
    }

    public String name() {
        return this.groupId().name();
    }

    public void setGroupId(GroupId aGroupId) {

        this.assertArgumentNotNull(aGroupId.tenantId(), "The tenantId must be provided.");
        this.assertArgumentNotEmpty(aGroupId.name(), "Group name is required.");
        this.assertArgumentLength(aGroupId.name(), 1, 100, "Group name must be 100 characters or less.");

        if (this.isInternalGroup(aGroupId.name())) {
            String uuid = aGroupId.name().substring(ROLE_GROUP_PREFIX.length());

            try {
                UUID.fromString(uuid);
            } catch (Exception e) {
                throw new IllegalArgumentException("The group name has an invalid format.");
            }
        }

        this.groupId = aGroupId;
    }


}
