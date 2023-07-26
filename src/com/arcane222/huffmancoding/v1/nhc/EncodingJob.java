package com.arcane222.huffmancoding.v1.nhc;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;


public class EncodingJob extends HuffmanJob {

    private double totalExecTime;

    public EncodingJob(String inputPath, String inputName, String outputPath, String outputName) {
        super(inputPath, inputName, outputPath, outputName);
    }

    private StringBuilder readData(DataInputStream dis) throws IOException {
        var currTime = System.nanoTime();

        StringBuilder buffer = new StringBuilder();
        while (true) {
            int byteValue = dis.read();
            if (byteValue != -1)
                buffer.append((char) byteValue);
            else break;
        }
        System.out.println(getJobId() + " / EncodingJob - Read Data End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);
//        log.info("[{}] EncodingJob - Read Data End [{} sec]",
//                getJobId(), execTime(currTime));

        return buffer;
    }


    private StringBuilder readData2(FileInputStream fis) throws IOException {
        var currTime = System.nanoTime();

        ByteBuffer buf = ByteBuffer.allocateDirect(BUFFER_SIZE);
        final StringBuilder builder = new StringBuilder();

        int bufferPointer = 0;
        while (true) {
            int bytesRead = fis.getChannel().read(buf);
            if (bytesRead <= 0)
                break;

            while (bufferPointer < bytesRead) {
                builder.append((char) buf.get(bufferPointer++));
            }
            buf.clear();
            bufferPointer = 0;
        }

        System.out.println(getJobId() + " / EncodingJob - Read Data End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);

        return builder;
    }

    private PriorityQueue<Node> frequency(StringBuilder buffer) {
        var currTime = System.nanoTime();

        Map<Character, Node> freqMap = new HashMap<>();
        for (int i = 0; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (freqMap.containsKey(c))
                freqMap.get(c).increaseFreq();
            else
                freqMap.put(c, new Node(c));
        }

        var pq = new PriorityQueue<>(freqMap.values());
        System.out.println(getJobId() + " / EncodingJob - Calculate Frequency End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);
//        log.info("[{}] EncodingJob - Calculate Frequency End [{} sec]",
//                getJobId(), execTime(currTime));

        return pq;
    }

    private Node buildHuffmanTree(PriorityQueue<Node> freqTree) {
        var currTime = System.nanoTime();

        Node root = null;
        if (freqTree.size() == 1) {
            var lc = freqTree.peek();
            root = new Node('\0');
            lc.parent = root;
            lc.bitValue = 0;
            root.lc = lc;
        } else {
            while (freqTree.size() > 1) {
                var lc = freqTree.poll();
                var rc = freqTree.poll();
                assert rc != null;

                root = new Node('\0');
                root.frequency = lc.frequency + rc.frequency;
                lc.parent = rc.parent = root;
                lc.bitValue = 0;
                rc.bitValue = 1;

                root.lc = lc;
                root.rc = rc;

                freqTree.add(root);
            }
        }
        System.out.println(getJobId() + " / EncodingJob - Build Huffman Tree End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);

//        log.info("[{}] EncodingJob - Build Huffman Tree End [{} sec]",
//                getJobId(), execTime(currTime));

        return root;
    }

    private Map<Character, Node> buildLeafMap(Node root) {
        var currTime = System.nanoTime();

        List<Node> queue = new LinkedList<>();
        Map<Character, Node> leafMap = new HashMap<>();
        queue.add(root);

        while (queue.size() > 0) {
            var curr = queue.remove(0);
            var left = curr.lc;
            var right = curr.rc;

            if (left != null)
                queue.add(left);

            if (right != null)
                queue.add(right);

            if (curr.isLeaf()) {
                leafMap.put(curr.element, curr);
            }
        }
        System.out.println(getJobId() + " / EncodingJob - Build Leaf Node Map End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);

//        log.info("[{}] EncodingJob - Build Leaf Node Map End [{} sec]",
//                getJobId(), execTime(currTime));

        return leafMap;
    }

    private StringBuilder buildHuffmanCode(StringBuilder encodingBuffer, Map<Character, Node> leafMap) {
        var currTime = System.nanoTime();

        for (Node leaf : leafMap.values()) {
            var node = leaf;
            var tmp = new StringBuilder();

            while (node.parent != null) {
                tmp.append(node.bitValue);
                node = node.parent;
            }

            leaf.huffmanCode = tmp.reverse().toString();
        }

        var result = new StringBuilder();
        for (int i = 0; i < encodingBuffer.length(); i++) {
            char c = encodingBuffer.charAt(i);
            result.append(leafMap.get(c).huffmanCode);
        }

        System.out.println(getJobId() + " / EncodingJob - Build Huffman Code End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);

//        log.info("[{}] EncodingJob - Build Huffman Code End [{} sec]",
//                getJobId(), execTime(currTime));

        return result;
    }

    private void writeResult(DataOutputStream dos, StringBuilder result, Map<Character, Node> leafMap) throws IOException {
        var currTime = System.nanoTime();

        var leafs = leafMap.values();
        dos.write(leafMap.size());
        for (Node node : leafs) {
            var length = node.huffmanCode.length();
            var code = Integer.parseInt(node.huffmanCode, 2);

            dos.write(node.element);
            dos.write(length);

            if (length <= Byte.SIZE) dos.write(code);
            else if (length <= (Byte.SIZE << 1)) dos.writeShort(code);
            else dos.writeInt(code);
        }

        dos.writeInt(result.length());
        int sp = 0;
        while (sp < result.length()) {
            int code = 0;
            if (sp + Byte.SIZE < result.length())
                code = Integer.parseInt(result.substring(sp, sp + Byte.SIZE), 2);
            else
                code = Integer.parseInt(result.substring(sp, result.length()), 2);

            dos.write(code);
            sp += Byte.SIZE;
        }

        System.out.println(getJobId() + " / EncodingJob - Write Encoding Result End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);

//        log.info("[{}] EncodingJob - Write Encoding Result End [{} sec]",
//                getJobId(), execTime(currTime));
    }

    @Override
    public void run() {
        var input = new File(inputPath + '/' + inputName + ".txt");
        var output = new File(outputPath + '/' + outputName + ".ch");
        var outputDir = new File(outputPath);

        // output file이 이미 있을 경우 삭제 후 생성 (덮어 쓰기)
        if (output.exists())
            output.delete();

        // output 경로의 directory가 없을 경우 생성
        if (!outputDir.exists())
            outputDir.mkdir();


        try (var fis = new FileInputStream(input);
             var dis = new DataInputStream(new BufferedInputStream(fis, BUFFER_SIZE));
             var fos = new FileOutputStream(output);
             var dos = new DataOutputStream(new BufferedOutputStream(fos, BUFFER_SIZE))) {

            // read data
//            var buffer = readData(dis);
            var buffer = readData2(fis);

            // get frequency
            var pq = frequency(buffer);

            // build huffman tree
            var root = buildHuffmanTree(pq);

            // get leaf table
            var leafMap = buildLeafMap(root);

            // build huffman code
            var result = buildHuffmanCode(buffer, leafMap);

            // write result
            writeResult(dos, result, leafMap);

        } catch (Exception e) {
            e.printStackTrace();
//            log.error("{}", e.getMessage());
//            log.error("{}", Arrays.toString(e.getStackTrace()));
        }

        System.out.println(getJobId() + " / EncodingJob - Total Exec Time [" + totalExecTime + " sec]");

//        log.info("[{}] EncodingJob - In[{}], Out[{}], Compression Ratio: {}",
//                getJobId(), input.length(), output.length(), String.format("%.5f", input.length() / (double) output.length()));
    }

    public static EncodingJob createJob(String inputPath, String inputName, String outputPath, String outputName) {
        return new EncodingJob(inputPath, inputName, outputPath, outputName);
    }
}
