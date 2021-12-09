/**
 * @class HuffmanCoding
 * @author Lee Hong Jun (arcane22, hong3883@naver.com)
 * @description
 *  - Last modified 2021. 12. 09
 *  - encoding(), decoding() 메소드를 통하여 txt 파일을 인코딩, 디코딩함.
 *  - huffmanCoding().encoding(file) .txt 압축(인코딩) -> .bin 생성
 *  - huffmanCoding().decoding(file) 생성된 .bin 디코딩 -> .txt 생성
 */

package com.arcane22.huffman_coding;

import java.io.File;
import com.arcane22.huffman_coding.v1.HuffmanCoding;

public class Main {

    public static void main(String[] args) throws Exception{
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
                throw new Exception("UndefinedVersionTypeException");
        }
    }

    /**
     * Execute version 1
     */
    public static void execV1() {
        long before = 0, after = 0;
        float execTime = 0;

        HuffmanCoding huffmanCoding = new HuffmanCoding();
        File inputFile = new File("./input/input.txt");
        File compressedFile = new File("./input/input.bin");

        // Execute encoding, measure execution time
        before = System.nanoTime();
        huffmanCoding.encoding(inputFile);
        after = System.nanoTime();
        execTime += execTimeDump(before, after);

        // Execute decoding, measure execution time
        before = System.nanoTime();
        huffmanCoding.decoding(compressedFile);
        after = System.nanoTime();
        execTime += execTimeDump(before, after);

        System.out.println(String.format("Total Execution Time: %f (sec)", execTime));
    }

    /**
     * Execute version 2
     */
    public static void execV2() {
        // todo-refactoring huffman-coding code (version2)
    }

    /**
     * @param before Measurement start time. (System.nanoTime())
     * @param after Measurement end time (System.nanoTime())
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
