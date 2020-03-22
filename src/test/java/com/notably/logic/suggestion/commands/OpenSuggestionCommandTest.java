package com.notably.logic.suggestion.commands;

public class OpenSuggestionCommandTest {
    /**
     * Creates a tree with dummy values. TODO: to be updated with the real DS.
     * @return The root of the tree.
     */
    public static Node<String> createTree() {
        Node<String> root = new Node<>("nus");

        Node<String> nus = root.addChild(new Node<String>("nus"));
        Node<String> y2s1 = nus.addChild(new Node<String>("y2s1"));
        Node<String> y2s2 = nus.addChild(new Node<String>("y2s2"));
        Node<String> cs2103t = y2s2.addChild(new Node<String>("cs2103t"));
        Node<String> cs2101 = y2s2.addChild(new Node<String>("cs2101"));
        Node<String> cs3243 = y2s2.addChild(new Node<String>("cs3243"));
        Node<String> tp = cs2103t.addChild(new Node<String>("tp"));
        Node<String> ip = cs2103t.addChild(new Node<String>("ip"));
        Node<String> notably = tp.addChild(new Node<String>("notably"));

        Node<String> personal = root.addChild(new Node<String>("personal"));
        Node<String> p1 = personal.addChild(new Node<String>("p1"));
        Node<String> p2 = personal.addChild(new Node<String>("p2"));

        return root;
    }


    /**
     * Prints the skeleton of the tree. TODO: to be removed, but will be put here for now in case needed.
     * @param node The root node.
     * @param appender A separator to format the display of the tree.
     * @param <T> A generic.
     */
    public static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getTitle());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }
}
