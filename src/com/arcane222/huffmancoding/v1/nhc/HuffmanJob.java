package com.arcane222.huffmancoding.v1.nhc;



public abstract class HuffmanJob extends AbstractJob {

    protected static final int BUFFER_SIZE = 1 << 16;
    protected final String inputPath, inputName;
    protected final String outputPath, outputName;

    protected static class Node implements Comparable<Node> {
        protected Node lc, rc, parent;
        protected char element;
        protected int bitValue, frequency;
        protected String huffmanCode;

        public Node(char element) {
            this.element = element;
            this.frequency = 1;
        }

        public void increaseFreq() {
            frequency++;
        }

        public boolean isLeaf() {
            return lc == null && rc == null;
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(frequency, n.frequency);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    ", bitValue=" + bitValue +
                    ", frequency=" + frequency +
                    ", huffmanCode='" + huffmanCode + '\'' +
                    '}';
        }
    }

    public HuffmanJob(String inputPath, String inputName, String outputPath, String outputName) {
        super();
        this.inputPath = inputPath;
        this.inputName = inputName;
        this.outputPath = outputPath;
        this.outputName = outputName;
    }
}
