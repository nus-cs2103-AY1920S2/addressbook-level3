package com.flashspeed.model;

import com.flashspeed.model.deck.Deck;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a library.
 */
public interface ReadOnlyLibrary {

    /**
     * Returns an unmodifiable view of the decks list.
     * This list will not contain any duplicate decks.
     */
    ObservableList<Deck> getDeckList();
}
