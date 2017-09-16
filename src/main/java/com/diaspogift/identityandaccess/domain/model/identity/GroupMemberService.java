package com.diaspogift.identityandaccess.domain.model.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class GroupMemberService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * In this method we assume that aUser belong to group aGroup.
     *
     * @param aGroup The group in which the user belong
     * @param aUser  The user we want to confirm
     * @return Return true if a user with the group tenantid and with user username exists and is enabled.
     * @see Group
     */
    public boolean confirmUser(Group aGroup, User aUser) {
        boolean userConfirmed = true;

        User confirmedUser =
                this.userRepository()
                        .userWithUsername(aGroup.groupId().tenantId(), aUser.userId().username());

        if (confirmedUser == null || !confirmedUser.isEnabled()) {
            userConfirmed = false;
        }

        return userConfirmed;
    }

    /**
     * We assume that in this method, aMemberGroup parameter is type GROUP. It verify that a group member belong or not
     * to a group
     *
     * @param aGroup       Group in which we are looking the group member
     * @param aMemberGroup The group member to look for.
     * @return return true if the group member was found and false if not.
     */
    public boolean isMemberGroup(Group aGroup, GroupMember aMemberGroup) {


        if (aMemberGroup == null || aMemberGroup.isUser()) {
            return false;
        }

        boolean isMember = false;

        Iterator<GroupMember> iter =
                aGroup.groupMembers().iterator();

        while (!isMember && iter.hasNext()) {

            GroupMember member = iter.next();


            if (member.isGroup()) {

                if (aMemberGroup.equals(member)) {
                    isMember = true;
                } else {
                    Group group =
                            this.groupRepository()
                                    .groupNamed(member.tenantId(), member.name());
                    if (group != null) {
                        isMember = this.isMemberGroup(group, aMemberGroup);
                    }
                }
            }
            // A omis de faire le esle de ce if.
        }

        return isMember;
    }

    /**
     * Verify that a user belong to a nested group of a group.
     *
     * @param aGroup The group in which to look for user in nested group
     * @param aUser  The user to look for
     * @return return true if the user was found and false if not
     */
    public boolean isUserInNestedGroup(Group aGroup, User aUser) {
        boolean isInNestedGroup = false;

        Iterator<GroupMember> iter =
                aGroup.groupMembers().iterator();

        while (!isInNestedGroup && iter.hasNext()) {
            GroupMember member = iter.next();
            if (member.isGroup()) {
                Group group =
                        this.groupRepository()
                                .groupNamed(member.tenantId(), member.name());
                if (group != null) {
                    isInNestedGroup = group.isMember(aUser, this);
                }
            }
        }

        return isInNestedGroup;
    }

    private GroupRepository groupRepository() {
        return this.groupRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }
}
