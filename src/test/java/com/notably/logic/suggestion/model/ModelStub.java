package com.notably.logic.suggestion.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.notably.model.suggestion.SuggestionItem;
import com.notably.testutil.ModelStubBase;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A Stub class for Model.
 */
public class ModelStub extends ModelStubBase {
    private ObservableList<SuggestionItem> suggestions;
    private Property<Optional<String>> responseTextProperty;
    private StringProperty input;

    public ModelStub() {
        suggestions = FXCollections.observableArrayList();
        responseTextProperty = new SimpleObjectProperty(Optional.empty());
        input = new SimpleStringProperty("");
    }

    public Property<Optional<String>> responseTextProperty() {
        return responseTextProperty;
    }

    public void setResponseText(String responseText) {
        Objects.requireNonNull(responseText);
        responseTextProperty.setValue(Optional.of(responseText));
    }

    public ObservableList<SuggestionItem> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionItem> suggestions) {
        Objects.requireNonNull(suggestions);
        this.suggestions.setAll(suggestions);
    }

    public String getInput() {
        return this.input.getValue();
    }

    public void setInput(String input) {
        this.input.setValue(input);
    }

    /* TODO: add after BlockModelImpl is done
    public BlockTree getBlockTree() {

    }*/
}
