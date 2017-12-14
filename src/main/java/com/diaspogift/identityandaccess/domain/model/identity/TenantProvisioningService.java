package com.diaspogift.identityandaccess.domain.model.identity;import com.diaspogift.identityandaccess.domain.model.access.Role;import com.diaspogift.identityandaccess.domain.model.access.RoleRepository;import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;@Servicepublic class TenantProvisioningService {    public static final String ADMINISTRATOR = "ADMINISTRATOR";    public static final String ADMIN_USERNAME = "admin";    public static final String DEFAULT = "Default";    public static final String ADMINISTRATOR_DESCRIPTION = "administrator";    @Autowired    private RoleRepository roleRepository;    @Autowired    private TenantRepository tenantRepository;    @Autowired    private UserRepository userRepository;    public Tenant provisionTenant(String aTenantName, String aTenantDescription, FullName anAdministorName, EmailAddress anEmailAddress, PostalAddress aPostalAddress, Telephone aPrimaryTelephone, Telephone aSecondaryTelephone) {        try {            Tenant tenant = new Tenant(                    this.tenantRepository().nextIdentity(),                    aTenantName,                    aTenantDescription,                    true); // must be active to register admin            this.tenantRepository().add(tenant);            this.registerAdministratorFor(                    tenant,                    anAdministorName,                    anEmailAddress,                    aPostalAddress,                    aPrimaryTelephone,                    aSecondaryTelephone);            DomainEventPublisher                    .instance()                    .publish(new TenantProvisioned(                            tenant.tenantId()));            return tenant;        } catch (Throwable t) {            throw new IllegalStateException("Cannot provision tenant because: " + t.getMessage());        }    }    private void registerAdministratorFor(            Tenant aTenant,            FullName anAdministorName,            EmailAddress anEmailAddress,            PostalAddress aPostalAddress,            Telephone aPrimaryTelephone,            Telephone aSecondaryTelephone) {        RegistrationInvitation invitation =                aTenant.offerRegistrationInvitation("init").openEnded();      /*  String strongPassword =                DomainRegistry                        .passwordService()                        .generateStrongPassword();*/        String strongPassword = "password";        System.out.println("\n\n HERE THE GENERATED PASSWORD FOR TENANT  " + aTenant.tenantId() + " == " + strongPassword + "\n\n");        User admin =                aTenant.registerUser(                        invitation.invitationId(),                        ADMIN_USERNAME,                        strongPassword,                        Enablement.indefiniteEnablement(),                        new Person(                                anAdministorName,                                new ContactInformation(                                        anEmailAddress,                                        aPostalAddress,                                        aPrimaryTelephone,                                        aSecondaryTelephone)));        aTenant.withdrawInvitation(invitation.invitationId());        this.userRepository().add(admin);        Role adminRole =                aTenant.provisionRole(                        ADMINISTRATOR,                        DEFAULT + " " + aTenant.name() + " " + ADMINISTRATOR_DESCRIPTION + ".");        adminRole.assignUser(admin);        this.roleRepository().add(adminRole);        DomainEventPublisher.instance().publish(                new TenantAdministratorRegistered(                        aTenant.tenantId(),                        aTenant.name(),                        anAdministorName,                        anEmailAddress,                        admin.username(),                        strongPassword));    }    private RoleRepository roleRepository() {        return this.roleRepository;    }    private TenantRepository tenantRepository() {        return this.tenantRepository;    }    private UserRepository userRepository() {        return this.userRepository;    }}