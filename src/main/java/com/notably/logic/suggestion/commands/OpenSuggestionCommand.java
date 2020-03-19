package com.notably.logic.suggestion.commands;

import java.util.ArrayList;
import java.util.List;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.Path;
import com.notably.logic.suggestion.SuggestionCommand;
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
        updateResponseText(model);

        List<Path> possiblePaths = getPossiblePaths(path);
        List<SuggestionItem> suggestions = getSuggestions(possiblePaths, model);

        model.setSuggestions(suggestions);
    }

    private void updateResponseText(Model model) {
        model.setResponseText("Open a new note");
    }

    private List<Path> getPossiblePaths(Path path) {
        List<Path> possiblePaths = new ArrayList<>();

        // TODO: traverse the data structure. For now will use dummy values.
        if (path instanceof AbsolutePath) { // Search from root.

        } else {

        }

        // TODO: for instance, user input is "open /nus/", we get the children of the block. To be deleted.
        List<String> components1 = new ArrayList<>();
        components1.add("nus");
        components1.add("y1s1");

        List<String> components2 = new ArrayList<>();
        components2.add("nus");
        components2.add("y1s2");

        List<String> components3 = new ArrayList<>();
        components3.add("nus");
        components3.add("y2s1");
        components3.add("cs4444");

        Path path1 = new AbsolutePath(components1);
        Path path2 = new AbsolutePath(components2);
        Path path3 = new AbsolutePath(components3);

        possiblePaths.add(path1);
        possiblePaths.add(path2);
        possiblePaths.add(path3);
        return possiblePaths;
    }

    private List<SuggestionItem> getSuggestions(List<Path> possiblePaths, Model model) {
        List<SuggestionItem> suggestions = new ArrayList<>();
        for (Path path : possiblePaths) {
            String displayText = "open " + path.toString();
            Runnable action = () -> {
                model.setInput(displayText);
            };
            SuggestionItem suggestionItem = new SuggestionItemImpl(displayText, action);
            suggestions.add(suggestionItem);
        }
        return suggestions;
    }
}
