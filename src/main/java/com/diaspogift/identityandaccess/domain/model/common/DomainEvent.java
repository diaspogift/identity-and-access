package com.diaspogift.identityandaccess.domain.model.common;

import java.util.Date;

public interface DomainEvent {

    public int eventVersion();

    public Date occurredOn();
}
