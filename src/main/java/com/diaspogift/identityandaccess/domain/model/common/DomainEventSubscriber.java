package com.diaspogift.identityandaccess.domain.model.common;

public interface DomainEventSubscriber<T> {

    void handleEvent(final T aDomainEvent);

    Class<T> subscribedToEventType();
}
