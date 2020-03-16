package com.notably.model.suggestion;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The implementation class of SuggestionModel.
 */
public class SuggestionModelManager implements SuggestionModel {
    private ObservableList<SuggestionItem> suggestions;
    private String commandInputText;
    private Property<Optional<String>> commandTextProperty;

    @Override
    public ObservableList<SuggestionItem> getSuggestions() {
        return suggestions;
    }

    @Override
    public void setSuggestions(List<SuggestionItem> suggestions) {
        this.suggestions = FXCollections.observableArrayList(suggestions);
    }

    // TODO: update commands
    @Override
    public Property<Optional<String>> commandTextProperty() {
        if (commandInputText.startsWith("open")) {
            commandTextProperty.setValue(Optional.of("Open a note"));
        } else if (commandInputText.startsWith("search")) {
            commandTextProperty.setValue(Optional.of("Search a note"));
        } else if (commandInputText.isEmpty()) {
            commandTextProperty.setValue(Optional.empty());
        } else {
            commandTextProperty.setValue(Optional.of("TODO"));
        }

        return commandTextProperty;
    }

    @Override
    public void setCommandInputText(String commandInputText) {
        this.commandInputText = commandInputText;
    }

    @Override
    public String getCommandInputText() {
        return commandInputText;
    }

    @Override
    public void clearCommandInputText() {
        commandInputText = "";
    }

    @Override
    public void clearSuggestions() {
        suggestions.clear();
    }
}
