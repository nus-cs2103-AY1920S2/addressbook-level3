package com.notably.logic.suggestion.commands.treetest;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Node object.
 * @param <T> A generic type.
 */
public class Node<T> {

    private T title = null;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node(T title) {
        this.title = title;
    }

    /**
     * Adds child to the current node.
     * @param child The child.
     * @return The child.
     */
    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    /**
     * Adds list of children under a node.
     * @param children The list of children.
     */
    public void addChildren(List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    /**
     * Gets the children of a node.
     * @return List of children.
     */
    public List<Node<T>> getChildren() {
        return children;
    }

    public T getTitle() {
        return title;
    }

    public void setTitle(T title) {
        this.title = title;
    }

    private void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }
}
