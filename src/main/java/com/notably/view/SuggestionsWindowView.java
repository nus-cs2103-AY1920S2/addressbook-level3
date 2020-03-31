package com.notably.view;

import java.util.Optional;

import com.notably.model.suggestion.SuggestionItem;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


/**
 * A View-Controller for the Suggestions List that appears in the View.
 */
public class SuggestionsWindowView extends ViewPart<Region> {

    private static final String FXML = "SuggestionsWindowView.fxml";
    private IntegerBinding listSizeProperty;

    @FXML
    private VBox suggestionsBox;

    @FXML
    private Label suggestionsText;

    @FXML
    private ListView<SuggestionItem> suggestionsListPanel;

    public SuggestionsWindowView(ObservableList<SuggestionItem> suggestionsList,
                                 Property<Optional<String>> responseText) {
        super(FXML);
        autoUpdateSuggestionsDisplay(suggestionsList, responseText);
        suggestionsListPanel.setItems(suggestionsList);
        suggestionsListPanel.setCellFactory(listView -> new SuggestionsListViewCell());
    }

    /**
     * Sets weak and strong listeners to update the visibility of the {@code SuggestionsWindow}, and its components.
     * @param suggestionsList
     * @param responseText The info text to be displayed above the suggestions list, if any.
     */
    private void autoUpdateSuggestionsDisplay(ObservableList<SuggestionItem> suggestionsList,
                                              Property<Optional<String>> responseText) {
        suggestionsList.addListener((ListChangeListener)(listener -> {
            suggestionsListPanel.setPrefHeight(suggestionsList.size()*26+2);
            if (suggestionsList.size()==0) {
                suggestionsListPanel.setManaged(false);
            } else {
                suggestionsListPanel.setManaged(true);
            }
        }));

        responseText.addListener((observable, oldValue, newValue) -> {
            Optional<String> response = responseText.getValue();
            if (response.isPresent() && !response.equals("")) {
                suggestionsText.setManaged(true);
                suggestionsText.setText(response.get());
            } else {
                suggestionsText.setManaged(false);
                suggestionsText.setText("");
            }
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code SuggestionItem}.
     */
    class SuggestionsListViewCell extends ListCell<SuggestionItem> {
        @Override
        protected void updateItem(SuggestionItem suggestionItem, boolean empty) {
            super.updateItem(suggestionItem, empty);
            setGraphic(null);

            if (empty || suggestionItem == null) {
                setText("");
            } else {
                Optional<String> displayProperty = getItem().getProperty("displayText");
                String displayString = displayProperty.isPresent() ? displayProperty.get() : "";
                setText(displayString);
            }
        }
    }
}
