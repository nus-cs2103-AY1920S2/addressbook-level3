package com.notably.logic.suggestion.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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

    public OpenSuggestionCommand(AbsolutePath path) {
        Objects.requireNonNull(path);
        this.path = path;
    }

    @Override
    public void execute(Model model) {
        // Nullity check
        Objects.requireNonNull(model);

        // Set response text
        model.setResponseText(RESPONSE_MESSAGE);

        // Set suggestions
        List<AbsolutePath> possiblePaths = getPossiblePaths(model);
        List<SuggestionItem> suggestions = getSuggestions(possiblePaths, model);

        setSuggestionsToModel(model, suggestions);
    }


    public void setSuggestionsToModel(Model model, List<SuggestionItem> suggestions) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(suggestions);

        model.setSuggestions(suggestions);
    }

    /**
     * Gets the list of possible absolute paths, based on the user's input path.
     * @param model The app's model.
     * @return List of possible absolute paths, based on the user's input path.
     */
    public List<AbsolutePath> getPossiblePaths(Model model) {
        Objects.requireNonNull(model);

        List<AbsolutePath> possiblePaths = new ArrayList<>();

        BlockTreeItem currTreeItem = model.getBlockTree().get(path);
        List<String> components = path.getComponents();

        List<BlockTreeItem> children = new ArrayList<>();
        Queue<BlockTreeItem> queue = new LinkedList<>();

        queue.offer(currTreeItem);

        int index = 0;
        while (index < components.size() && !queue.isEmpty()) {
            BlockTreeItem curr = queue.poll();
            String dir = components.get(index);
            if (curr.getTitle().getText().equals(dir)) {
                children = curr.getBlockChildren();

                queue.clear();
                queue.addAll(children);

                index++;
            }
        }

        possiblePaths.add(path);

        if (!children.isEmpty()) {
            for (BlockTreeItem child : children) {
                List<String> newComponents = new ArrayList<>();
                newComponents.addAll(components);
                newComponents.add(child.getTitle().getText());
                possiblePaths = getChildRecursive(child, newComponents, possiblePaths);
            }
        }
        return possiblePaths;
    }

    public List<AbsolutePath> getChildRecursive(BlockTreeItem blockTreeItem, List<String> components,
        List<AbsolutePath> possiblePaths) {
        Objects.requireNonNull(blockTreeItem);
        Objects.requireNonNull(components);
        Objects.requireNonNull(possiblePaths);

        List<BlockTreeItem> children = blockTreeItem.getBlockChildren();
        if (children.size() == 0) { // if reach the end, add to possible paths
            AbsolutePath newPath = AbsolutePath.fromComponents(components);
            possiblePaths.add(newPath);
        } else {
            for (BlockTreeItem child : children) {
                List<String> newComponents = new ArrayList<>();
                newComponents.addAll(components);
                newComponents.add(child.getTitle().getText());
                getChildRecursive(child, newComponents, possiblePaths);
            }
        }
        return possiblePaths;
    }

    List<SuggestionItem> getSuggestions(List<AbsolutePath> possiblePaths, Model model) {
        Objects.requireNonNull(possiblePaths);
        Objects.requireNonNull(model);

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
