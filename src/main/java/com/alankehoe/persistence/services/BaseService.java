package com.alankehoe.persistence.services;

import com.alankehoe.persistence.models.Entity;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public abstract class BaseService<T extends Entity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    protected Cluster cluster = null;
    protected Keyspace keyspace = null;

    protected BaseService(Cluster cluster, String keyspaceName) {
        try {
            this.cluster = cluster;
            this.keyspace = cluster.getKeyspace(keyspaceName);
        } catch (ConnectionException e) {
            LOGGER.error("Failed to connect to cassandra for keyspace {}", keyspaceName);
        }
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
