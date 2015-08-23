package com.alankehoe.persistence.services;

import com.alankehoe.persistence.models.Entity;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public abstract class BaseService<T extends Entity> {

    protected final Cluster cluster;
    
    public BaseService(Cluster cluster) {
        this.cluster = cluster;
    }

    public abstract List<T> list();

    public abstract T create(T model) throws ConnectionException;

    public void stampAuditDetailsForCreate(T model) {
        model.setRef(UUID.randomUUID());
        model.setCreatedAt(new DateTime());
        model.setUpdatedAt(new DateTime());
    }
}
