package com.notably.view.suggestion;

import java.util.Optional;

import com.notably.model.suggestion.SuggestionItem;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * Custom {@code ListCell} that displays the graphics of a {@code SuggestionItem}
 * within {@link SuggestionsWindowView}.
 */
class SuggestionsListViewCell extends ListCell<SuggestionItem> {
    private final Label pathLabel;
    private final Label selectedItemLabel;
    private final Pane placeholderPane;
    private final HBox container;

    public SuggestionsListViewCell() {
        pathLabel = new Label();
        selectedItemLabel = new Label();
        placeholderPane = new Pane();
        container = new HBox(pathLabel, placeholderPane, selectedItemLabel);

        setComponentSettings(selectedItemLabel, placeholderPane, container);
    }

    private void setComponentSettings(Label selectedItemLabel, Pane placeholderPane, HBox container) {
        selectedItemLabel.setTextAlignment(TextAlignment.RIGHT);
        placeholderPane.setMinWidth(30);
        container.setMinWidth(0);
        container.setPrefWidth(1);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setHgrow(selectedItemLabel, Priority.ALWAYS);
        container.setHgrow(placeholderPane, Priority.SOMETIMES);
        container.setHgrow(pathLabel, Priority.SOMETIMES);
    }

    @Override
    protected void updateItem(SuggestionItem suggestionItem, boolean empty) {
        super.updateItem(suggestionItem, empty);

        if (empty || suggestionItem == null) {
            setText("");
            setGraphic(null);
        } else {
            setPathLabel(getItem());
            setSelectedItemLabel();
            setGraphic(container);
        }
    }

    private void setPathLabel(SuggestionItem item) {
        Optional<String> displayProperty = item.getProperty("displayText");
        String displayString = displayProperty.orElse("");
        pathLabel.setText(displayString);
    }

    private void setSelectedItemLabel() {
        if (isSelected()) {
            selectedItemLabel.setText("Press enter to autofill");
        } else {
            selectedItemLabel.setText("");
        }
    }
}
