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

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;


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

        suggestionsList.addListener((ListChangeListener)(c -> {System.out.println("lol");}));
        suggestionsListPanel.setItems(suggestionsList);
        suggestionsListPanel.setCellFactory(listView -> new SuggestionsListViewCell());
    }

    public SuggestionsWindowView(ObservableList<SuggestionItem> suggestionsList,
                                 Property<Optional<String>> responseText, Model model) {
        super(FXML);
        Runnable action = () -> {
            model.setInput("open" + " " + "Open a note" + " " + "mujibu");
        };

        suggestionsList.addListener((ListChangeListener)(c -> {System.out.println("lol");}));
        List<SuggestionItem> sg = new ArrayList<>();
        for (int i=0;i<5;i++) {
            sg.add(new SuggestionItemImpl("mujibu"+i ,action));
        }
        model.setSuggestions(sg);

        suggestionsText.setText("Suggestions Text Box");

        suggestionsListPanel.setItems(model.getSuggestions());
        suggestionsListPanel.setPrefHeight(suggestionsListPanel.getItems().size() * 27);
        suggestionsListPanel.setCellFactory(listView -> new SuggestionsListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code SuggestionItem}.
     */
    class SuggestionsListViewCell extends ListCell<SuggestionItem> {
        @Override
        protected void updateItem(SuggestionItem suggestionItem, boolean empty) {
            super.updateItem(suggestionItem, empty);
            System.out.println("ko");
            Optional<String> suggestionItemText = Optional.of("mujiboo");
            //Optional<String> suggestionItemText = suggestionItem.getProperty("displayText");
            System.out.println("ok");
            setGraphic(null);

            if (empty || suggestionItem == null || suggestionItemText.isEmpty()) {
                setText(null);
            } else {
                setText(suggestionItemText.get());
            }
        }
    }
}