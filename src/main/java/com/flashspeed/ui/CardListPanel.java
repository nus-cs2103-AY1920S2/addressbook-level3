package com.flashspeed.ui;

import java.util.logging.Logger;

import com.flashspeed.commons.core.LogsCenter;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

/**
 * The Card List Panel of the App.
 */
public class CardListPanel extends UiPart<Region> {

    private static final String FXML = "CardListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label defaultText;

    @FXML
    private TableView<Card> itemTbl;

    @SuppressWarnings("unchecked")
    public CardListPanel(ObservableValue<Deck> selectedDeck) {
        super(FXML);

        TableColumn<Card, Number> indexColumn = new TableColumn<>("ID");

        indexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(
                itemTbl.getItems().indexOf(column.getValue()) + 1));

        TableColumn<Card, FrontFace> frontColumn = new TableColumn<>("Front");
        frontColumn.setCellValueFactory(new PropertyValueFactory<>("frontFace"));

        TableColumn<Card, BackFace> backColumn = new TableColumn<>("Back");
        backColumn.setCellValueFactory(new PropertyValueFactory<>("backFace"));

        // unchecked generics array creation for varargs parameter here
        itemTbl.getColumns().addAll(indexColumn, frontColumn, backColumn);

        indexColumn.prefWidthProperty().bind(itemTbl.widthProperty().multiply(0.2));
        frontColumn.prefWidthProperty().bind(itemTbl.widthProperty().multiply(0.4));
        backColumn.prefWidthProperty().bind(itemTbl.widthProperty().multiply(0.4));

        indexColumn.setSortable(false);
        frontColumn.setSortable(false);
        backColumn.setSortable(false);

        indexColumn.setResizable(false);
        frontColumn.setResizable(false);
        backColumn.setResizable(false);

        // Load deck page when selected deck changes.
        selectedDeck.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                itemTbl.getItems().clear();
                defaultText.setText("No deck selected");
            } else {
                showCardList(newValue);
            }
        });
    }

    /**
     * Shows Cards of current Deck on right panel.
     *
     * @param deck current deck
     */
    private void showCardList(Deck deck) {
        itemTbl.getItems().clear();
        ObservableList<Card> cardList = deck.asUnmodifiableObservableList();
        if (cardList.size() == 0) {
            defaultText.setText("Selected deck is empty");
        }
        for (Card card : cardList) {
            itemTbl.getItems().add(card);
        }
    }

}
