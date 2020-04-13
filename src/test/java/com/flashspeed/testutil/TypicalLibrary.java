package com.flashspeed.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.flashspeed.logic.commands.CommandTestUtil;
import com.flashspeed.model.Library;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.card.Card;

/**
 * Provides a typical library with example values.
 */
public class TypicalLibrary {

    public static final Card KOREAN_HELLO = new CardBuilder().withInput(
            CommandTestUtil.VALID_CARD_NAME_KOREAN_1_HELLO).build();
    public static final Card KOREAN_YES = new CardBuilder().withInput(
            CommandTestUtil.VALID_CARD_NAME_KOREAN_1_YES).build();
    public static final Card KOREAN_NO = new CardBuilder().withInput(
            CommandTestUtil.VALID_CARD_NAME_KOREAN_1_NO).build();
    public static final Card KOREAN_WEEKENDS = new CardBuilder().withInput(
            CommandTestUtil.VALID_CARD_NAME_KOREAN_2_WEEKENDS).build();
    public static final Card KOREAN_WEEKDAYS = new CardBuilder().withInput(
            CommandTestUtil.VALID_CARD_NAME_KOREAN_2_WEEKDAYS).build();
    public static final Card KOREAN_SUNDAY = new CardBuilder().withInput(
            CommandTestUtil.VALID_CARD_NAME_KOREAN_2_SUNDAY).build();

    public static final ArrayList<Card> KOREAN_1_LIST = new ArrayList<>(
            Arrays.asList(KOREAN_HELLO, KOREAN_YES, KOREAN_NO));
    public static final ArrayList<Card> KOREAN_2_LIST = new ArrayList<>(
            Arrays.asList(KOREAN_WEEKENDS, KOREAN_WEEKDAYS, KOREAN_SUNDAY));

    public static final Deck KOREAN_1 = new DeckBuilder().withName(
            CommandTestUtil.VALID_DECK_NAME_KOREAN_1).withCards(KOREAN_1_LIST).build();
    public static final Deck KOREAN_2 = new DeckBuilder().withName(
            CommandTestUtil.VALID_DECK_NAME_KOREAN_2).withCards(KOREAN_2_LIST).build();

    private TypicalLibrary() {

    }

    /**
     * Returns an {@code Library} with all the typical Decks and Cards.
     */
    public static Library getTypicalLibrary() {
        Library library = new Library();
        for (Deck deck : getTypicalDecks()) {
            library.addDeck(deck);
        }

        return library;
    }

    public static List<Deck> getTypicalDecks() {
        return new ArrayList<>(Arrays.asList(KOREAN_1, KOREAN_2));
    }
}
