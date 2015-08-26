package com.alankehoe.persistence.mappers;

import com.alankehoe.persistence.models.Entity;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.model.ColumnList;
import org.joda.time.DateTime;

public abstract class ColumnMapper<T extends Entity> {

    protected Class<T> genericSuperClass = null;
    
    public ColumnMapper(Class<T> genericSuperClass) {
        this.genericSuperClass = genericSuperClass;
    }
    
    protected abstract void insertColumns(ColumnListMutation<String> column, T entity);
    protected abstract T convertColumns(ColumnList<String> columns, T entity);

    public void insert(ColumnListMutation<String> column, T entity) {
        column.putColumn("ref", entity.getRef());
        column.putColumn("created_at", entity.getCreatedAt().getMillis());
        column.putColumn("updated_at", entity.getUpdatedAt().getMillis());

        insertColumns(column, entity);
    }
    
    public T convert(ColumnList<String> columns) {
        T entity = getEmptyEntity();
        
        entity.setRef(columns.getColumnByName("ref").getUUIDValue());
        entity.setCreatedAt(new DateTime(columns.getColumnByName("created_at").getLongValue()));
        entity.setUpdatedAt(new DateTime(columns.getColumnByName("updated_at").getLongValue()));
        
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