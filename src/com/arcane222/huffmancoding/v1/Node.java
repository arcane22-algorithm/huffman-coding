/**
 * @class Node
 * @author Lee Hong Jun (arcane22, hong3883@naver.com)
 * @description
 *  - Last modified 2021. 12. 09
 *  - Huffman tree의 각 node를 위한 Node class
 */

package com.arcane222.huffmancoding.v1;

public class Node implements Comparable<Node> {
    /*** Instance Variable ***/
    private Node parent, leftChild, rightChild;
    private char element;
    private int bitValue, frequency;
    private String huffmanCode;


    /*** Constructor ***/
    public Node(){}

    public Node(char element, int frequency) {
        this.element = element;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Node n) {
        return Integer.compare(frequency, n.frequency);
    }

    /**
     * 자식노드가 없다면 Left(잎) 노드
     * @return
     */
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    /*** Get & Set node's value (element) ***/
    public char getElement() { return element; }
    public void setElement(char element) { this.element = element; }

    /*** Get & Set parent node ***/
    public Node getParent() { return parent; }
    public void setParent(Node parent) { this.parent = parent; }

    /*** Get & Set left child node ***/
    public Node getLeftChild() { return leftChild; }
    public void setLeftChild(Node leftChild) { this.leftChild = leftChild; }

    /*** Get & Set right child node ***/
    public Node getRightChild() { return rightChild; }
    public void setRightChild(Node rightChild) { this.rightChild = rightChild; }

    /*** Get & Set node bit value (0 or 1) ***/
    public int getBitValue() { return bitValue; }
    public void setBitValue(int bitValue) { this.bitValue = bitValue; }

    /*** Get & Set character's frequency ***/
    public int getFrequency() { return frequency; }
    public void setFrequency(int frequency) { this.frequency = frequency; }

    /*** Get & Set binary code (huffman code - encoded character's value) ***/
    public String getHuffmanCode() { return huffmanCode; }
    public void setHuffmanCode(String huffmanCode) { this.huffmanCode = huffmanCode; }
}