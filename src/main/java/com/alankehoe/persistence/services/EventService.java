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

public class EventService extends BaseService<Event> implements GenericService<Event> {

    private static final ColumnFamily<UUID, String> CF_EVENTS = ColumnFamily
            .newColumnFamily("events", UUIDSerializer.get(), StringSerializer.get());
    
    public EventService(Cluster cluster, String keyspaceName) {
        super(cluster, keyspaceName);
        
        columnMapper = new EventColumnMapper();
    }

    @Override
    public List<Event> findAll() throws ConnectionException {
        return findAll(CF_EVENTS);
    }

    @Override
    public ListenableFuture<List<Event>> findAllAsync() throws ConnectionException {
        return findAllAsync(CF_EVENTS);
    }
    
    @Override
    public Event findByRef(UUID ref) throws ConnectionException {
        return findByRef(ref, CF_EVENTS);
    }

    @Override
    public ListenableFuture<Event> findByRefAsync(UUID ref) throws ConnectionException {
        return findByRefAsync(ref, CF_EVENTS);
    }

    @Override
    public Event create(Event event) throws ConnectionException {
        return create(event, CF_EVENTS);
    }
}
