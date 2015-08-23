package com.alankehoe.persistence.models;

import org.joda.time.DateTime;

import java.util.UUID;

public abstract class Entity {
    
    private UUID ref;
    private DateTime createdAt;
    private DateTime updatedAt;

    public UUID getRef() {
        return ref;
    }

    public void setRef(UUID ref) {
        this.ref = ref;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
