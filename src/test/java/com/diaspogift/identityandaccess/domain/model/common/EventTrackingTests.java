package com.diaspogift.identityandaccess.domain.model.common;

import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventTrackingTests {


    private List<Class<? extends DomainEvent>> handledEvents;
    private Map<String, String> handledNotifications;


    protected EventTrackingTests() {


        super();


    }

    protected void expectedEvent(Class<? extends DomainEvent> aDomainEventType) {
        this.expectedEvent(aDomainEventType, 1);
    }

    protected void expectedEvent(Class<? extends DomainEvent> aDomainEventType, int aTotal) {
        int count = 0;

        for (Class<? extends DomainEvent> type : this.handledEvents) {
            if (type == aDomainEventType) {
                ++count;
            }
        }

        if (count != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " " + aDomainEventType.getSimpleName() + " events, but handled "
                    + this.handledEvents.size() + " events: " + this.handledEvents);
        }
    }

    protected void expectedEvents(int anEventCount) {
        if (this.handledEvents.size() != anEventCount) {
            throw new IllegalStateException("Expected " + anEventCount + " events, but handled " + this.handledEvents.size()
                    + " events: " + this.handledEvents);

        }
    }

    @Before
    public void setUp() throws Exception {

        System.out.println(">>>>>>>>>>>>>>>>>>>>> (started) " + this.getClass().getSimpleName());


        DomainEventPublisher.instance().reset();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent aDomainEvent) {


                handledEvents.add(aDomainEvent.getClass());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });

        this.handledEvents = new ArrayList<Class<? extends DomainEvent>>();
        this.handledNotifications = new HashMap<String, String>();
    }

    @After
    public void tearDown() throws Exception {

        System.out.println("<<<<<<<<<<<<<<<<<<<< (done) " + this.getClass().getSimpleName());
    }

    public List<Class<? extends DomainEvent>> handledEvents() {
        return this.handledEvents;
    }

    public Map<String, String> handledNotifications() {
        return this.handledNotifications;
    }


}
