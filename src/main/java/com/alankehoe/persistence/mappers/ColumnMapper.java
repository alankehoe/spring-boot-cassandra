package com.alankehoe.persistence.mappers;

import com.alankehoe.persistence.models.Entity;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.model.ColumnList;
import org.joda.time.DateTime;

public abstract class ColumnMapper<T extends Entity> {

    public static final String COLUMN_REF = "ref";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    protected Class<T> genericSuperClass = null;

    public ColumnMapper(Class<T> genericSuperClass) {
        this.genericSuperClass = genericSuperClass;
    }

    protected abstract void insertColumns(ColumnListMutation<String> column, T entity);

    protected abstract T convertColumns(ColumnList<String> columns, T entity);

    public void insert(ColumnListMutation<String> column, T entity) {
        column.putColumn(COLUMN_REF, entity.getRef());
        column.putColumn(COLUMN_CREATED_AT, entity.getCreatedAt().getMillis());
        column.putColumn(COLUMN_UPDATED_AT, entity.getUpdatedAt().getMillis());

        insertColumns(column, entity);
    }

    public T convert(ColumnList<String> columns) {
        T entity = getEmptyEntity();

        entity.setRef(columns.getColumnByName(COLUMN_REF).getUUIDValue());
        entity.setCreatedAt(new DateTime(columns.getColumnByName(COLUMN_CREATED_AT).getLongValue()));
        entity.setUpdatedAt(new DateTime(columns.getColumnByName(COLUMN_UPDATED_AT).getLongValue()));

        return convertColumns(columns, entity);
    }

    private T getEmptyEntity() {
        try {
            return genericSuperClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}