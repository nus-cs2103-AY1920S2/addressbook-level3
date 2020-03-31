package com.notably.view;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.suggestion.SuggestionItem;

import com.notably.model.suggestion.SuggestionItemImpl;
import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuggestionsWindowView extends ViewPart<Region> {

    private static final String FXML = "SuggestionsWindowView.fxml";

    @FXML
    private VBox suggestionsWindow;

    @FXML
    private Label suggestionsText;

    @FXML
    private ListView<SuggestionItem> suggestionsListPanel;

    public SuggestionsWindowView(ObservableList<SuggestionItem> suggestionsList,
                                 Property<Optional<String>> responseText) {
        super(FXML);
        suggestionsText.setText("Suggestions Text Box");

        suggestionsListPanel.setItems(suggestionsList);
        suggestionsListPanel.setCellFactory(listView -> new SuggestionsListViewCell());
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