package ru.itmo.wp.model.repository;

public interface ThingRepository<T> {
    void save(T thing);
    T find(long id);
}