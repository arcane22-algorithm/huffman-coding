package com.arcane222.huffmancoding.net.example.async.util;

import com.arcane222.huffmancoding.net.example.async.exception.PoolSizeException;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ThreadPool implements Executor {

    private static final int MIN_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = 200;

    private final ExecutorService pool;

    private ThreadPool() {
        this.pool = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory());
    }

    private ThreadPool(int size) {
        if (illegalPoolSize(size)) throw new PoolSizeException(size);

        this.pool = new ThreadPoolExecutor(size, size, 0L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory());
    }

    private ThreadPool(int defaultSize, int maxSize) {
        if (illegalPoolSize(defaultSize))
            throw new PoolSizeException(defaultSize);

        if (illegalPoolSize(maxSize))
            throw new PoolSizeException(maxSize);

        this.pool = new ThreadPoolExecutor(defaultSize, maxSize, 60L,
                TimeUnit.SECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory());
    }

    private boolean illegalPoolSize(int size) {
        return MIN_POOL_SIZE > size || MAX_POOL_SIZE < size;
    }

    public static ThreadPool ofDefaultPool() {
        return new ThreadPool();
    }

    public static ThreadPool ofFixedPool(int size) {
        return new ThreadPool(size);
    }

    public static ThreadPool ofCachedPool(int defaultSize, int maxSize) {
        return new ThreadPool(defaultSize, maxSize);
    }

    public void shutdown() {
        this.pool.shutdown();
    }

    public boolean isShutdown() {
        return pool.isShutdown();
    }

    public boolean isTerminated() {
        return pool.isTerminated();
    }

    @Override
    public void execute(Runnable task) {
        this.pool.execute(task);
    }

    public CompletableFuture<Void> executeWithFuture(Runnable task) {
        return CompletableFuture.runAsync(task, this.pool);
    }

    public <U> CompletableFuture<U> supplyTaskAsync(Supplier<U> task) {
        return CompletableFuture.supplyAsync(task, this.pool);
    }
}
