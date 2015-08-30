package com.alankehoe.persistence.models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        return new EqualsBuilder()
                .append(ref, entity.ref)
                .append(createdAt, entity.createdAt)
                .append(updatedAt, entity.updatedAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(ref)
                .append(createdAt)
                .append(updatedAt)
                .toHashCode();
    }
}
