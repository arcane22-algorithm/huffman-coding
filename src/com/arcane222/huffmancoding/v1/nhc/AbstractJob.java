package com.arcane222.huffmancoding.v1.nhc;



import java.util.UUID;

public abstract class AbstractJob implements Runnable {

    private UUID jobId;

    public AbstractJob() {
        jobId = UUID.randomUUID();
    }

    public String execTime(long beginTimeNano) {
        return String.format("%.5f", (System.nanoTime() - beginTimeNano) * 0.000000001f);
    }

    public double execTimeDouble(long beginTimeNano) {
        return (System.nanoTime() - beginTimeNano) * 0.000000001f;
    }


    public UUID getJobId() {
        return jobId;
    }
}
