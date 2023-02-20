package com.arcane222.huffmancoding.v1;

public class HuffmanUtils {
    public static String zeroPadding(String binaryString, int fullSize) {
        int length = binaryString.length();
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < fullSize - length; i++)
            buffer.append(0);
        buffer.append(binaryString);

        return buffer.toString();
    }
}
