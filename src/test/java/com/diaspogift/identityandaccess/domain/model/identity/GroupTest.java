package com.diaspogift.identityandaccess.domain.model.identity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupTest {

    private String description;
    private Set<GroupMember> groupMembers;
    private String name;
    private TenantId tenantId;
    private Group group;

    @Before
    public void init() {
        description = "First group";
        name = "Group of DDD Developers";
        tenantId = new TenantId(UUID.randomUUID().toString().toUpperCase());
        groupMembers = new HashSet<>();

        group = new Group(tenantId, name, description);


        String description1 = "First group nested";
        String name1 = "Group of DDD Developers In PHP";
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());


    }

    @Test
    public void createGroup() {
        assertNotNull(group);
    }


    @After
    public void reset() {

    }
}
