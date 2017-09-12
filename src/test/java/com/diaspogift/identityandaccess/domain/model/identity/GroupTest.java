package com.diaspogift.identityandaccess.domain.model.identity;

import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventSubscriber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.*;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GroupTest {


    private String description;
    private Set<GroupMember> groupMembers;
    private String name;
    private TenantId tenantId;
    private Group group;


    @Autowired
    private EncryptionService      encryptionService;

    private  String                id;
    private  FullName              fullName;
    private  ContactInformation    contactInformation;
    private Calendar               calendier;
    private Date                   now;
    private  Date                  afterTomorow;
    private  Person                person;
    private  User                  user;


    @Autowired
    private GroupMemberService groupMemberService;



    @Before
    public void init(){
        description = "First group";
        name        = "Group of DDD Developers";
        id = UUID.randomUUID().toString().toUpperCase();
        tenantId = new TenantId(id);
        //groupMembers = new HashSet<>();

        group = new Group(tenantId, name, description);

        //
        //tenantId = new TenantId(id);
        fullName = new FullName("Nkalla Ehawe", "Didier Junior");
        contactInformation = new ContactInformation(
                new EmailAddress("email@yahoo.fr"),
                new PostalAddress("Street address", "Street city", "State province","Postal code","US"),
                new Telephone("CMR","00237","691178154"),
                new Telephone("CMR","00237","669262656")
        );

        calendier = Calendar.getInstance();
        calendier.add(Calendar.DAY_OF_YEAR, 2);
        afterTomorow = calendier.getTime();
        now = new Date();
        person = new Person(tenantId, fullName, contactInformation);
        user = new User(tenantId,
                "username@gmail.com",
                "secretSTRENGTH1234",
                new Enablement(true, now, afterTomorow),
                person
        );


    }

    @Test
    public void createGroup(){
        assertNotNull(group);
        assertEquals(tenantId, group.tenantId());
        assertEquals(description, group.description());
        assertEquals(name, group.name());
        assertEquals(0, group.groupMembers().size());
    }

    @Test
    public void addGroupToGroup(){
        String description1 = "First group nested";
        String name1        = "Group of DDD Developers In PHP";
        Group nestGroup = new Group(tenantId, name1, description1);

        group.addGroup(nestGroup, groupMemberService);
        assertEquals(1, group.groupMembers().size());



        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = nestGroup.toGroupMember();
        for (GroupMember groupMember:group.groupMembers()){
            if (groupMember.equals(groupMemberExpected)){
                foundGroupMember = groupMember;
                break;
            }
        }

        assertNotNull(foundGroupMember);
        assertEquals(GroupMemberType.Group, foundGroupMember.type());

        Group foundGroup = new Group(tenantId, foundGroupMember.name(), description1);
        assertEquals(nestGroup, foundGroup);
        assertTrue(foundGroupMember.isGroup());

    }

    @Test(expected = IllegalArgumentException.class)
    public void addGroupToGroupWithWrongTenantId(){
        String description1 = "First group nested";
        String name1        = "Group of DDD Developers In PHP";
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group nestGroup = new Group(tenantId1, name1, description1);

        group.addGroup(nestGroup, groupMemberService);

    }

    @Test
    public void addGroupToGroupWithGoodRicorsion(){
        String description1 = "First group nested1";
        String name1        = "Group of DDD Developers In PHP";
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group1 = new Group(tenantId, name1, description1);

        String description2 = "First group nested2";
        String name2        = "Group of DDD Developers In PHP Object";
        TenantId tenantId2 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group2 = new Group(tenantId, name2, description2);

        String description3 = "First group nested3";
        String name3        = "Group of DDD Developers In PHP Plugin";
        TenantId tenantId3 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group3 = new Group(tenantId, name3, description3);


        String description4 = "First group nested";
        String name4        = "Group of DDD Developers In PHP Library";
        TenantId tenantId4 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group4 = new Group(tenantId, name4, description4);

        DomainRegistry.groupRepository().add(group);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);


        group1.addGroup(group2, groupMemberService);
        group2.addGroup(group3, groupMemberService);
        group2.addGroup(group4, groupMemberService);
        group.addGroup(group1, groupMemberService);

        assertEquals(true, groupMemberService.isMemberGroup(group, group4.toGroupMember()));
        assertEquals(true, groupMemberService.isMemberGroup(group, group3.toGroupMember()));
        assertEquals(true, groupMemberService.isMemberGroup(group, group2.toGroupMember()));
        assertEquals(false, groupMemberService.isMemberGroup(group3, group4.toGroupMember()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void addGroupToGroupWithBadRicorsion(){
        String description1 = "First group nested1";
        String name1        = "Group of DDD Developers In PHP";
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group1 = new Group(tenantId, name1, description1);

        String description2 = "First group nested2";
        String name2        = "Group of DDD Developers In PHP Object";
        TenantId tenantId2 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group2 = new Group(tenantId, name2, description2);

        String description3 = "First group nested3";
        String name3        = "Group of DDD Developers In PHP Plugin";
        TenantId tenantId3 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group3 = new Group(tenantId, name3, description3);


        String description4 = "First group nested";
        String name4        = "Group of DDD Developers In PHP Library";
        TenantId tenantId4 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group4 = new Group(tenantId, name4, description4);

        DomainRegistry.groupRepository().add(group);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);


        group1.addGroup(group2, groupMemberService);
        group2.addGroup(group3, groupMemberService);
        group2.addGroup(group4, groupMemberService);
        group.addGroup(group1, groupMemberService);

        group4.addGroup(group, groupMemberService);

        /*assertEquals(true, groupMemberService.isMemberGroup(group, group4.toGroupMember()));
        assertEquals(true, groupMemberService.isMemberGroup(group, group3.toGroupMember()));
        assertEquals(true, groupMemberService.isMemberGroup(group, group2.toGroupMember()));
        assertEquals(false, groupMemberService.isMemberGroup(group3, group4.toGroupMember()));*/
    }


    @Test
    public void removeGroup(){
        String description1 = "First group nested1";
        String name1        = "Group of DDD Developers In PHP";
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group1 = new Group(tenantId, name1, description1);

        String description2 = "First group nested2";
        String name2        = "Group of DDD Developers In PHP Object";
        TenantId tenantId2 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group2 = new Group(tenantId, name2, description2);

        String description3 = "First group nested3";
        String name3        = "Group of DDD Developers In PHP Plugin";
        TenantId tenantId3 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group3 = new Group(tenantId, name3, description3);


        String description4 = "First group nested";
        String name4        = "Group of DDD Developers In PHP Library";
        TenantId tenantId4 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group4 = new Group(tenantId, name4, description4);

        DomainRegistry.groupRepository().add(group);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);


        group1.addGroup(group2, groupMemberService);
        group2.addGroup(group3, groupMemberService);
        group2.addGroup(group4, groupMemberService);
        group.addGroup(group1, groupMemberService);


        group2.removeGroup(group3);

        assertEquals(1, group2.groupMembers().size());

        assertEquals(false, groupMemberService.isMemberGroup(group2, group3.toGroupMember()));

        /*assertEquals(true, groupMemberService.isMemberGroup(group, group3.toGroupMember()));
        assertEquals(true, groupMemberService.isMemberGroup(group, group2.toGroupMember()));
        assertEquals(false, groupMemberService.isMemberGroup(group3, group4.toGroupMember()));*/
    }


    @Test
    public void addUser(){
        group.addUser(user);

        assertEquals(1, group.groupMembers().size());



        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = user.toGroupMember();
        for (GroupMember groupMember:group.groupMembers()){
            if (groupMember.equals(groupMemberExpected)){
                foundGroupMember = groupMember;
                break;
            }
        }

        assertNotNull(foundGroupMember);
        assertEquals(GroupMemberType.User, foundGroupMember.type());
        assertEquals(true, foundGroupMember.isUser());


    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToGroupWithWrongTenantId(){
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        user = new User(tenantId1,
                "username.bad@gmail.com",
                "Bad.secretSTRENGTH1234",
                new Enablement(true, now, afterTomorow),
                person
        );
        group.addUser(user);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addNoInvalidUserToGroup(){
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        user = new User(tenantId,
                "username.bad@gmail.com",
                "Bad.secretSTRENGTH1234",
                new Enablement(false, now, afterTomorow),
                person
        );
        group.addUser(user);
    }

    @Test
    public void removeUser(){
        group.addUser(user);

        group.removeUser(user);
        assertEquals(0, group.groupMembers().size());

        assertEquals(false, group.isMember(user, groupMemberService));


    }

    private boolean isDone;

    /**
     * Domain events
     */


    @Test
    public void addGroupToGroupEvent(){
        String description1 = "First group nested";
        String name1        = "Group of DDD Developers In PHP";
        Group nestGroup = new Group(tenantId, name1, description1);
        isDone = false;

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupAdded>() {

            @Override
            public void handleEvent(GroupGroupAdded aDomainEvent) {
                isDone = true;
                //System.out.println("\n\n\naDomainEvent + " + aDomainEvent + "\n\n\n");
            }

            @Override
            public Class<GroupGroupAdded> subscribedToEventType() {
                return GroupGroupAdded.class;
            }
        });

        group.addGroup(nestGroup, groupMemberService);
        assertEquals(1, group.groupMembers().size());



        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = nestGroup.toGroupMember();
        for (GroupMember groupMember:group.groupMembers()){
            if (groupMember.equals(groupMemberExpected)){
                foundGroupMember = groupMember;
                break;
            }
        }

        assertNotNull(foundGroupMember);
        assertEquals(GroupMemberType.Group, foundGroupMember.type());

        Group foundGroup = new Group(tenantId, foundGroupMember.name(), description1);
        assertEquals(nestGroup, foundGroup);
        assertTrue(foundGroupMember.isGroup());

        assertTrue(isDone);

    }


    @Test
    public void addUserEvent(){
        isDone = false;
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupUserAdded>() {
            @Override
            public void handleEvent(GroupUserAdded aDomainEvent) {
                isDone = true;
                //System.out.println("\n\n\naDomainEvent + " + aDomainEvent + "\n\n\n");
            }

            @Override
            public Class<GroupUserAdded> subscribedToEventType() {
                return GroupUserAdded.class;
            }
        });

        group.addUser(user);

        assertEquals(1, group.groupMembers().size());



        GroupMember foundGroupMember = null;
        GroupMember groupMemberExpected = user.toGroupMember();
        for (GroupMember groupMember:group.groupMembers()){
            if (groupMember.equals(groupMemberExpected)){
                foundGroupMember = groupMember;
                break;
            }
        }

        assertNotNull(foundGroupMember);
        assertEquals(GroupMemberType.User, foundGroupMember.type());
        assertEquals(true, foundGroupMember.isUser());
        assertTrue(isDone);

    }


    @Test
    public void removeGroupEvent(){
        isDone = false;
        String description1 = "First group nested1";
        String name1        = "Group of DDD Developers In PHP";
        TenantId tenantId1 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group1 = new Group(tenantId, name1, description1);

        String description2 = "First group nested2";
        String name2        = "Group of DDD Developers In PHP Object";
        TenantId tenantId2 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group2 = new Group(tenantId, name2, description2);

        String description3 = "First group nested3";
        String name3        = "Group of DDD Developers In PHP Plugin";
        TenantId tenantId3 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group3 = new Group(tenantId, name3, description3);


        String description4 = "First group nested";
        String name4        = "Group of DDD Developers In PHP Library";
        TenantId tenantId4 = new TenantId(UUID.randomUUID().toString().toUpperCase());
        //Set<GroupMember> groupMembers1 = new HashSet<>();
        Group group4 = new Group(tenantId, name4, description4);

        DomainRegistry.groupRepository().add(group);
        DomainRegistry.groupRepository().add(group1);
        DomainRegistry.groupRepository().add(group2);
        DomainRegistry.groupRepository().add(group3);
        DomainRegistry.groupRepository().add(group4);


        group1.addGroup(group2, groupMemberService);
        group2.addGroup(group3, groupMemberService);
        group2.addGroup(group4, groupMemberService);
        group.addGroup(group1, groupMemberService);

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupRemoved>() {
            @Override
            public void handleEvent(GroupGroupRemoved aDomainEvent) {
                isDone = true;
                //System.out.println("\n\n\naDomainEvent + " + aDomainEvent + "\n\n\n");
            }

            @Override
            public Class<GroupGroupRemoved> subscribedToEventType() {
                return GroupGroupRemoved.class;
            }
        });

        group2.removeGroup(group3);

        assertEquals(1, group2.groupMembers().size());

        assertEquals(false, groupMemberService.isMemberGroup(group2, group3.toGroupMember()));
        assertTrue(isDone);

    }


    @Test
    public void removeUserEvent(){
        isDone = false;
        group.addUser(user);

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupUserRemoved>() {
            @Override
            public void handleEvent(GroupUserRemoved aDomainEvent) {
                isDone = true;
                //System.out.println("\n\n\naDomainEvent + " + aDomainEvent + "\n\n\n");
            }

            @Override
            public Class<GroupUserRemoved> subscribedToEventType() {
                return GroupUserRemoved.class;
            }
        });

        group.removeUser(user);
        assertEquals(0, group.groupMembers().size());

        assertEquals(false, group.isMember(user, groupMemberService));
        assertTrue(isDone);

    }
    @After
    public void reset(){

    }
}
