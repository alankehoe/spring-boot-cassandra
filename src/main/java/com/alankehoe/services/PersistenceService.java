package com.alankehoe.services;

import java.util.List;

public interface PersistenceService<T> {
    public List<T> list();
    public T create(T entity);
}
