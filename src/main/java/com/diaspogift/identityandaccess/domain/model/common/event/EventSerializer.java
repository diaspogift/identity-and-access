package com.diaspogift.identityandaccess.domain.model.common.event;import com.diaspogift.identityandaccess.domain.model.common.DomainEvent;import com.diaspogift.identityandaccess.domain.model.common.serializer.AbstractSerializer;public class EventSerializer extends AbstractSerializer {    private static EventSerializer eventSerializer;    public EventSerializer(boolean isCompact) {        this(false, isCompact);    }    public EventSerializer(boolean isPretty, boolean isCompact) {        super(isPretty, isCompact);    }    private EventSerializer() {        this(false, false);    }    public static synchronized EventSerializer instance() {        if (EventSerializer.eventSerializer == null) {            EventSerializer.eventSerializer = new EventSerializer();        }        return EventSerializer.eventSerializer;    }    public String serialize(DomainEvent aDomainEvent) {        String serialization = this.gson().toJson(aDomainEvent);        return serialization;    }    public <T extends DomainEvent> T deserialize(String aSerialization, final Class<T> aType) {        T domainEvent = this.gson().fromJson(aSerialization, aType);        return domainEvent;    }}