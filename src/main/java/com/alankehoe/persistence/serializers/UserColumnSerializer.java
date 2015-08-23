package com.alankehoe.persistence.serializers;

import com.alankehoe.persistence.models.User;
import com.netflix.astyanax.ColumnListMutation;

public class UserColumnSerializer extends ColumnSerializer<User> {

    protected void insertColumns(ColumnListMutation<String> column, User entity) {
        column.putColumn("name", entity.getName());
        column.putColumn("email", entity.getEmail());
        column.putColumn("password", entity.getPassword());
    }
}