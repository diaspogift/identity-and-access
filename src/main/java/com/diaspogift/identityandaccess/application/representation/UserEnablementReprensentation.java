package com.diaspogift.identityandaccess.application.representation;

import org.springframework.hateoas.ResourceSupport;

public class UserEnablementReprensentation extends ResourceSupport {

    private boolean enabled;
    private String startDate;
    private String endDate;

    public UserEnablementReprensentation() {
        super();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}


