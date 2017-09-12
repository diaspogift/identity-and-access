package com.diaspogift.identityandaccess;

import com.diaspogift.identityandaccess.domain.model.identity.Group;
import com.diaspogift.identityandaccess.domain.model.identity.GroupRepository;
import com.diaspogift.identityandaccess.domain.model.identity.TenantId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Transactional
public class IdentityAndAccessApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext ctx = SpringApplication.run(IdentityAndAccessApplication.class, args);
        GroupRepository groupRepository = ctx.getBean(GroupRepository.class);


        Group group = new Group(
                new TenantId("FA381C64-445B-4D3F-83B7-21E3CBA95D2A"),
                "NAME",
                "Role backing group for: ROLE NAME");

        groupRepository.add(group);


    }


}
