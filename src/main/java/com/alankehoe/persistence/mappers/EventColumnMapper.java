package com.alankehoe.persistence.mappers;

import com.alankehoe.persistence.models.Event;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.model.ColumnList;

public class EventColumnMapper extends ColumnMapper<Event> {

    public static final String COLUMN_PAYLOAD = "payload";

    public EventColumnMapper() {
        super(Event.class);
    }
    
    @Override
    protected void insertColumns(ColumnListMutation<String> column, Event event) {
        column.putColumn(COLUMN_PAYLOAD, event.getPayload());
    }

    @Override
    protected Event convertColumns(ColumnList<String> columns, Event event) {
        event.setPayload(columns.getColumnByName(COLUMN_PAYLOAD).getStringValue());
        
        return event;
    }
}