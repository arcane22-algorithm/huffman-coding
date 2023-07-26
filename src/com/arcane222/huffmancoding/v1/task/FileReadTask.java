package com.arcane222.huffmancoding.v1.task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReadTask<T extends StringBuilder> extends AbstractTask<T> {

    private static final int DEFAULT_BUFFER_SIZE = 1 << 13;

    private final boolean useDirect;

    private final ByteBuffer buffer;
    private final int idx;
    private final String path;

    public FileReadTask(String path, int idx) {
        this(path, false, DEFAULT_BUFFER_SIZE, idx);
    }

    public FileReadTask(String path, boolean useDirect, int idx) {
        this(path, useDirect, DEFAULT_BUFFER_SIZE, idx);
    }

    public FileReadTask(String path, int bufferSize, int idx) {
        this(path, false, bufferSize, idx);
    }

    public FileReadTask(String path, boolean useDirect, int bufferSize, int idx) {
        this.useDirect = useDirect;
        this.buffer = useDirect ? ByteBuffer.allocateDirect(bufferSize)
                : ByteBuffer.allocate(bufferSize);
        this.idx = idx;
        this.path = path;
    }

    @Override
    public T get() {
        try (FileInputStream fis = new FileInputStream(path)) {
            FileChannel channel = fis.getChannel();
            channel.read(buffer, (long) buffer.capacity() * idx);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < buffer.limit(); i++) {
            builder.append((char) buffer.get());
        }

        return (T) builder;
    }
}
