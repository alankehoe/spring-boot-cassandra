package com.alankehoe.persistence.services;

import com.alankehoe.persistence.utils.AuditUtils;
import com.alankehoe.persistence.mappers.ColumnMapper;
import com.alankehoe.persistence.models.Entity;
import com.google.common.collect.Lists;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.query.ColumnFamilyQuery;
import com.netflix.astyanax.query.RowQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public abstract class BaseService<T extends Entity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
    
    protected Cluster cluster = null;
    protected Keyspace keyspace = null;
    protected ColumnMapper<T> columnMapper = null;

    protected BaseService(Cluster cluster, String keyspaceName) {
        this.cluster = cluster;
        
        try {
            this.keyspace = cluster.getKeyspace(keyspaceName);
        } catch (ConnectionException e) {
            LOGGER.error("Failed to connect to keyspace {}", keyspaceName);
        }
    }
    
    public List<T> findAll(ColumnFamily<UUID, String> columnFamily) throws ConnectionException {
        Rows<UUID, String> rows;
        try {
            rows = keyspace.prepareQuery(columnFamily)
                    .getAllRows()
                    .execute()
                    .getResult();
        } catch (ConnectionException e) {
            LOGGER.error("failed to find all entities");
            throw e;
        }

        return convertAllRows(rows);
    }

    public T findByRef(UUID ref, ColumnFamily<UUID, String> columnFamily) throws ConnectionException {
        try {
            ColumnFamilyQuery<UUID, String> usersColumnFamily = keyspace.prepareQuery(columnFamily);
            RowQuery<UUID, String> rowQuery = usersColumnFamily.getKey(ref);
            OperationResult<ColumnList<String>> operationResult = rowQuery.execute();
            return columnMapper.convert(operationResult.getResult());
        } catch (ConnectionException e) {
            LOGGER.error("failed to find entity with ref {}", ref);
            throw e;
        }
    }

    public T create(T entity, ColumnFamily<UUID, String> columnFamily) throws ConnectionException {
        AuditUtils.stampAuditDetailsForCreate(entity);

        try {
            MutationBatch mutationBatch = keyspace.prepareMutationBatch();

            ColumnListMutation<String> userMutation = mutationBatch.withRow(columnFamily, entity.getRef());
            columnMapper.insert(userMutation, entity);

            mutationBatch.execute();

            return entity;
        } catch (ConnectionException e) {
            LOGGER.error("failed to persist entity with ref {}", entity.getRef());
            throw e;
        }
    }

    private List<T> convertAllRows(Rows<UUID, String> rows) {
        List<T> entities = Lists.newArrayList();

        for (Row<UUID, String> row : rows) {
            T entity = columnMapper.convert(row.getColumns());
            entities.add(entity);
        }

        return entities;
    }
}
