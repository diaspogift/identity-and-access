package com.diaspogift.identityandaccess.domain.model.common;

import com.diaspogift.identityandaccess.domain.model.common.event.EventSerializer;
import com.diaspogift.identityandaccess.domain.model.common.event.TestableDomainEvent;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

public class EventSerializerTests {

    public EventSerializerTests() {
        super();
    }

    @Test
    public void tefaultFormat() throws Exception {
        EventSerializer serializer = EventSerializer.instance();

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        assertTrue(serializedEvent.contains("\"id\""));
        assertTrue(serializedEvent.contains("\"occurredOn\""));
        assertFalse(serializedEvent.contains("\n"));
        assertTrue(serializedEvent.contains("null"));
    }

    @Test
    public void compact() throws Exception {
        EventSerializer serializer = new EventSerializer(true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        assertTrue(serializedEvent.contains("\"id\""));
        assertTrue(serializedEvent.contains("\"occurredOn\""));
        assertFalse(serializedEvent.contains("\n"));
        assertFalse(serializedEvent.contains("null"));
    }

    public void prettyAndCompact() throws Exception {
        EventSerializer serializer = new EventSerializer(true, true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        assertTrue(serializedEvent.contains("\"id\""));
        assertTrue(serializedEvent.contains("\"occurredOn\""));
        assertTrue(serializedEvent.contains("\n"));
        assertFalse(serializedEvent.contains("null"));
    }

    @Test
    public void deserializeDefault() throws Exception {
        EventSerializer serializer = EventSerializer.instance();

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertTrue(serializedEvent.contains("null"));
        assertEquals(1, event.id());
        assertEquals(null, event.name());
        assertNotNull(event.occurredOn());
    }

    @Test
    public void deserializeCompactNotNull() throws Exception {
        EventSerializer serializer = new EventSerializer(true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, "test"));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertFalse(serializedEvent.contains("null"));
        assertTrue(serializedEvent.contains("\"test\""));
        assertEquals(1, event.id());
        assertEquals("test", event.name());
        assertNotNull(event.occurredOn());
    }

    @Test
    public void deserializeCompactNull() throws Exception {
        EventSerializer serializer = new EventSerializer(true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertFalse(serializedEvent.contains("null"));
        assertEquals(1, event.id());
        assertEquals(null, event.name());
        assertNotNull(event.occurredOn());
    }

    @Test
    public void deserializePrettyAndCompactNull() throws Exception {
        EventSerializer serializer = new EventSerializer(true, true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertFalse(serializedEvent.contains("null"));
        assertTrue(serializedEvent.contains("\n"));
        assertEquals(1, event.id());
        assertEquals(null, event.name());
        assertNotNull(event.occurredOn());
    }
}