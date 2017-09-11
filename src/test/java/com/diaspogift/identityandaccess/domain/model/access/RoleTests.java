package com.diaspogift.identityandaccess.domain.model.access;


import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.IdentityAndAccessTest;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventSubscriber;
import com.diaspogift.identityandaccess.domain.model.identity.Group;
import com.diaspogift.identityandaccess.domain.model.identity.Tenant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoleTests extends IdentityAndAccessTest {

    public RoleTests() {
        super();
    }

    @Test
    public void cretateRoleThatSupportNesting(){

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<RoleProvisioned>() {


            @Override
            public void handleEvent(RoleProvisioned aDomainEvent) {
                roleProvisionedHandled = true;
            }

            @Override
            public Class<RoleProvisioned> subscribedToEventType() {
                return RoleProvisioned.class;
            }
        });
        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION,true);
        assertTrue(role.supportsNesting());
        DomainRegistry.roleRepository().add(role);
        Role savedRole = DomainRegistry.roleRepository().roleNamed(activeTenant.tenantId(), role.name());
        assertEquals(role, savedRole);
        assertTrue(this.isroleProvisionedHandled());
    }


    @Test
    public void cretateRoleThatDoesNotSupportNesting(){

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<RoleProvisioned>() {


            @Override
            public void handleEvent(RoleProvisioned aDomainEvent) {
                roleProvisionedHandled = true;
            }

            @Override
            public Class<RoleProvisioned> subscribedToEventType() {
                return RoleProvisioned.class;
            }
        });
        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION);
        assertFalse(role.supportsNesting());
        DomainRegistry.roleRepository().add(role);
        Role savedRole = DomainRegistry.roleRepository().roleNamed(activeTenant.tenantId(), role.name());
        assertEquals(role, savedRole);
        assertTrue(this.isroleProvisionedHandled());
    }


    @Test
    public void assignGroup(){
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupAssignedToRole>() {


            @Override
            public void handleEvent(GroupAssignedToRole aDomainEvent) {
                groupAssignedToRoleHandled = true;
            }

            @Override
            public Class<GroupAssignedToRole> subscribedToEventType() {
                return GroupAssignedToRole.class;
            }
        });
        Tenant activeTenant = this.actifTenantAggregate();
        Role role = activeTenant.provisionRole(FIXTURE_ROLE_NAME, FIXTURE_ROLE_DESCRIPTION);
        DomainRegistry.roleRepository().add(role);
        Group group = activeTenant.provisionGroup(FIXTURE_GROUP_NAME, FIXTURE_GROUP_DESCRIPTION);
        role.assignGroup(group, DomainRegistry.groupMemberService());
        assertTrue(this.isgroupAssignedToRoleHandled());



    }



}
