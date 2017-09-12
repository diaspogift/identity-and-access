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

package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return user == null ? false : this.isUserInRole(user, aRoleName);
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
