package com.alankehoe.persistence.services;

import com.alankehoe.persistence.mappers.EventColumnMapper;
import com.alankehoe.persistence.models.Event;
import com.google.common.util.concurrent.ListenableFuture;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;

import java.util.List;
import java.util.UUID;

public class EventService extends BaseService<Event> {

    private static final ColumnFamily<UUID, String> CF_EVENTS = ColumnFamily
            .newColumnFamily("events", UUIDSerializer.get(), StringSerializer.get());
    
    public EventService(String keyspaceName) {
        super(keyspaceName, CF_EVENTS, new EventColumnMapper());
    }
}
