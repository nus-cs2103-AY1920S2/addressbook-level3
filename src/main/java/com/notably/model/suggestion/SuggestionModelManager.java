package com.notably.model.suggestion;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SuggestionModelManager implements SuggestionModel {
    ObservableList<SuggestionItem> suggestions;
    String commandInputText;
    Property<Optional<String>> commandTextProperty;

    @Override
    public ObservableList<SuggestionItem> getSuggestions() {
        return suggestions;
    }

    @Override
    public void setSuggestions(List<SuggestionItem> suggestions) {
        this.suggestions = FXCollections.observableArrayList(suggestions);
    }

    @Override
    public Property<Optional<String>> commandTextProperty() {
        return commandTextProperty;
    }

    @Override
    public void setCommandInputText(String commandText) {
        commandInputText = commandText;
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
