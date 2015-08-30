package com.alankehoe.persistence.services;

import com.alankehoe.persistence.client.AstyanaxClient;
import com.alankehoe.persistence.mappers.ColumnMapper;
import com.alankehoe.persistence.models.Entity;
import com.alankehoe.persistence.utils.AuditUtils;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
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
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public abstract class BaseService<T extends Entity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
    private static final int THREAD_POOL_SIZE = 10;

    private final ListeningExecutorService executorService = MoreExecutors
            .listeningDecorator(Executors.newFixedThreadPool(THREAD_POOL_SIZE));

    private Keyspace keyspace;
    private ColumnMapper<T> columnMapper;
    private ColumnFamily<UUID, String> columnFamily;

    protected BaseService(String keyspaceName, ColumnFamily<UUID, String> columnFamily, ColumnMapper<T> columnMapper) {
        Cluster cluster = AstyanaxClient.getCluster();

        try {
            this.keyspace = cluster.getKeyspace(keyspaceName);
            this.columnFamily = columnFamily;
            this.columnMapper = columnMapper;
        } catch (ConnectionException e) {
            LOGGER.error("Failed to connect to keyspace {}", keyspaceName);
        }
    }

    public List<T> findAll() throws ConnectionException {
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

    public ListenableFuture<List<T>> findAllAsync() throws ConnectionException {
        Callable<List<T>> asyncTask = this::findAll;

        return executorService.submit(asyncTask);
    }

    public T findByRef(UUID ref) throws ConnectionException {
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

    public ListenableFuture<T> findByRefAsync(UUID ref) throws ConnectionException {
        Callable<T> asyncTask = () -> findByRef(ref);

        return executorService.submit(asyncTask);
    }

    public T create(T entity) throws ConnectionException {
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

    public ListenableFuture<T> createAsync(T entity) throws ConnectionException {
        Callable<T> asyncTask = () -> create(entity);

        return executorService.submit(asyncTask);
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
