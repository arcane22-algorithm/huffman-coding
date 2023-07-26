package com.arcane222.huffmancoding.v1.hc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Decoder {

    public Map<String, Character> decodingTable(DataInputStream dis) throws IOException {
        Map<String, Character> decodingTable = new HashMap<>();
        int charCount = dis.read();

        for (int i = 0; i < charCount; i++) {
            char c = (char) dis.read();
            int length = dis.read();
            String code;
            if (length <= Byte.SIZE)
                code = Integer.toBinaryString(dis.read());
            else if (length <= (Byte.SIZE << 1))
                code = Integer.toBinaryString(dis.readShort());
            else
                code = Integer.toBinaryString(dis.readInt());
            code = HuffmanUtils.zeroPadding(code, length);

            decodingTable.put(code, c);
        }

        return decodingTable;
    }
}
