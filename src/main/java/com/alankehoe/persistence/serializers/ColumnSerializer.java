package com.alankehoe.persistence.serializers;

import com.alankehoe.persistence.models.Entity;
import com.netflix.astyanax.ColumnListMutation;

public abstract class ColumnSerializer<T extends Entity> {

    protected abstract void insertColumns(ColumnListMutation<String> column, T entity);

    public void insert(ColumnListMutation<String> column, T entity) {
        column.putColumn("ref", entity.getRef());
        column.putColumn("created_at", entity.getCreatedAt().getMillis());
        column.putColumn("updated_at", entity.getUpdatedAt().getMillis());

        insertColumns(column, entity);
    }
}