package com.flashspeed.model;

import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;

import javafx.collections.ObservableList;

/**
 * The API of Game Component
 */
public interface Game {

    /**
     * Returns the value of whether a card has been flipped.
     * @return true if a card has been flipped, false otherwise.
     */
    boolean isFlipped();

    /**
     * Flips the card to the back face.
     * @return true if the card has not been flipped, false otherwise
     */
    BackFace flip();

    /**
     * Returns a random next card after user answers Yes.
     * Removes the correct card from the deck.
     * @return the next card or null if card list is empty
     */
    Card answerYes();

    /**
     * Returns the next card after user answers No.
     * Adds a duplicate wrong card to the deck.
     * @return the next card or null if card list is empty
     */
    Card answerNo();

    /**
     * Returns the statistics report when user stops the sessions.
     */
    Statistics stop();

    /**
     * Returns the current index of the card.
     */
    int getCurrCardIdx();

    /**
     * Returns the number of cards have been attempted.
     */
    int getCardAttempted();

    /**
     * Returns number of the remaining cards inside the deck.
     */
    int getDeckSize();

    /**
     * Returns the card list.
     */
    ObservableList<Card> getCards();
}
