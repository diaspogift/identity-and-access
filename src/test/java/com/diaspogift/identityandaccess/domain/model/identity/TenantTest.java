package com.diaspogift.identityandaccess.domain.model.identity;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantTest {

    @Test
    public void createTenantId(){

        String id = UUID.fromString(UUID.randomUUID().toString()).toString().toUpperCase();
        TenantId tenantId = new TenantId(id);
        assertNotNull(tenantId);
        assertEquals(id, tenantId.id());

    }
}
