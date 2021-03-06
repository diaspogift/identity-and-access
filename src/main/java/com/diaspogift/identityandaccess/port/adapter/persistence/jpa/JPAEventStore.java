package com.diaspogift.identityandaccess.port.adapter.persistence.jpa;


import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;
import com.diaspogift.identityandaccess.domain.model.common.event.EventSerializer;
import com.diaspogift.identityandaccess.domain.model.common.event.EventStore;
import com.diaspogift.identityandaccess.domain.model.common.event.StoredEvent;
import com.diaspogift.identityandaccess.domain.model.common.event.TestableDomainEvent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
public class JPAEventStore implements EventStore {

    private static final long START_ID = 789;


    @PersistenceContext
    private EntityManager entityManager;


    public JPAEventStore() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId, long aHighStoredEventId) {

        List<StoredEvent> storedEvents =
                this.entityManager().
                        createQuery("select storedEvent from StoredEvent as storedEvent " +
                                "where storedEvent.eventId between :lowStoredEventId and :highStoredEventId " +
                                "order by storedEvent.eventId", StoredEvent.class)
                        .setParameter("lowStoredEventId", aLowStoredEventId)
                        .setParameter("highStoredEventId", aHighStoredEventId)
                        .getResultList();

        return storedEvents;
    }

    @Override
    public List<StoredEvent> allStoredEventsSince(long aStoredEventId) {

        List<StoredEvent> storedEvents =
                this.entityManager().
                        createQuery("select storedEvent from StoredEvent as storedEvent " +
                                "where storedEvent.eventId > :storedEventId " +
                                "order by storedEvent.eventId", StoredEvent.class)
                        .setParameter("storedEventId", aStoredEventId)
                        .getResultList();

        return storedEvents;

    }

    @Override
    public StoredEvent append(DomainEvent aDomainEvent) {
        String eventSerialization =
                EventSerializer.instance().serialize(aDomainEvent);

        StoredEvent storedEvent =
                new StoredEvent(
                        aDomainEvent.getClass().getName(),
                        aDomainEvent.occurredOn(),
                        eventSerialization);

        this.entityManager().persist(storedEvent);

        return storedEvent;
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public long countStoredEvents() {
        Long count =
                this.entityManager().createQuery("select count(*) from StoredEvent", Long.class)
                        .getSingleResult();

        return count;
    }


    public EntityManager entityManager() {
        return this.entityManager;
    }


    public void init() {

        List<StoredEvent> storedEvents = new ArrayList<StoredEvent>();

        int numberOfStoredEvents =
                Calendar
                        .getInstance()
                        .get(Calendar.MILLISECOND) + 1;

        if (numberOfStoredEvents < 21) {
            numberOfStoredEvents = 21;
        }

        for (int idx = 0; idx < numberOfStoredEvents; ++idx) {
            StoredEvent storedEvent = this.newStoredEvent(START_ID + idx, idx + 1);

            this.entityManager().persist(storedEvent);
        }

    }

    private StoredEvent newStoredEvent(long domainEventId, long storedEventId) {
        EventSerializer serializer = EventSerializer.instance();

        DomainEvent event = new TestableDomainEvent(domainEventId, "name" + domainEventId);
        String serializedEvent = serializer.serialize(event);
        StoredEvent storedEvent = new StoredEvent(event.getClass().getName(), event.occurredOn(), serializedEvent);
        storedEvent.setEventId(storedEventId);

        return storedEvent;
    }
}

