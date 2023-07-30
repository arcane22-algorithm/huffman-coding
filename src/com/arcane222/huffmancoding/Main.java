/**
 * @class HuffmanCoding
 * @author Lee Hong Jun (arcane222, hong3883@naver.com)
 * @description - Last modified 2022. 10. 01
 * - encoding(), decoding() 메소드를 통하여 txt 파일을 인코딩, 디코딩함.
 * - huffmanCoding().encoding(file) .txt 압축(인코딩) -> .bin 생성
 * - huffmanCoding().decoding(file) 생성된 .bin 디코딩 -> .txt 생성
 */

package com.arcane222.huffmancoding;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import com.arcane222.huffmancoding.v1.hc.HuffmanCoding;
import com.arcane222.huffmancoding.v1.nhc.DecodingJob;
import com.arcane222.huffmancoding.v1.nhc.EncodingJob;

public class Main {

    public static void main(String[] args) throws Exception {
        init(args);
    }

    public static void init(String... args) throws Exception {
        // Get program version from program argument
        String version = args[0];

        switch(version) {
            case "v1":
                execV1();
                break;

            case "v2":
                execV2();
                break;

            default:
                throw new RuntimeException("UndefinedVersionTypeException");
        }
    }

    /**
     * Execute version 1
     */
    public static void execV1() {
        long before = 0, after = 0;
        float execTime = 0;

        HuffmanCoding huffmanCoding = new HuffmanCoding();
        final String path = "./input/input.txt";

        // Execute encoding, measure execution time
        File inputFile = new File(path);

        before = System.nanoTime();
        huffmanCoding.encoding(inputFile);
        after = System.nanoTime();
        execTime += execTimeDump(before, after);

        // Execute decoding, measure execution time
        File compressedFile = new File("./input/input.bin");
        before = System.nanoTime();
        huffmanCoding.decoding(compressedFile);
        after = System.nanoTime();
        execTime += execTimeDump(before, after);

        System.out.println(String.format("Total Execution Time: %f (sec)", execTime));
    }

    /**
     * Execute version 2
     */
    public static void execV2() throws ExecutionException, InterruptedException {
        // todo-refactoring huffman-coding code (version2)
        EncodingJob encodingJob = EncodingJob.createJob("./input", "input", "./output", "output");
        CompletableFuture.runAsync(encodingJob).join();

        DecodingJob decodingJob = DecodingJob.createJob("./output", "output", "./output", "output");
        CompletableFuture.runAsync(decodingJob).join();
    }

    /**
     * @param before Measurement start time. (System.nanoTime())
     * @param after  Measurement end time (System.nanoTime())
     * @return Execution time (sec)
     */
    public static float execTimeDump(long before, long after) {
        float execTime = (after - before) * 0.000000001f;

        StringBuilder sb = new StringBuilder();
        sb.append("Execution Time: ");
        sb.append(execTime);
        sb.append("(sec)\n");

        System.out.println(sb);
        return execTime;
    }
}
