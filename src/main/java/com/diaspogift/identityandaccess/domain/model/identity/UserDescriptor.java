package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.access.RoleDescriptor;
import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class UserDescriptor extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private String emailAddress;
    private TenantId tenantId;
    private String username;

    private Collection<RoleDescriptor> roleDescriptorList = new ArrayList<RoleDescriptor>();

    public UserDescriptor(TenantId aTenantId, String aUsername, String anEmailAddress, List<RoleDescriptor> aRoleDescriptorList) {
        super();

        this.setEmailAddress(anEmailAddress);
        this.setTenantId(aTenantId);
        this.setUsername(aUsername);
        this.setRoleDescriptorList(aRoleDescriptorList);
    }

    private UserDescriptor() {
        super();
    }

    public static UserDescriptor nullDescriptorInstance() {
        return new UserDescriptor();
    }

    public String emailAddress() {
        return this.emailAddress;
    }

    public boolean isNullDescriptor() {
        return this.emailAddress() == null || this.tenantId() == null || this.username() == null;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public String username() {
        return this.username;
    }


    public Collection<RoleDescriptor> roleDescriptorList() {
        return roleDescriptorList;
    }

    public void setRoleDescriptorList(Collection<RoleDescriptor> roleDescriptorList) {
        this.roleDescriptorList = roleDescriptorList;
    }

    private void setEmailAddress(String anEmailAddress) {
        this.assertArgumentNotEmpty(anEmailAddress, "Email address must be provided.");

        this.emailAddress = anEmailAddress;
    }

    private void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "TenantId must not be set as null.");

        this.tenantId = aTenantId;
    }

    private void setUsername(String aUsername) {
        this.assertArgumentNotEmpty(aUsername, "Username must not be set as null.");

        this.username = aUsername;
    }

    private void setRoleDescriptorList(List<RoleDescriptor> aRoleDescriptorList) {
        this.roleDescriptorList = aRoleDescriptorList;
    }


    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            UserDescriptor typedObject = (UserDescriptor) anObject;
            equalObjects =
                    this.emailAddress().equals(typedObject.emailAddress()) &&
                            this.tenantId().equals(typedObject.tenantId()) &&
                            this.username().equals(typedObject.username());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(9429 * 263)
                        + this.emailAddress().hashCode()
                        + this.tenantId().hashCode()
                        + this.username().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "UserDescriptor [emailAddress=" + emailAddress
                + ", tenantId=" + tenantId + ", username=" + username + "]";
    }

}
