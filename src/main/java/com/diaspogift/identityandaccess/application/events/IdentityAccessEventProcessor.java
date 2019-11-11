package com.diaspogift.identityandaccess.application.events;

import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventPublisher;
import com.diaspogift.identityandaccess.domain.model.common.DomainEventSubscriber;
import com.diaspogift.identityandaccess.domain.model.common.event.EventStore;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IdentityAccessEventProcessor {

    @Autowired
    private EventStore eventStore;

    /**
     * Constructs my default state.
     */
    public IdentityAccessEventProcessor() {
        super();
    }

    /**
     * Registers a IdentityAccessEventProcessor to listen
     * and forward all domain events to external subscribers.
     * This factory method is provided in the case where
     * Spring AOP wiring is not desired.
     */

    public static void register() {
        (new IdentityAccessEventProcessor()).listen();
    }

    /**
     * Listens for all domain events and stores them.
     */
    @Before("execution(* com.diaspogift.identityandaccess.application.identity.*.*(..)) || " +
            "execution(* com.diaspogift.identityandaccess.application.access.*.*(..))")
    public void listen() {

        DomainEventPublisher
                .instance()
                .subscribe(new DomainEventSubscriber<DomainEvent>() {

                    public void handleEvent(DomainEvent aDomainEvent) {
                        store(aDomainEvent);
                    }

                    public Class<DomainEvent> subscribedToEventType() {
                        return DomainEvent.class; // all domain events
                    }
                });
    }

    /**
     * Stores aDomainEvent to the event store.
     *
     * @param aDomainEvent the DomainEvent to store
     */
    private void store(DomainEvent aDomainEvent) {
        this.eventStore().append(aDomainEvent);
    }

    /**
     * Answers my EventStore.
     *
     * @return EventStore
     */
    private EventStore eventStore() {
        return this.eventStore;
    }
}

