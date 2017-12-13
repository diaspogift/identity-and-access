package com.diaspogift.identityandaccess.domain.model.common;

import com.diaspogift.identityandaccess.domain.model.common.event.TestableDomainEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainEventPublisherTests {

    private boolean anotherEventHandled;
    private boolean eventHandled;

    public DomainEventPublisherTests() {
        super();
    }

    @Test
    public void domainEventPublisherPublish() throws Exception {
        DomainEventPublisher.instance().reset();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TestableDomainEvent>() {
            @Override
            public void handleEvent(TestableDomainEvent aDomainEvent) {
                assertEquals(100L, aDomainEvent.id());
                assertEquals("test", aDomainEvent.name());
                eventHandled = true;
            }

            @Override
            public Class<TestableDomainEvent> subscribedToEventType() {
                return TestableDomainEvent.class;
            }
        });

        assertFalse(this.eventHandled);

        DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

        assertTrue(this.eventHandled);
    }


    public void domainEventPublisherBlocked() throws Exception {
        DomainEventPublisher.instance().reset();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TestableDomainEvent>() {
            @Override
            public void handleEvent(TestableDomainEvent aDomainEvent) {
                assertEquals(100L, aDomainEvent.id());
                assertEquals("test", aDomainEvent.name());
                eventHandled = true;
                // attempt nested publish, which should not work
                DomainEventPublisher.instance().publish(new AnotherTestableDomainEvent(1000.0));
            }

            @Override
            public Class<TestableDomainEvent> subscribedToEventType() {
                return TestableDomainEvent.class;
            }
        });

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<AnotherTestableDomainEvent>() {
            @Override
            public void handleEvent(AnotherTestableDomainEvent aDomainEvent) {
                // should never be reached due to blocked publisher
                assertEquals(1000.0, aDomainEvent.value(), 0);
                anotherEventHandled = true;
            }

            @Override
            public Class<AnotherTestableDomainEvent> subscribedToEventType() {
                return AnotherTestableDomainEvent.class;
            }
        });

        assertFalse(this.eventHandled);
        assertFalse(this.anotherEventHandled);

        DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

        assertTrue(this.eventHandled);
        assertFalse(this.anotherEventHandled);
    }
}