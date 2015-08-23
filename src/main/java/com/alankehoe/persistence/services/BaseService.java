package com.alankehoe.persistence.services;

import com.alankehoe.persistence.models.Entity;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public abstract class BaseService<T extends Entity> {

    protected final Cluster cluster;
    protected final String keyspaceName;

    protected BaseService(Cluster cluster, String keyspaceName) {
        this.cluster = cluster;
        this.keyspaceName = keyspaceName;
    }

    public abstract List<T> list();

    public abstract T create(T model) throws ConnectionException;

    protected void stampAuditDetailsForCreate(T model) {
        DateTime now = new DateTime();
        
        model.setRef(UUID.randomUUID());
        model.setCreatedAt(now);
        model.setUpdatedAt(now);
    }

    protected void stampAuditDetailsForUpdate(T model) {
        DateTime now = new DateTime();
        
        model.setUpdatedAt(now);
    }
}
