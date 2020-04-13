package com.flashspeed.testutil;

import com.flashspeed.model.Library;
import com.flashspeed.model.deck.Deck;

/**
 * A utility class to help with building Library objects.
 * Example usage: <br>
 *     {@code Library library = new LibraryBuilder().withDeck("Japanese", "Korean").build();}
 */
public class LibraryBuilder {

    private Library library;

    public LibraryBuilder() {
        library = new Library();
    }

    public LibraryBuilder(Library library) {
        this.library = library;
    }

    /**
     * Adds a new {@code Deck} to the {@code Library} that we are building.
     */
    public LibraryBuilder withDeck(Deck deck) {
        library.createDeck(deck);
        return this;
    }

    public Library build() {
        return library;
    }
}
