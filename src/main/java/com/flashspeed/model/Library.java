package com.flashspeed.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.UniqueDeckList;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the library level.
 * Duplicates are not allowed (by .isSameDeck comparison).
 */
public class Library implements ReadOnlyLibrary {

    private final UniqueDeckList decks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        decks = new UniqueDeckList();
    }

    public Library() {}

    /**
     * Creates an Library using the Decks in {@code toBeCopied}.
     */
    public Library(ReadOnlyLibrary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the deck list with {@code decks}.
     * {@code decks} must not contain duplicate decks.
     */
    public void setDecks(List<Deck> decks) {
        this.decks.setDecks(decks);
    }


    /**
     * Resets the existing data of this {@code Library} with {@code newData}.
     */
    public void resetData(ReadOnlyLibrary newData) {
        requireNonNull(newData);
        setDecks(newData.getDeckList());
    }

    //// deck-level operations

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in the Library.
     */
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return decks.contains(deck);
    }

    public Deck getDeck(Index index) {
        int idxInInt = index.getZeroBased();
        return decks.get(idxInInt);

    }

    /**
     * Adds a deck to the library.
     * The deck must not already exist in the library.
     */
    public void addDeck(Deck p) {
        decks.add(p);
    }

    public void createDeck(Deck p) {
        decks.add(p);
    }

    /**
     * Replaces the given deck {@code target} in the list with {@code editedDeck}.
     * {@code target} must exist in the library.
     * The deck identity of {@code editedDeck} must not be the same as another existing deck in the library.
     */
    public void setDeck(Deck target, Deck editedDeck) {
        requireNonNull(editedDeck);

        decks.setDeck(target, editedDeck);
    }

    /**
     * Removes {@code key} from this {@code Library}.
     * {@code key} must exist in the library .
     */

    public void deleteDeck(Deck key) {
        decks.remove(key);
    }

    // TODO: refine
    @Override
    public String toString() {
        return decks.asUnmodifiableObservableList().size() + " decks";
    }

    @Override
    public ObservableList<Deck> getDeckList() { // remove the code at the end
        return decks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Library // instanceof handles nulls
                && decks.equals(((Library) other).decks));
    }

    @Override
    public int hashCode() {
        return decks.hashCode();
    }

    public int getSize() {
        return decks.getSize();
    }
}
