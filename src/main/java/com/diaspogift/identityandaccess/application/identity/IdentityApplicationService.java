package com.diaspogift.identityandaccess.application.identity;


import com.diaspogift.identityandaccess.application.command.*;
import com.diaspogift.identityandaccess.application.representation.group.GroupMemberRepresentation;
import com.diaspogift.identityandaccess.domain.model.identity.*;
import com.diaspogift.identityandaccess.port.adapter.persistence.exception.DiaspoGiftRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class IdentityApplicationService {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private GroupMemberService groupMemberService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TenantProvisioningService tenantProvisioningService;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserRepository userRepository;

    public IdentityApplicationService() {
        super();
        // IdentityAccessEventProcessor.register();
    }

    @Transactional
    public void activateTenant(ActivateTenantCommand aCommand) throws DiaspoGiftRepositoryException {

        try {

            Tenant tenant = this.existingTenant(aCommand.getTenantId());

            tenant.activate();

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());

        }

    }

    @Transactional
    public void addGroupToGroup(AddGroupToGroupCommand aCommand) throws DiaspoGiftRepositoryException {

        try {

            Group parentGroup =
                    this.existingGroup(
                            aCommand.getTenantId(),
                            aCommand.getParentGroupName());

            Group childGroup =
                    this.existingGroup(
                            aCommand.getTenantId(),
                            aCommand.getChildGroupName());

            parentGroup.addGroup(childGroup, this.groupMemberService());

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }

    }

    @Transactional
    public void addUserToGroup(AddUserToGroupCommand aCommand) throws DiaspoGiftRepositoryException {


        try {

            Group group =
                    this.existingGroup(
                            aCommand.getTenantId(),
                            aCommand.getGroupName());

            User user =
                    this.existingUser(
                            aCommand.getTenantId(),
                            aCommand.getUsername());

            group.addUser(user);
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }

    }

    @Transactional(readOnly = true)
    public UserDescriptor authenticateUser(AuthenticateUserCommand aCommand) throws DiaspoGiftRepositoryException {

        try {

            UserDescriptor userDescriptor =
                    this.authenticationService()
                            .authenticate(
                                    new TenantId(aCommand.getTenantId()),
                                    aCommand.getUsername(),
                                    aCommand.getPassword());

            return userDescriptor;

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }

    }

    @Transactional
    public void deactivateTenant(DeactivateTenantCommand aCommand) throws DiaspoGiftRepositoryException {

        try {

            Tenant tenant = this.existingTenant(aCommand.getTenantId());

            tenant.deactivate();

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void changeUserContactInformation(ChangeContactInfoCommand aCommand) throws DiaspoGiftRepositoryException {

        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            this.internalChangeUserContactInformation(
                    user,
                    new ContactInformation(
                            new EmailAddress(aCommand.getEmailAddress()),
                            new PostalAddress(
                                    aCommand.getAddressStreetAddress(),
                                    aCommand.getAddressCity(),
                                    aCommand.getAddressStateProvince(),
                                    aCommand.getAddressPostalCode(),
                                    aCommand.getAddressCountryCode()),
                            new Telephone(aCommand.getPrimaryCountryCode(), aCommand.getPrimaryDialingCountryCode(), aCommand.getPrimaryTelephone()),
                            new Telephone(aCommand.getSecondaryCountryCode(), aCommand.getSecondaryDialingCountryCode(), aCommand.getSecondaryTelephone())));

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }


    @Transactional
    public void changeUserEmailAddress(ChangeEmailAddressCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            this.internalChangeUserContactInformation(
                    user,
                    user.person()
                            .contactInformation()
                            .changeEmailAddress(new EmailAddress(aCommand.getEmailAddress())));
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void changeUserPostalAddress(ChangePostalAddressCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            this.internalChangeUserContactInformation(
                    user,
                    user.person()
                            .contactInformation()
                            .changePostalAddress(
                                    new PostalAddress(
                                            aCommand.getAddressStreetAddress(),
                                            aCommand.getAddressCity(),
                                            aCommand.getAddressStateProvince(),
                                            aCommand.getAddressPostalCode(),
                                            aCommand.getAddressCountryCode())));

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void changeUserPrimaryTelephone(ChangePrimaryTelephoneCommand aCommand) throws DiaspoGiftRepositoryException {

        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            this.internalChangeUserContactInformation(
                    user,
                    user.person()
                            .contactInformation()
                            .changePrimaryTelephone(new Telephone(aCommand.getCountryCode(), aCommand.getDialingCountryCode(), aCommand.getTelephone())));

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void changeUserSecondaryTelephone(ChangeSecondaryTelephoneCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            this.internalChangeUserContactInformation(
                    user,
                    user.person()
                            .contactInformation()
                            .changeSecondaryTelephone(new Telephone(aCommand.getCountryCode(), aCommand.getDialingCountryCode(), aCommand.getTelephone())));
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void changeUserPassword(ChangeUserPasswordCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            user.changePassword(aCommand.getCurrentPassword(), aCommand.getChangedPassword());
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void changeUserPersonalName(ChangeUserPersonalNameCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            user.person().changeName(new FullName(aCommand.getFirstName(), aCommand.getLastName()));

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void defineUserEnablement(DefineUserEnablementCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

            user.defineEnablement(
                    new Enablement(
                            aCommand.isEnabled(),
                            aCommand.getStartDate(),
                            aCommand.getEndDate()));

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional(readOnly = true)
    public Group group(String aTenantId, String aGroupName) throws DiaspoGiftRepositoryException {
        try {
            Group group =
                    this.groupRepository()
                            .groupNamed(new TenantId(aTenantId), aGroupName);

            return group;

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional(readOnly = true)
    public boolean isGroupMember(String aTenantId, String aGroupName, String aUsername) throws DiaspoGiftRepositoryException {

        try {
            Group group =
                    this.existingGroup(
                            aTenantId,
                            aGroupName);

            User user =
                    this.existingUser(
                            aTenantId,
                            aUsername);

            return group.isMember(user, this.groupMemberService());

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public Group provisionGroup(ProvisionGroupCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            Tenant tenant = this.existingTenant(aCommand.getTenantId());

            Group group =
                    tenant.provisionGroup(
                            aCommand.getGroupName(),
                            aCommand.getDescription());

            this.groupRepository().add(group);

            return group;
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public Tenant provisionTenant(ProvisionTenantCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            return
                    this.tenantProvisioningService().provisionTenant(
                            aCommand.getTenantName(),
                            aCommand.getTenantDescription(),
                            new FullName(
                                    aCommand.getAdministorFirstName(),
                                    aCommand.getAdministorLastName()),
                            new EmailAddress(aCommand.getEmailAddress()),
                            new PostalAddress(
                                    aCommand.getAddressStateProvince(),
                                    aCommand.getAddressCity(),
                                    aCommand.getAddressStateProvince(),
                                    aCommand.getAddressPostalCode(),
                                    aCommand.getAddressCountryCode()),
                            new Telephone(aCommand.getPrimaryCountryCode(), aCommand.getPrimaryDialingCountryCode(), aCommand.getPrimaryTelephone()),
                            new Telephone(aCommand.getSecondaryCountryCode(), aCommand.getSecondaryDialingCountryCode(), aCommand.getSecondaryTelephone()));
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public User registerUser(RegisterUserCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            Tenant tenant = this.existingTenant(aCommand.getTenantId());


            //TODO Check nullssss
            //TODO Check nullssss
            //TODO Check nullssss

            RegistrationInvitation registrationInvitation = tenant.invitation(aCommand.getInvitationIdentifier());


            User user =
                    tenant.registerUser(
                            aCommand.getInvitationIdentifier(),
                            aCommand.getUsername(),
                            aCommand.getPassword(),
                            new Enablement(
                                    aCommand.isEnabled(),
                                    registrationInvitation.startingOn(),
                                    registrationInvitation.until()),
                            new Person(
                                    new FullName(aCommand.getFirstName(), aCommand.getLastName()),
                                    new ContactInformation(
                                            new EmailAddress(aCommand.getEmailAddress()),
                                            new PostalAddress(
                                                    aCommand.getAddressStateProvince(),
                                                    aCommand.getAddressCity(),
                                                    aCommand.getAddressStateProvince(),
                                                    aCommand.getAddressPostalCode(),
                                                    aCommand.getAddressCountryCode()),
                                            new Telephone(aCommand.getPrimaryCountryCode(), aCommand.getPrimaryDialingCountryCode(), aCommand.getPrimaryTelephone()),
                                            new Telephone(aCommand.getSecondaryCountryCode(), aCommand.getSecondaryDialingCountryCode(), aCommand.getSecondaryTelephone()))));

            if (user == null) {
                throw new IllegalStateException("User not registered.");
            }

            this.userRepository().add(user);

            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void removeGroupFromGroup(RemoveGroupFromGroupCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            Group parentGroup =
                    this.existingGroup(
                            aCommand.getTenantId(),
                            aCommand.getParentGroupName());

            Group childGroup =
                    this.existingGroup(
                            aCommand.getTenantId(),
                            aCommand.getChildGroupName());

            parentGroup.removeGroup(childGroup);

        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional
    public void removeUserFromGroup(RemoveUserFromGroupCommand aCommand) throws DiaspoGiftRepositoryException {
        try {
            Group group =
                    this.existingGroup(
                            aCommand.getTenantId(),
                            aCommand.getGroupName());

            User user =
                    this.existingUser(
                            aCommand.getTenantId(),
                            aCommand.getUsername());

            group.removeUser(user);
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }


    //TODO TESTTTTTTTTTT
    //TODO TESTTTTTTTTTT
    //TODO TESTTTTTTTTTT
    //TODO TESTTTTTTTTTT
    @Transactional
    public void removeGroupMembersFromGroup(RemoveGroupMembersFromGroupCommand aCommand) {

        try {


            Group parentGroup =
                    this.existingGroup(
                            aCommand.getTenantId(),
                            aCommand.getParentGroupName());


            Collection<Group> childGroups = new HashSet<Group>();
            Collection<User> childUsers = new HashSet<User>();


            for (GroupMemberRepresentation next : aCommand.getGroupMembers()) {


                if ("Group".equals(next.getType())) {

                    Group group =
                            this.existingGroup(
                                    aCommand.getTenantId(),
                                    next.getName());

                    childGroups.add(group);

                } else if ("User".equals(next.getType())) {

                    User user =
                            this.existingUser(
                                    aCommand.getTenantId(),
                                    next.getName());

                    childUsers.add(user);

                }

            }

            for (Group next : childGroups) {

                parentGroup.removeGroup(next);
            }
            for (User next : childUsers) {

                parentGroup.removeUser(next);
            }


        } catch (DiaspoGiftRepositoryException e) {
            e.printStackTrace();
        }


    }

    @Transactional(readOnly = true)
    public Tenant tenant(String aTenantId) throws DiaspoGiftRepositoryException {
        try {
            Tenant tenant =
                    this.tenantRepository()
                            .tenantOfId(new TenantId(aTenantId));

            return tenant;
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional(readOnly = true)
    public User user(String aTenantId, String aUsername) throws DiaspoGiftRepositoryException {
        try {
            User user =
                    this.userRepository()
                            .userWithUsername(
                                    new TenantId(aTenantId),
                                    aUsername);

            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DiaspoGiftRepositoryException(e.getMessage(), e, e.getClass().getSimpleName());
        }
    }

    @Transactional(readOnly = true)
    public UserDescriptor userDescriptor(String aTenantId, String aUsername) throws DiaspoGiftRepositoryException {


        UserDescriptor userDescriptor = null;

        User user = this.user(aTenantId, aUsername);

        if (user != null) {
            userDescriptor = user.userDescriptor();
        }

        return userDescriptor;
    }


    //TO DO TEST
    @Transactional
    public Collection<Tenant> allTenants() {
        return this.tenantRepository().allTenants();
    }


    //TO DO TEST
    @Transactional
    public Collection<Tenant> allTenants(Integer aFirst, Integer aLast) {
        return this.tenantRepository().allTenants(aFirst, aLast);
    }


    //TO DO TEST
    @Transactional
    public Collection<Group> allTenantGroups(String aTenantId) {

        TenantId tenantId = new TenantId(aTenantId);

        return this.groupRepository().allGroups(tenantId);
    }

    //TO DO TEST
    @Transactional
    public Collection<GroupMember> groupMembers(String aTenantId, String groupName) {

        TenantId tenantId = new TenantId(aTenantId);

        return this.groupRepository().groupMembers(tenantId, groupName);

    }

    //TO DO TEST
    @Transactional
    public Collection<User> allUserFor(String aTenantId) {

        TenantId tenantId = new TenantId(aTenantId);


        Collection<User> allUsers = this.userRepository().allUserFor(tenantId);


        return allUsers;

    }


    //TO DO TEST
    @Transactional
    public InvitationDescriptor offerRegistrationInvitation(OfferRegistrationInvitationCommand aCommand) {


        TenantId tenantId = new TenantId(aCommand.getTenantId());


        Tenant tenant = this.tenantRepository().tenantOfId(tenantId);

        RegistrationInvitation registrationInvitation = null;

        if (tenant != null) {

            registrationInvitation =
                    tenant.offerRegistrationInvitation(aCommand.getDescription(), new EmailAddress(aCommand.getEmail()), 1, "", "")
                            .startingOn(ZonedDateTime.parse(aCommand.getStartingOn()))
                            .until(ZonedDateTime.parse(aCommand.getUntil()));
        }

        return registrationInvitation == null ? null : registrationInvitation.toDescriptor();

    }

    //TO DO TEST
    @Transactional
    public void removeGroup(String aTenantId, String groupName) {

        TenantId tenantId = new TenantId(aTenantId);

        Group groupToremove = this.groupRepository().groupNamed(tenantId, groupName);

        this.groupRepository().remove(groupToremove);

    }


    //TO DO TEST
    @Transactional
    public ContactInformation userContactInformation(String aTenantId, String aUsername) {

        TenantId tenantId = new TenantId(aTenantId);

        System.out.println(" \n\n aTenantId ====  " + aTenantId + " aUsername ==== " + aUsername);
        System.out.println(" \n\n aTenantId ====  " + aTenantId + " aUsername ==== " + aUsername);
        System.out.println(" \n\n aTenantId ====  " + aTenantId + " aUsername ==== " + aUsername);

        ContactInformation contactInformation = this.userRepository().userContactInformation(tenantId, aUsername);


        System.out.println(" \n\n contactInformation ====  " + contactInformation + " contactInformation ==== " + contactInformation);

        return contactInformation;

    }

    //TO DO TEST
    @Transactional
    public EmailAddress userEmailAddress(String aTenantId, String aUsername) {

        TenantId tenantId = new TenantId(aTenantId);

        EmailAddress emailAddress = this.userRepository().userEmailAddress(tenantId, aUsername);

        return emailAddress;
    }

    //TO DO TEST
    @Transactional
    public FullName userPersonalName(String aTenantId, String aUsername) {

        TenantId tenantId = new TenantId(aTenantId);

        FullName fullName = this.userRepository().userPersonalName(tenantId, aUsername);

        return fullName;
    }

    //TO DO TEST
    @Transactional
    public boolean availabilityStatus(String aTenantId) {

        TenantId tenantId = new TenantId(aTenantId);


        return this.tenantRepository().availabilityStatus(tenantId);


    }


    //TO DO TEST
    @Transactional
    public Collection<GroupMember> notGroupMembers(String aTenantId, String groupName) {

        TenantId tenantId = new TenantId(aTenantId);

        Group group = this.groupRepository().groupNamed(tenantId, groupName);

        return this.groupMemberService().notInGoup(group);
    }


    private AuthenticationService authenticationService() {
        return this.authenticationService;
    }

    private Group existingGroup(String aTenantId, String aGroupName) throws DiaspoGiftRepositoryException {
        Group group = this.group(aTenantId, aGroupName);

        if (group == null) {
            throw new IllegalArgumentException(
                    "Group does not exist for: "
                            + aTenantId + " and: " + aGroupName);
        }

        return group;
    }

    private Tenant existingTenant(String aTenantId) throws DiaspoGiftRepositoryException {
        Tenant tenant = this.tenant(aTenantId);

        if (tenant == null) {
            throw new IllegalArgumentException(
                    "Tenant does not exist for: " + aTenantId);
        }

        return tenant;
    }

    private User existingUser(String aTenantId, String aUsername) throws DiaspoGiftRepositoryException {
        User user = this.user(aTenantId, aUsername);

        if (user == null) {
            throw new IllegalArgumentException(
                    "User does not exist for: "
                            + aTenantId + " and " + aUsername);
        }

        return user;
    }


    private void internalChangeUserContactInformation(
            User aUser,
            ContactInformation aContactInformation) {

        if (aUser == null) {
            throw new IllegalArgumentException("User must exist.");
        }

        aUser.person().changeContactInformation(aContactInformation);
    }


    private TenantProvisioningService tenantProvisioningService() {
        return this.tenantProvisioningService;
    }

    private TenantRepository tenantRepository() {
        return this.tenantRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }

    private GroupMemberService groupMemberService() {
        return this.groupMemberService;
    }

    private GroupRepository groupRepository() {
        return this.groupRepository;
    }

}
