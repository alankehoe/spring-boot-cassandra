package com.alankehoe.persistence.utils;

import com.alankehoe.persistence.models.Entity;
import org.joda.time.DateTime;

import java.util.UUID;

public class AuditUtils {

    public static void stampAuditDetailsForCreate(Entity entity) {
        DateTime now = new DateTime();

        entity.setRef(UUID.randomUUID());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    protected void stampAuditDetailsForUpdate(Entity entity) {
        DateTime now = new DateTime();

        entity.setUpdatedAt(now);
    }
}
