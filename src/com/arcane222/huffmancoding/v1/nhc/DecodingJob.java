package com.arcane222.huffmancoding.v1.nhc;


import java.io.*;


public class DecodingJob extends HuffmanJob {

    private double totalExecTime;

    private static class Node {
        private Node[] child;
        private char c;

        public Node() {
            child = new Node[2];
            c = '\0';
        }

        public void add(String word, char c) {
            Node curr = this;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - '0';
                if (curr.child[idx] == null) {
                    curr.child[idx] = new Node();
                }
                curr = curr.child[idx];
            }
            curr.c = c;
        }
    }

    private Node root = new Node();

    public DecodingJob(String inputPath, String inputName, String outputPath, String outputName) {
        super(inputPath, inputName, outputPath, outputName);
    }

    private String zeroPadding(String binaryString, int fullSize) {
        int length = binaryString.length();
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < fullSize - length; i++)
            buffer.append(0);
        buffer.append(binaryString);

        return buffer.toString();
    }

    private Node buildDecodingTable(DataInputStream dis) throws IOException {
        var currTime = System.nanoTime();

        String code;
        int characterCount = dis.read();
        for (int i = 0; i < characterCount; i++) {
            char c = (char) dis.read();
            int length = dis.read();

            if (length <= Byte.SIZE) code = Integer.toBinaryString(dis.read());
            else if (length <= (Byte.SIZE << 1)) code = Integer.toBinaryString(dis.readShort());
            else code = Integer.toBinaryString(dis.readInt());
            code = zeroPadding(code, length);

            root.add(code, c);
        }

//        log.info("[{}] DecodingJob - Build Decoding Table End [{} sec]",
//                getJobId(), execTime(currTime));
        System.out.println(getJobId() + " / DecodingJob - Build Decoding Table End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);

        return root;
    }

    private StringBuilder readHuffmanCode(DataInputStream dis) throws IOException {
        var currTime = System.nanoTime();

        StringBuilder buffer = new StringBuilder();
        int fullCodeSize = dis.readInt();

        for (int i = 0; i < (fullCodeSize >> 3); i++) {
            int val = dis.read();
            buffer.append(zeroPadding(Integer.toBinaryString(val), Byte.SIZE));
        }

        if (fullCodeSize % Byte.SIZE != 0) {
            buffer.append(zeroPadding(Integer.toBinaryString(dis.read()), fullCodeSize % Byte.SIZE));
        }

//        log.info("[{}] DecodingJob - Read Encoded Code End [{} sec]",
//                getJobId(), execTime(currTime));
        System.out.println(getJobId() + " / DecodingJob - Read Encoded Code End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);

        return buffer;
    }

    private void writeResult(DataOutputStream dos, Node root, StringBuilder buffer) throws IOException {
        var currTime = System.nanoTime();

        int sp = 0; // src pointer
        Node curr = root;
        while (sp < buffer.length()) {
            curr = curr.child[buffer.charAt(sp++) - '0'];

            if(curr.c != '\0') {
                dos.write(curr.c);
                curr = root;
            }
        }

//        log.info("[{}] DecodingJob - Write Result End [{} sec]",
//                getJobId(), execTime(currTime));
        System.out.println(getJobId() + " / DecodingJob - Write Result End [" + execTime(currTime) + " sec]");
        totalExecTime += execTimeDouble(currTime);
    }


    @Override
    public void run() {
        var input = new File(inputPath + '/' + inputName + ".ch");
        var output = new File(outputPath + '/' + outputName + ".txt");
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

            // read decoding header info & build table
            var root = buildDecodingTable(dis);

            // read encoded string
            var buffer = readHuffmanCode(dis);

            // write decoded result
            writeResult(dos, root, buffer);

        } catch (Exception e) {
//            log.error(Arrays.toString(e.getStackTrace()));
        }


//        log.info("[{}] DecodingJob - In[{}], Out[{}], Compression Ratio: {}",
//                getJobId(), input.length(), output.length(), String.format("%.5f", output.length() / (double) input.length()));

        System.out.println(getJobId() + " / EncodingJob - Total Exec Time [" + totalExecTime + " sec]");
    }


    public static DecodingJob createJob(String inputPath, String inputName, String outputPath, String outputName) {
        return new DecodingJob(inputPath, inputName, outputPath, outputName);
    }
}
