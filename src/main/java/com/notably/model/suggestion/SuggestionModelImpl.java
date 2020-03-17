package com.notably.model.suggestion;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The implementation class of SuggestionModel.
 */
public class SuggestionModelImpl implements SuggestionModel {
    private ObservableList<SuggestionItem> suggestions;
    private Property<Optional<String>> responseTextProperty;

    @Override
    public ObservableList<SuggestionItem> getSuggestions() {
        return suggestions;
    }

    @Override
    public void setSuggestions(List<SuggestionItem> suggestions) {
        this.suggestions = FXCollections.observableArrayList(suggestions);
    }

    @Override
    public Property<Optional<String>> responseTextProperty() {
        return responseTextProperty;
    }

    @Override
    public void setResponseTextProperty(String responseText) {
        responseTextProperty.setValue(Optional.ofNullable(responseText));
    }

    @Override
    public void clearResponseTextProperty() {
        responseTextProperty.setValue(Optional.of(""));
    }

    @Override
    public void clearSuggestions() {
        suggestions.clear();
    }
}
