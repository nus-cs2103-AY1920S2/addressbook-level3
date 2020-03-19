package com.notably.logic.suggestion.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.Path;
import com.notably.logic.suggestion.SuggestionCommand;
import com.notably.logic.suggestion.commands.treetest.Node;
import com.notably.model.Model;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;

/**
 * Represents a suggestion command object to open a note.
 */
public class OpenSuggestionCommand implements SuggestionCommand {
    private Path path;

    public OpenSuggestionCommand(Path path) {
        this.path = path;
    }

    @Override
    public void execute(Model model) {
        String responseText = "Open a note";
        model.setResponseText(responseText);
        System.out.println(responseText);

        List<Path> possiblePaths = getPossiblePaths(path);
        List<SuggestionItem> suggestions = getSuggestions(possiblePaths, model);

        for (SuggestionItem suggestionItem : suggestions) {
            System.out.println(suggestionItem.getDisplayText());
        }

        model.setSuggestions(suggestions);
    }

    private List<Path> getPossiblePaths(Path path) {
        List<Path> possiblePaths = new ArrayList<>();
        Node<String> root = null;

        // TODO: traverse the data structure. For now will use dummy values.
        if (path instanceof AbsolutePath) { // Search from root.
            root = createTree();
        } else {
            // TODO
        }

        List<String> components = path.getComponents();
        List<Node<String>> children = new ArrayList<>();
        int index = 0;
        Queue<Node<String>> queue = new LinkedList<>();
        for (Node<String> rootChild : root.getChildren()) { //don't need add root to queue
            queue.add(rootChild);
        }

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

        if (children.isEmpty()) { // if the note doesn't have any children
            Path newPath = AbsolutePath.fromComponent(components);
            possiblePaths.add(newPath);
        } else {
            for (Node<String> child : children) {
                List<String> newComponents = new ArrayList<>();
                newComponents.addAll(components);
                newComponents.add(child.getTitle());
                Path newPath = AbsolutePath.fromComponent(newComponents);
                possiblePaths.add(newPath);
            }
        }
        return possiblePaths;
    }

    private List<SuggestionItem> getSuggestions(List<Path> possiblePaths, Model model) {
        List<SuggestionItem> suggestions = new ArrayList<>();
        for (Path path : possiblePaths) {
            String displayText = path.toString();
            Runnable action = () -> {
                model.setInput(displayText);
            };
            SuggestionItem suggestionItem = new SuggestionItemImpl(displayText, action);
            suggestions.add(suggestionItem);
        }

        return suggestions;
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
