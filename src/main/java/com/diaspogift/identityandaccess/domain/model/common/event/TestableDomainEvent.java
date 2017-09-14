package com.diaspogift.identityandaccess.domain.model.common.event;

import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;

import java.util.Date;

public class TestableDomainEvent implements DomainEvent {

    private int eventVersion;
    private long id;
    private String name;
    private Date occurredOn;

    public TestableDomainEvent(long anId, String aName) {
        super();

        this.setEventVersion(1);
        this.setId(anId);
        this.setName(aName);
        this.setOccurredOn(new Date());
    }

    public int eventVersion() {
        return eventVersion;
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    private void setEventVersion(int anEventVersion) {
        this.eventVersion = anEventVersion;
    }

    private void setId(long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }
}
