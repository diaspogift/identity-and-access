package com.diaspogift.identityandaccess.domain.model.common;

import java.util.Date;

public interface DomainEvent {

    int eventVersion();

    Date occurredOn();
}
