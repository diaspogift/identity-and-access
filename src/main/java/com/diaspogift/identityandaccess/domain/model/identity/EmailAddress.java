//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.diaspogift.identityandaccess.domain.model.identity;


import com.diaspogift.identityandaccess.domain.model.common.AssertionConcern;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
public final class EmailAddress extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "email")
    private String address;

    public EmailAddress(String anAddress) {
        super();

        this.setAddress(anAddress);
    }

    public EmailAddress(EmailAddress anEmailAddress) {
        this(anEmailAddress.address());
    }

    protected EmailAddress() {
        super();
    }

    public String address() {
        return this.address;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            EmailAddress typedObject = (EmailAddress) anObject;
            equalObjects = this.address().equals(typedObject.address());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(17861 * 179)
                        + this.address().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "EmailAddress [address=" + address + "]";
    }

    private void setAddress(String anAddress) {
        this.assertArgumentNotEmpty(anAddress, "The email address is required.");
        this.assertArgumentLength(anAddress, 1, 100, "Email address must be 100 characters or less.");
        this.assertArgumentTrue(
                Pattern.matches("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", anAddress),
                "Email address format is invalid.");
        this.address = anAddress;
    }
}


/**
 * https://play.google.com/about/privacy-security/personal-sensitive/
 * http://www.lesnumeriques.com/mobilite/play-store-pas-regles-confidentialite-application-effacee-n60329.html
 * https://www.iubenda.com/blog/warning-google-play-developer-policy-violation-action-required-policy-issue/
 */