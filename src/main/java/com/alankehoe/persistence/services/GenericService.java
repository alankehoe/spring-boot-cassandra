package com.alankehoe.persistence.services;

import com.alankehoe.persistence.models.Entity;
import com.google.common.util.concurrent.ListenableFuture;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import java.util.List;
import java.util.UUID;

public interface GenericService<T extends Entity> {
    public List<T> findAll() throws ConnectionException;
    
    public ListenableFuture<List<T>> findAllAsync() throws ConnectionException;

    public T findByRef(UUID ref) throws ConnectionException;
    
    public ListenableFuture<T> findByRefAsync(UUID ref) throws ConnectionException;

    public T create(T model) throws ConnectionException;
}
