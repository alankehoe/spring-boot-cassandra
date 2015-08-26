package com.alankehoe.persistence.services;

import com.alankehoe.persistence.models.Entity;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import java.util.List;
import java.util.UUID;

public interface GenericService<T extends Entity> {
    public List<T> findAll() throws ConnectionException;

    public T findByRef(UUID ref) throws ConnectionException;

    public T create(T model) throws ConnectionException;
}
