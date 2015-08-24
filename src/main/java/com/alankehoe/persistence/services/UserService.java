package com.alankehoe.persistence.services;

import com.alankehoe.persistence.models.User;
import com.alankehoe.persistence.serializers.UserColumnSerializer;
import com.google.common.collect.Lists;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class UserService extends BaseService<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static ColumnFamily<UUID, String> CF_USERS = ColumnFamily
            .newColumnFamily("users", UUIDSerializer.get(), StringSerializer.get());

    public UserService(Cluster cluster, String keyspaceName) {
        super(cluster, keyspaceName);
    }

    @Override
    public List<User> list() {
        List<User> users = Lists.newArrayList();
        IntStream.range(0, 100).forEach((i -> users.add(mockUser())));
        return users;
    }

    @Override
    public User create(User user) throws ConnectionException {
        stampAuditDetailsForCreate(user);

        try {
            MutationBatch mutationBatch = keyspace.prepareMutationBatch();

            ColumnListMutation<String> userMutation = mutationBatch.withRow(CF_USERS, user.getRef());
            insertColumns(user, userMutation);

            mutationBatch.execute();

            return user;
        } catch (ConnectionException e) {
            LOGGER.error("failed to persist user {}", user.getRef());
            throw e;
        }
    }

    protected void insertColumns(User user, ColumnListMutation<String> columnListMutation) {
        new UserColumnSerializer().insert(columnListMutation, user);
    }
    
    private User mockUser() {
        User user = new User();
        user.setRef(UUID.randomUUID());
        user.setName("alan kehoe");
        user.setEmail("alankehoe111@gmail.com");
        user.setCreatedAt(new DateTime());
        user.setUpdatedAt(new DateTime());
        return user;
    }
}
