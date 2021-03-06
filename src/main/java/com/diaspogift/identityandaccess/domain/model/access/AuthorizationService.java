package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class AuthorizationService extends AssertionConcern {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupMemberService groupMemberService;


    public boolean isUserInRole(TenantId aTenantId, String aUsername, String aRoleName) {
        this.assertArgumentNotNull(aTenantId, "TenantId must not be null.");
        this.assertArgumentNotEmpty(aUsername, "Username must not be provided.");
        this.assertArgumentNotEmpty(aRoleName, "Role name must not be null.");

        User user = this.userRepository().userWithUsername(aTenantId, aUsername);

        return user != null && this.isUserInRole(user, aRoleName);
    }

    public boolean isUserInRole(User aUser, String aRoleName) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentNotEmpty(aRoleName, "Role name must not be null.");

        boolean authorized = false;

        if (aUser.isEnabled()) {
            Role role = this.roleRepository().roleNamed(aUser.tenantId(), aRoleName);

            if (role != null) {

                authorized = role.isInRole(aUser, this.groupMemberService());
            }
        }

        return authorized;
    }


    //////////// NOT TESTED ////
    public Collection<RoleDescriptor> allRolesForIdentifiedUser(TenantId aTenantId, String aUsername) {

        User user = this.userRepository().userWithUsername(aTenantId, aUsername);


        Collection<RoleDescriptor> allUserRoleDescriptors = new HashSet<RoleDescriptor>();

        Collection<Role> allTenantRoles = roleRepository().allRoles(aTenantId);

        for (Role next : allTenantRoles) {

            if (isUserInRole(user, next.roleId().name())) {

                allUserRoleDescriptors.add(next.roleDescriptor());
            }
        }

        return allUserRoleDescriptors;

    }

    //////////// NOT TESTED ////
    public Collection<GroupDescriptor> allGroupsForIdentifiedUser(TenantId aTenantId, String aUsername) {

        User user = this.userRepository().userWithUsername(aTenantId, aUsername);


        System.out.println("\n\n UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU " + user.toString() + "UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU\n\n");


        Collection<GroupDescriptor> allUsersGroupsDescriptors = new HashSet<GroupDescriptor>();

        Collection<Group> allTenantsGroups = this.groupRepository().allGroups(aTenantId);

        for (Group next : allTenantsGroups) {

            System.out.println("\n\n GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG " + next.toString() + "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG\n\n");

            if (next.isMember(user, DomainRegistry.groupMemberService())) {

                allUsersGroupsDescriptors.add(next.toGroupDescriptor());
            }
        }

        return allUsersGroupsDescriptors;

    }


    private GroupRepository groupRepository() {
        return this.groupRepository;
    }

    private RoleRepository roleRepository() {
        return this.roleRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }

    public GroupMemberService groupMemberService() {
        return this.groupMemberService;
    }
}
