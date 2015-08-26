package com.alankehoe.persistence.mappers;

import com.alankehoe.persistence.models.User;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.model.ColumnList;

public class UserColumnMapper extends ColumnMapper<User> {
    
    public UserColumnMapper() {
        super(User.class);
    }
    
    @Override
    protected void insertColumns(ColumnListMutation<String> column, User entity) {
        column.putColumn("name", entity.getName());
        column.putColumn("email", entity.getEmail());
        column.putColumn("password", entity.getPassword());
    }

    @Override
    protected User convertColumns(ColumnList<String> columns, User entity) {
        entity.setName(columns.getColumnByName("name").getStringValue());
        entity.setEmail(columns.getColumnByName("email").getStringValue());
        entity.setPassword(columns.getColumnByName("password").getStringValue());
        
        return entity;
    }
}