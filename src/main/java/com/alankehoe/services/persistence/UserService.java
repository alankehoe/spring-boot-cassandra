package com.alankehoe.services.persistence;

import com.alankehoe.models.User;
import com.alankehoe.services.PersistenceService;
import com.google.common.collect.Lists;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class UserService extends BaseService implements PersistenceService<User> {

    public static ColumnFamily<UUID, String> CF_USERS = ColumnFamily
            .newColumnFamily("users", UUIDSerializer.get(), StringSerializer.get());

    @Override
    public List<User> list() {
        astyanaxClient.getAstyanaxClient();

        List<User> users = Lists.newArrayList();
        IntStream.range(0, 100).forEach((i -> users.add(mockUser())));
        return users;
    }

    private User mockUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("alan");
        user.setLastName("kehoe");
        user.setAge(23);
        return user;
    }
}
