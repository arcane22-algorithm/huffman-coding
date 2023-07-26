package com.arcane222.huffmancoding.v1.hc;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Encoder {

    public Map<Character, Node> getCharFreq(DataInputStream dis) throws IOException {
        StringBuilder buf = new StringBuilder();
        while (true) {
            int byteVal = dis.read();
            if (byteVal != -1)
                buf.append((char) byteVal);
            else break;
        }

        Map<Character, Node> map = new HashMap<>();
        for (int i = 0; i < buf.length(); i++) {
            char c = buf.charAt(i);
            if (map.containsKey(c))
                map.get(c).setFrequency(map.get(c).getFrequency() + 1);
            else
                map.put(c, new Node(c, 1));
        }

        return map;
    }
}
