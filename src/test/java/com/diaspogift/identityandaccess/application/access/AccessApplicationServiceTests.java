//   Copyright 2012,2013 Vaughn Vernon////   Licensed under the Apache License, Version 2.0 (the "License");//   you may not use this file except in compliance with the License.//   You may obtain a copy of the License at////       http://www.apache.org/licenses/LICENSE-2.0////   Unless required by applicable law or agreed to in writing, software//   distributed under the License is distributed on an "AS IS" BASIS,//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.//   See the License for the specific language governing permissions and//   limitations under the License.package com.diaspogift.identityandaccess.application.access;import com.diaspogift.identityandaccess.application.ApplicationServiceRegistry;import com.diaspogift.identityandaccess.application.ApplicationServiceTests;import com.diaspogift.identityandaccess.application.command.AssignUserToRoleCommand;import com.diaspogift.identityandaccess.domain.model.DomainRegistry;import com.diaspogift.identityandaccess.domain.model.access.Role;import com.diaspogift.identityandaccess.domain.model.identity.User;import org.junit.Test;import org.junit.runner.RunWith;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.test.context.junit4.SpringRunner;import org.springframework.transaction.annotation.Transactional;import static junit.framework.TestCase.assertFalse;import static org.junit.Assert.*;@RunWith(SpringRunner.class)@SpringBootTest@Transactionalpublic class AccessApplicationServiceTests extends ApplicationServiceTests {    public AccessApplicationServiceTests() {        super();    }    @Test    public void assignUserToRole() throws Exception {        User user = this.userAggregate();        DomainRegistry.userRepository().add(user);        Role role = this.roleAggregate();        DomainRegistry.roleRepository().add(role);        assertFalse(role.isInRole(user, DomainRegistry.groupMemberService()));        ApplicationServiceRegistry                .accessApplicationService()                .assignUserToRole(                        new AssignUserToRoleCommand(                                user.tenantId().id(),                                user.username(),                                role.name()));        assertTrue(role.isInRole(user, DomainRegistry.groupMemberService()));    }    @Test    public void isUserInRole() throws Exception {        User user = this.userAggregate();        DomainRegistry.userRepository().add(user);        Role role = this.roleAggregate();        DomainRegistry.roleRepository().add(role);        assertFalse(                ApplicationServiceRegistry                        .accessApplicationService()                        .isUserInRole(                                user.tenantId().id(),                                user.username(),                                role.name()));        ApplicationServiceRegistry                .accessApplicationService()                .assignUserToRole(                        new AssignUserToRoleCommand(                                user.tenantId().id(),                                user.username(),                                role.name()));        assertTrue(                ApplicationServiceRegistry                        .accessApplicationService()                        .isUserInRole(                                user.tenantId().id(),                                user.username(),                                role.name()));    }    @Test    public void userInRole() throws Exception {        User user = this.userAggregate();        DomainRegistry.userRepository().add(user);        Role role = this.roleAggregate();        DomainRegistry.roleRepository().add(role);        User userNotInRole =                ApplicationServiceRegistry                        .accessApplicationService()                        .userInRole(user.tenantId().id(), user.username(), role.name());        assertNull(userNotInRole);        ApplicationServiceRegistry                .accessApplicationService()                .assignUserToRole(                        new AssignUserToRoleCommand(                                user.tenantId().id(),                                user.username(),                                role.name()));        User userInRole =                ApplicationServiceRegistry                        .accessApplicationService()                        .userInRole(user.tenantId().id(), user.username(), role.name());        assertNotNull(userInRole);    }}