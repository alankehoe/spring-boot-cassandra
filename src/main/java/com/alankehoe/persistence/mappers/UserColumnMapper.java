package com.alankehoe.persistence.mappers;

import com.alankehoe.persistence.models.User;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.model.ColumnList;

public class UserColumnMapper extends ColumnMapper<User> {

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    public UserColumnMapper() {
        super(User.class);
    }
    
    @Override
    protected void insertColumns(ColumnListMutation<String> column, User entity) {
        column.putColumn(COLUMN_NAME, entity.getName());
        column.putColumn(COLUMN_EMAIL, entity.getEmail());
        column.putColumn(COLUMN_PASSWORD, entity.getPassword());
    }

    @Override
    protected User convertColumns(ColumnList<String> columns, User entity) {
        entity.setName(columns.getColumnByName(COLUMN_NAME).getStringValue());
        entity.setEmail(columns.getColumnByName(COLUMN_EMAIL).getStringValue());
        entity.setPassword(columns.getColumnByName(COLUMN_PASSWORD).getStringValue());
        
        return entity;
    }
}