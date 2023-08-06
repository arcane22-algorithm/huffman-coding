package com.arcane222.huffmancoding.net.example.async.core.server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractUniquePool<K, V> {

    private final Map<K, V> pool;

    public AbstractUniquePool(boolean concurrency) {
        this.pool = concurrency ? new ConcurrentHashMap<>() : new HashMap<>();
    }

    protected boolean add(K key, V val) {
        if (checkExist(key))
            return false;

        this.pool.put(key, val);
        return checkExist(key);
    }

    protected boolean remove(K key, V val) {
        if (!checkExist(key))
            return false;

        this.pool.remove(key);
        return !checkExist(key);
    }

    protected Optional<V> get(K key) {
        if(!checkExist(key))
            return Optional.empty();

        return Optional.ofNullable(this.pool.get(key));
    }

    protected boolean checkExist(K key) {
        return this.pool.containsKey(key);
    }

    protected Collection<V> values() {
        return this.pool.values();
    }

    protected int size() {
        return this.pool.size();
    }
}
