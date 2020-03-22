package com.notably.logic.suggestion.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.logic.suggestion.SuggestionCommand;
import com.notably.model.Model;
import com.notably.model.block.BlockTreeItem;
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
        setResponseTextToModel(model);
        List<AbsolutePath> possiblePaths = getPossiblePaths(path, model);
        List<SuggestionItem> suggestions = getSuggestions(possiblePaths, model);

        setSuggestionsToModel(model, suggestions);
    }

    private void setResponseTextToModel(Model model) {
        model.setResponseText(RESPONSE_MESSAGE);
    }

    private void setSuggestionsToModel(Model model, List<SuggestionItem> suggestions) {
        model.setSuggestions(suggestions);
    }

    public List<AbsolutePath> getPossiblePaths(AbsolutePath path, Model model) {
        BlockTreeItem root = model.getBlockTree().getRootBlock();

        List<String> components = path.getComponents();
        List<BlockTreeItem> children = new ArrayList<>();
        Queue<BlockTreeItem> queue = new LinkedList<>();

        for (BlockTreeItem rootChild : root.getBlockChildren()) {
            queue.add(rootChild);
        }

        int index = 0;
        while (!queue.isEmpty()) {
            BlockTreeItem curr = queue.poll();
            String dir = components.get(index);
            if (curr.getTitle().equals(dir)) {
                children = curr.getBlockChildren();
                if (index == components.size() - 1) { // just get all the children
                    break;
                } else {
                    queue.clear();
                    for (BlockTreeItem child : children) {
                        queue.add(child);
                    }
                    index++;
                }
            }
        }

        possiblePaths.add(path);
        if (!children.isEmpty()) {
            for (BlockTreeItem child : children) {
                List<String> newComponents = new ArrayList<>();
                newComponents.addAll(components);
                newComponents.add(child.getTitle().getText());
                getChildRecursive(child, newComponents);
            }
        }
        return possiblePaths;
    }

    public void getChildRecursive(BlockTreeItem node, List<String> components) {
        List<BlockTreeItem> children = node.getBlockChildren();
        if (children.size() == 0) { // if reach the end, add to possible paths
            AbsolutePath newPath = AbsolutePath.fromComponents(components);
            possiblePaths.add(newPath);
        } else {
            for (BlockTreeItem child : children) {
                List<String> newComponents = new ArrayList<>();
                newComponents.addAll(components);
                newComponents.add(child.getTitle().getText());
                getChildRecursive(child, newComponents);
            }
        }
    }

    List<SuggestionItem> getSuggestions(List<AbsolutePath> possiblePaths, Model model) {
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
}
