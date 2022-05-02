public class Node implements Comparable<Node> {

    private String sym;
    private String code ="";
    private int freq;
    private Node leftChild;
    private Node rightChild;

    public Node(int freq) {
        this.freq = freq;
    }

    public Node(String sym, int freq) {
        this.sym = sym;
        this.freq = freq;
    }

    public Node(String sym, int freq, Node leftChild, Node rightChild) {
        this.sym = sym;
        this.freq = freq;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public int compareTo(Node o) {
        return freq - o.getFreq();
    }
}
