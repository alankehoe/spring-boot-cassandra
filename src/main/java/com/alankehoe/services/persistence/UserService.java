package com.alankehoe.services.persistence;

import com.alankehoe.models.User;
import com.alankehoe.services.PersistenceService;
import com.google.common.collect.Lists;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class UserService extends BaseService implements PersistenceService<User> {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    public static ColumnFamily<UUID, String> CF_USERS = ColumnFamily
            .newColumnFamily("users", UUIDSerializer.get(), StringSerializer.get());

    private static final String INSERT_STATEMENT = "INSERT INTO users (ref, first_name, last_name, age, email) VALUES (?, ?, ?, ?, ?);";

    @Override
    public List<User> list() {
        List<User> users = Lists.newArrayList();
        IntStream.range(0, 100).forEach((i -> users.add(mockUser())));
        return users;
    }

    @Override
    public User create(User user) {
        Keyspace keyspace = astyanaxClient.getAstyanaxClient();

        try {
            keyspace.prepareQuery(CF_USERS)
                    .withCql(INSERT_STATEMENT)
                    .asPreparedStatement()
                    .withUUIDValue(UUID.randomUUID())
                    .withStringValue(user.getFirstName())
                    .withStringValue(user.getLastName())
                    .withIntegerValue(user.getAge())
                    .withStringValue(user.getEmail())
                    .execute();
        } catch (ConnectionException e) {
            System.out.println(e);
            LOGGER.error("Failed to persist user {} {}", user.getFirstName(), user.getLastName());
        }

        return user;
    }

    private User mockUser() {
        User user = new User();
        user.setRef(UUID.randomUUID());
        user.setFirstName("alan");
        user.setLastName("kehoe");
        user.setAge(23);
        return user;
    }
}
