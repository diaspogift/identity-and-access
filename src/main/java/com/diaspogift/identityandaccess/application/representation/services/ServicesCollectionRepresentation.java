package com.diaspogift.identityandaccess.application.representation.services;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ServicesCollectionRepresentation extends ResourceSupport {

    private List<String> services;


    public ServicesCollectionRepresentation(List<String> services) {
        this.services = services;
    }


    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
