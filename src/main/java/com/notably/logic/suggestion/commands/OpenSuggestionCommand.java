package com.notably.logic.suggestion.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.logic.suggestion.SuggestionCommand;
import com.notably.logic.suggestion.commands.treetest.Node;
import com.notably.model.Model;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;

/**
 * Represents a suggestion command object to open a note.
 */
public class OpenSuggestionCommand implements SuggestionCommand {
    public static final String COMMAND_WORD = "open";
    public static final String RESPONSE_MESSAGE = "Open a note";
    private AbsolutePath path;
    private List<AbsolutePath> possiblePaths = new ArrayList<>();

    public OpenSuggestionCommand(AbsolutePath path) {
        this.path = path;
    }

    @Override
    public void execute(Model model) {
        model.setResponseText(RESPONSE_MESSAGE);
        System.out.println(RESPONSE_MESSAGE);

        List<AbsolutePath> possiblePaths = getPossiblePaths(path);
        List<SuggestionItem> suggestions = getSuggestions(possiblePaths, model);

        for (SuggestionItem suggestionItem : suggestions) {
            System.out.println(suggestionItem.getDisplayText());
        }

        model.setSuggestions(suggestions);
    }

    private List<AbsolutePath> getPossiblePaths(AbsolutePath path) {
        Node<String> root = createTree();

        List<String> components = path.getComponents();
        List<Node<String>> children = new ArrayList<>();
        Queue<Node<String>> queue = new LinkedList<>();

        for (Node<String> rootChild : root.getChildren()) { //don't need add root to queue, but add root's children
            queue.add(rootChild);
        }

        int index = 0;
        while (!queue.isEmpty()) {
            Node<String> curr = queue.poll();
            String dir = components.get(index);
            if (curr.getTitle().equals(dir)) {
                children = curr.getChildren();
                if (index == components.size() - 1) { // just get all the children
                    break;
                } else {
                    queue.clear();
                    for (Node<String> child : children) {
                        queue.add(child);
                    }
                    index++;
                }
            }
        }

        possiblePaths.add(path);
        if (!children.isEmpty()) {
            for (Node<String> child : children) {
                List<String> newComponents = new ArrayList<>();
                newComponents.addAll(components);
                newComponents.add(child.getTitle());
                getChildRecursive(child, newComponents);
            }
        }
        return possiblePaths;
    }

    private void getChildRecursive(Node<String> node, List<String> components) {
        List<Node<String>> children = node.getChildren();
        if (children.size() == 0) { // if reach the end, add to possible paths
            AbsolutePath newPath = AbsolutePath.fromComponents(components);
            possiblePaths.add(newPath);
        } else {
            for (Node<String> child : children) {
                List<String> newComponents = new ArrayList<>();
                newComponents.addAll(components);
                newComponents.add(child.getTitle());
                getChildRecursive(child, newComponents);
            }
        }
    }

    private List<SuggestionItem> getSuggestions(List<AbsolutePath> possiblePaths, Model model) {
        return possiblePaths.stream()
                .map(path -> {
                    String displayText = path.getStringRepresentation();
                    Runnable action = () -> {
                        model.setInput(displayText);
                    };
                    return new SuggestionItemImpl(displayText, action);
                })
                .collect(Collectors.toList());
    }

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
     * Prints the skeleton of the tree.
     * @param node The root node.
     * @param appender A separator to format the display of the tree.
     * @param <T> A generic.
     */
    public static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getTitle());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }
}
