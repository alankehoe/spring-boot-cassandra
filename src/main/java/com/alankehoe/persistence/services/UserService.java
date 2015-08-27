package com.alankehoe.persistence.services;

import com.alankehoe.persistence.mappers.UserColumnMapper;
import com.alankehoe.persistence.models.User;
import com.google.common.util.concurrent.ListenableFuture;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;

import java.util.List;
import java.util.UUID;

public class UserService extends BaseService<User> implements GenericService<User> {

    private static final ColumnFamily<UUID, String> CF_USERS = ColumnFamily
            .newColumnFamily("users", UUIDSerializer.get(), StringSerializer.get());
    
    public UserService(Cluster cluster, String keyspaceName) {
        super(cluster, keyspaceName);
        
        columnMapper = new UserColumnMapper();
    }

    @Override
    public List<User> findAll() throws ConnectionException {
        return findAll(CF_USERS);
    }

    @Override
    public ListenableFuture<List<User>> findAllAsync() throws ConnectionException {
        return findAllAsync(CF_USERS);
    }
    
    @Override
    public User findByRef(UUID ref) throws ConnectionException {
        return findByRef(ref, CF_USERS);
    }

    @Override
    public ListenableFuture<User> findByRefAsync(UUID ref) throws ConnectionException {
        return findByRefAsync(ref, CF_USERS);
    }

    @Override
    public User create(User user) throws ConnectionException {
        return create(user, CF_USERS);
    }
}
