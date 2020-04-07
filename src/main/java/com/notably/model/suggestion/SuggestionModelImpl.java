package com.notably.model.suggestion;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The implementation class of SuggestionModel.
 */
public class SuggestionModelImpl implements SuggestionModel {
    private ObservableList<SuggestionItem> suggestions;

    public SuggestionModelImpl() {
        suggestions = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<SuggestionItem> getSuggestions() {
        return suggestions;
    }

    @Override
    public void setSuggestions(List<SuggestionItem> suggestions) {
        Objects.requireNonNull(suggestions);
        this.suggestions.setAll(suggestions);
    }

    @Override
    public void clearSuggestions() {
        suggestions.clear();
    }
}
