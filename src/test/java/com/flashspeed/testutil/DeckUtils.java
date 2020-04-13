package com.flashspeed.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.flashspeed.model.Library;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.Card;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class DeckUtils {

    public static final String EMPTY_DECK_NAME = "";
    public static final String JAP_DECK_NAME = "Japanese";
    public static final String MALAY_DECK_NAME = "Malay";
    public static final Deck JAPANESE_DECK = getTypicalJapDeck();
    public static final Deck MALAY_DECK = getTypicalMalayDeck();

    public static final Deck NEW_EMPTY_DECK = new Deck(new Name("Empty deck"));

    // Manually added - Person's details found in {@code CommandTestUtil}

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public DeckUtils() {} // prevents instantiation

    public static Deck getTypicalJapDeck() {
        Deck deck = new Deck(new Name(JAP_DECK_NAME));
        for (Card card: CardUtils.getJapCards()) {
            deck.add(card);
        }
        return deck;
    }

    public static Deck getTypicalMalayDeck() {
        Deck deck = new Deck(new Name(MALAY_DECK_NAME));
        for (Card card: CardUtils.getMalayCards()) {
            deck.add(card);
        }
        return deck;
    }

    /**
     * Returns an {@code Libary} with all the typical decks.
     */
    public static Library getTypicalLibrary() {
        Library library = new Library();
        for (Deck deck : getTypicalDecks()) {
            library.addDeck(deck);
        }
        return library;
    }

    public static List<Deck> getTypicalDecks() {
        return new ArrayList<>(Arrays.asList(getTypicalJapDeck(), getTypicalMalayDeck()));
    }
}
