package com.flashspeed.ui;

import com.flashspeed.model.deck.Deck;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Deck}.
 */
public class DeckCard extends UiPart<Region> {

    private static final String FXML = "DeckListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Deck deck;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private Label cardNo;

    public DeckCard(Deck deck, int displayedIndex) {
        super(FXML);
        this.deck = deck;
        id.setText(displayedIndex + ". ");
        name.setText(deck.getName().name);
        cardNo.setText(String.valueOf(deck.asUnmodifiableObservableList().size()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeckCard)) {
            return false;
        }

        // state check
        DeckCard card = (DeckCard) other;
        return id.getText().equals(card.id.getText())
                && deck.equals(card.deck);
    }
}
