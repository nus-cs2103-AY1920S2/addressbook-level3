package seedu.address.commons.trie;

import java.util.HashMap;

/**
 * This class represents a single node of the trie data structure.
 */
public class Node {

    private static final String EMPTY_STRING = "";
    private static final int SIZE_OF_ONE = 1;

    private char letter;
    private Node parent;
    private HashMap<Character, Node> children;
    private boolean isWordEnd;

    public Node() {
        children = new HashMap<>();
        isWordEnd = false;
        parent = null;
    }

    public Node(char letter, Node parent) {
        this.letter = letter;
        this.parent = parent;
        children = new HashMap<>();
        isWordEnd = false;
    }

    public Node getChild(char letter) {
        return children.get(letter);
    }

    public HashMap<Character, Node> getChildren() {
        return children;
    }

    public boolean hasChild(char letter) {
        return children.containsKey(letter);
    }

    public boolean hasSingleChild() {
        return children.size() == SIZE_OF_ONE;
    }

    /**
     * Returns the only child of this node. {@code children} must only have a single
     * child to start with.
     */
    public Node getSingleChild() {
        assert hasSingleChild();
        for (Node node : children.values()) {
            return node;
        }
        return null;
    }

    public void addChild(char letter, Node child) {
        children.put(letter, child);
    }

    public void setIsWordEnd(boolean bool) {
        isWordEnd = bool;
    }

    public boolean isWordEnd() {
        return isWordEnd;
    }

    public char getLetter() {
        return letter;
    }

    /**
     * Constructs the word by recursively going up the parent node.
     *
     * @return the constructed word string
     */
    public String constructWord() {
        if (parent == null) {
            return EMPTY_STRING;
        }
        return parent.constructWord() + this.getLetter();
    }
}
