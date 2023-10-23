package spell;

public class Node implements INode{
    Node[] children = new Node[26];
    int frequency = 0;

    public Node() {

    }

    @Override
    public int getValue() {
        return frequency;
    }

    @Override
    public void incrementValue() {
        frequency++;
    }

    @Override
    public Node[] getChildren() {

        return children;
    }
}
