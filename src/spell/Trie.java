package spell;

import java.util.Objects;
import java.util.TreeSet;
import java.util.SortedSet;

public class Trie implements ITrie{
    private Node root;
    private int numNodes;
    private int numWords;

    public Trie() {
        root = new Node();
        numNodes = 1;
        numWords = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this){
            return true;
        }
        if (o.getClass() != this.getClass()){
            return false;
        }
        Trie trie = (Trie) o;
        if (this.getWordCount() != (trie.getWordCount()) || this.getNodeCount() != (trie.getNodeCount())){
            return false;
        }
        if (compareTries(trie.root, this.root)){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean compareTries(Node node1, Node node2){
        if (node1.getValue() != node2.getValue()){
            return false;
        }
        //need to compare the characters as well
        for (int i = 0; i < node1.getChildren().length; i++){
            Node child1 = node1.getChildren()[i];
            Node child2 = node2.getChildren()[i];
            if ((child1 != null) && (child2 != null)){
                if (!compareTries(child1, child2)){
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        int index = 0;
        for (int i = 0; i < root.getChildren().length; i++) {
            if (root.getChildren()[i] != null){
                index = i;
                break;
            }
        }
        return numWords * index * numNodes;
    }

    @Override
    public String toString() {
        StringBuilder word = new StringBuilder();
        StringBuilder output = new StringBuilder();

        printWords(root, word, output);

        return output.toString();
    }

    private void printWords (Node node, StringBuilder current, StringBuilder output){
        if (node.getValue() > 0){ // testing if its the word
            output.append(current.toString());
            output.append("\n");
        }

        for (int i = 0; i < node.getChildren().length; i++){
            Node child = node.getChildren()[i];

            if (child != null){
                char c = (char)(i + 'a');
                current.append(c);

                printWords(child, current, output);

                current.deleteCharAt(current.length()-1);
            }
        }
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase(); // convert to lower case
        Node node = root; // create root node
        char c; // temporary char holder for current char
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i); // first character of the word
            int index = c - 'a';
            if (node.getChildren()[index] == null){ // this checks if a node exists where that character is in the array
                node.getChildren()[index] = new Node();// if theres no node, then create a new node
                numNodes++; //increment number of nodes
            }
            node = node.children[c - 'a'];
        }
        if (node.frequency < 1) {
            numWords++;
        }
        node.incrementValue();
    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();
        Node node = root; // get to root node
        for (int i = 0; i < word.length(); i++){ //loop through input word
            if (node.children[word.charAt(i) - 'a'] == null){ //check if node is null at that char
                System.out.println("character at " + i + " is null.");
                return null;
            }

            node = node.children[word.charAt(i)- 'a']; // advancing the node
        }

        if (node.frequency > 0){ //check if frequency is above 0
            return node; //return that node bc its final node
        }
        else {
            System.out.println("frequency is zero.");
            return null;
        }

    }

    @Override
    public int getWordCount() {
        return numWords;
    }

    @Override
    public int getNodeCount() {
        return numNodes;
    }
}
