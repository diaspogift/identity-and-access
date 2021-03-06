package com.diaspogift.identityandaccess.application.access;

import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.application.representation.group.GroupCollectionRepresentation;
import com.diaspogift.identityandaccess.application.representation.user.UserCollectionRepresentation;
import com.diaspogift.identityandaccess.domain.model.DomainRegistry;
import com.diaspogift.identityandaccess.domain.model.access.AuthorizationService;
import com.diaspogift.identityandaccess.domain.model.access.Role;
import com.diaspogift.identityandaccess.domain.model.access.RoleDescriptor;
import com.diaspogift.identityandaccess.domain.model.access.RoleRepository;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static com.diaspogift.identityandaccess.domain.model.identity.Group.ROLE_GROUP_PREFIX;

@Service
public class AccessApplicationService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;


    @Transactional
    public void assignUsersToRole(AssignUsersToRoleCommand aCommand) throws DiaspoGiftRepositoryException {


        try {

            TenantId tenantId = new TenantId(aCommand.getTenantId());


            Collection<User> users = new HashSet<User>();

            List<String> usernames = aCommand.getUsernames();

            System.out.println("\n\n In assignUsersToRole usernames = " + usernames.toString() + "\n\n");


            for (String next : usernames) {

                User user =
                        this.userRepository()
                                .userWithUsername(
                                        tenantId,
                                        next);

                users.add(user);

            }


            System.out.println("\n\n In assignUsersToRole users.size = " + users.size() + "\n\n");


            if (!usernames.isEmpty()) {

                Role role =
                        this.roleRepository()
                                .roleNamed(
                                        tenantId,
                                        aCommand.getRoleName());

                if (role != null) {

                    for (User next : users) {


                        System.out.println("\n\n In assignUsersToRole in for loop next user = " + next.toString() + "\n\n");

                        role.assignUser(next);

                    }
                }
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }


    }


    ///TO TESTTTT
    @Transactional
    public void unassignUsersFromRole(UnassignUsersFromRoleCommand aCommand) throws DiaspoGiftRepositoryException {


        try {

            TenantId tenantId = new TenantId(aCommand.getTenantId());


            Collection<User> users = new HashSet<User>();

            List<String> usernames = aCommand.getUsernames();

            System.out.println("\n\n In unassignUsersFromRole usernames = " + usernames.toString() + "\n\n");


            for (String next : usernames) {

                User user =
                        this.userRepository()
                                .userWithUsername(
                                        tenantId,
                                        next);

                users.add(user);

            }


            System.out.println("\n\n In unassignUsersFromRole users.size = " + users.size() + "\n\n");


            if (!usernames.isEmpty()) {

                Role role =
                        this.roleRepository()
                                .roleNamed(
                                        tenantId,
                                        aCommand.getRoleName());

                if (role != null) {

                    for (User next : users) {


                        System.out.println("\n\n In unassignUsersFromRole in for loop next user = " + next.toString() + "\n\n");

                        role.unassignUser(next);

                    }
                }
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }


    }


    //TO TEST
    @Transactional
    public void assignGroupsToRole(AssignGroupsToRoleCommand aCommand) throws DiaspoGiftRepositoryException {


        try {

            TenantId tenantId = new TenantId(aCommand.getTenantId());


            Collection<Group> groups = new HashSet<Group>();

            List<String> groupNames = aCommand.getGroupNames();

            System.out.println("\n\n In assignGroupsToRole groupNames = " + groupNames.toString() + "\n\n");


            for (String next : groupNames) {

                Group group =
                        this.groupRepository()
                                .groupNamed(
                                        tenantId,
                                        next);

                groups.add(group);

            }

            System.out.println("\n\n In assignGroupsToRole groups.size = " + groups.size() + "\n\n");


            if (!groups.isEmpty()) {

                Role role =
                        this.roleRepository()
                                .roleNamed(
                                        tenantId,
                                        aCommand.getRoleName());

                if (role != null) {

                    for (Group next : groups) {


                        System.out.println("\n\n In assignGroupsToRole in for loop next group = " + next.toString() + "\n\n");

                        role.assignGroup(next, DomainRegistry.groupMemberService());

                    }
                }
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }


    }


    //TO TEST
    @Transactional
    public void unassignGroupsFromRole(UnassignGroupsFromRoleCommand aCommand) throws DiaspoGiftRepositoryException {


        try {

            TenantId tenantId = new TenantId(aCommand.getTenantId());


            Collection<Group> groups = new HashSet<Group>();

            List<String> groupNames = aCommand.getGroupNames();

            for (String next : groupNames) {

                Group group =
                        this.groupRepository()
                                .groupNamed(
                                        tenantId,
                                        next);

                groups.add(group);

            }


            if (!groups.isEmpty()) {

                Role role =
                        this.roleRepository()
                                .roleNamed(
                                        tenantId,
                                        aCommand.getRoleName());

                if (role != null) {

                    for (Group next : groups) {

                        role.unassignGroup(next);

                    }
                }
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }


    }


    @Transactional(readOnly = true)
    public boolean isUserInRole(String aTenantId, String aUsername, String aRoleName) throws DiaspoGiftRepositoryException {

        try {

            User user = this.userInRole(aTenantId, aUsername, aRoleName);

            return user != null;

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }
    }

    @Transactional
    public void provisionRole(ProvisionRoleCommand aCommand) throws DiaspoGiftRepositoryException {

        try {

            TenantId tenantId = new TenantId(aCommand.getTenantId());

            Tenant tenant = this.tenantRepository().tenantOfId(tenantId);

            Role role =
                    tenant.provisionRole(
                            aCommand.getRoleName(),
                            aCommand.getDescription(),
                            aCommand.isSupportsNesting());

            this.roleRepository().add(role);

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }
    }

    @Transactional(readOnly = true)
    public User userInRole(String aTenantId, String aUsername, String aRoleName) throws DiaspoGiftRepositoryException {

        try {

            User userInRole = null;

            TenantId tenantId = new TenantId(aTenantId);

            User user =
                    this.userRepository()
                            .userWithUsername(
                                    tenantId,
                                    aUsername);


            if (user != null) {
                Role role =
                        this.roleRepository()
                                .roleNamed(tenantId, aRoleName);


                if (role != null) {


                    if (role.isInRole(user, DomainRegistry.groupMemberService())) {


                        userInRole = user;


                    }
                }
            }

            return userInRole;

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }
    }


    //TO DO TEST
    @Transactional
    public Collection<Role> allRoles(String aTenantId) {

        TenantId tenantId = new TenantId(aTenantId);

        return this.roleRepository().allRoles(tenantId);
    }


    //TO DO TEST
    @Transactional
    public Role roleNamed(String aTenantId, String aName) {

        TenantId tenantId = new TenantId(aTenantId);

        return this.roleRepository().roleNamed(tenantId, aName);
    }

    //TO DO TEST
    @Transactional
    public void removeRoleNamed(String aTenantId, String aName) throws DiaspoGiftRepositoryException {


        try {
            TenantId tenantId = new TenantId(aTenantId);

            Role roleToRemove = this.roleRepository().roleNamed(tenantId, aName);

            this.roleRepository().remove(roleToRemove);

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());


        }

    }

    //TO DO TEST
    @Transactional(readOnly = true)
    public Collection<RoleDescriptor> allRolesForIdentifiedUser(String aTenantId, String aName) {

        return authorizationService.allRolesForIdentifiedUser(new TenantId(aTenantId), aName);

    }


    //TO DO TEST
    @Transactional(readOnly = true)
    public Collection<GroupDescriptor> allGroupsForIdentifiedUser(String aTenantId, String aUsername) {

        TenantId tenantId = new TenantId(aTenantId);

        return authorizationService.allGroupsForIdentifiedUser(tenantId, aUsername);

    }


    //TO DO TEST
    ///REALLY BAD NEEDS A READ MODEL
    @Transactional(readOnly = true)
    public GroupCollectionRepresentation getGroupsPlayingRole(String aTenantId, String roleName) {


        TenantId tenantId = new TenantId(aTenantId);

        Collection<Group> reslut = new HashSet<Group>();

        Role role = this.roleRepository().roleNamed(tenantId, roleName);

        Collection<Group> allGroups = this.groupRepository().allGroups(tenantId);

        for (Group next : allGroups) {


            System.out.println("\n\n GROUP ==== " + next.toString());

            if (DomainRegistry.groupMemberService().isMemberGroup(role.group(), next.toGroupMember())) {

                reslut.add(next);
            }
        }


        return new GroupCollectionRepresentation(reslut);

    }


    //TO DO TEST
    ///REALLY BAD NEEDS A READ MODEL
    @Transactional(readOnly = true)
    public GroupCollectionRepresentation getGroupsNotPlayingRole(String aTenantId, String roleName) {


        TenantId tenantId = new TenantId(aTenantId);

        Collection<Group> reslut = new HashSet<Group>();

        Role role = this.roleRepository().roleNamed(tenantId, roleName);

        Collection<Group> allGroups = this.groupRepository().allGroups(tenantId);

        for (Group next : allGroups) {

            if (!DomainRegistry.groupMemberService().isMemberGroup(role.group(), next.toGroupMember()) && !(next.name().contains(ROLE_GROUP_PREFIX))) {

                reslut.add(next);
            }
        }


        return new GroupCollectionRepresentation(reslut);

    }


    //TO DO TEST
    ///REALLY BAD NEEDS A READ MODEL
    @Transactional(readOnly = true)
    public UserCollectionRepresentation getUsersInRole(String aTenantId, String roleName) {


        TenantId tenantId = new TenantId(aTenantId);

        Collection<User> reslut = new HashSet<User>();

        Role role = this.roleRepository().roleNamed(tenantId, roleName);

        Collection<User> allUsers = this.userRepository().allUserFor(tenantId);

        for (User next : allUsers) {

            if (role.isInRole(next, DomainRegistry.groupMemberService())) {
                reslut.add(next);
            }
        }


        return new UserCollectionRepresentation(reslut);

    }


    @Transactional(readOnly = true)
    public UserCollectionRepresentation getNotUsersNotInRole(String aTenantId, String roleName) {


        TenantId tenantId = new TenantId(aTenantId);

        Collection<User> reslut = new HashSet<User>();

        Role role = this.roleRepository().roleNamed(tenantId, roleName);

        Collection<User> allUsers = this.userRepository().allUserFor(tenantId);

        for (User next : allUsers) {

            if (!role.isInRole(next, DomainRegistry.groupMemberService())) {
                reslut.add(next);
            }
        }


        return new UserCollectionRepresentation(reslut);

    }


    private GroupRepository groupRepository() {
        return this.groupRepository;
    }

    private RoleRepository roleRepository() {
        return this.roleRepository;
    }

    private TenantRepository tenantRepository() {
        return this.tenantRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }

}
