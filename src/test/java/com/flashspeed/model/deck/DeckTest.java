package com.flashspeed.model.deck;

import static com.flashspeed.logic.commands.CommandTestUtil.VALID_DECK_NAME_KOREAN_1;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.flashspeed.testutil.CardUtils;
import com.flashspeed.testutil.DeckBuilder;
import com.flashspeed.testutil.DeckUtils;

public class DeckTest {

    @Test
    public void isSameDeck() {
        // same object -> returns true
        assertTrue(DeckUtils.JAPANESE_DECK.isSameDeck(DeckUtils.JAPANESE_DECK));

        // null -> returns false
        assertFalse(DeckUtils.JAPANESE_DECK.isSameDeck(null));

        // rename Japanese to French -> returns false
        Deck renamed = new DeckBuilder(DeckUtils.JAPANESE_DECK).withName(VALID_DECK_NAME_KOREAN_1).build();
        assertFalse(DeckUtils.JAPANESE_DECK.isSameDeck(renamed));


        // new deck with same name, same cards -> returns true
        Deck newDeck = new DeckBuilder()
                .withName("Japanese")
                .withCards(CardUtils.JAP_CARDS).build();
        assertTrue(DeckUtils.JAPANESE_DECK.isSameDeck(newDeck));

        // new deck with same name, no cards -> return true
        newDeck = new DeckBuilder()
                .withName("Japanese").build();
        assertTrue(DeckUtils.JAPANESE_DECK.isSameDeck(newDeck));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Deck japaneseCopy = new DeckBuilder(DeckUtils.JAPANESE_DECK).build();
        assertTrue(DeckUtils.JAPANESE_DECK.equals(japaneseCopy));

        // same object -> returns true
        assertTrue(DeckUtils.JAPANESE_DECK.equals(DeckUtils.JAPANESE_DECK));

        // null -> returns false
        assertFalse(DeckUtils.JAPANESE_DECK.equals(null));

        // different type -> returns false
        assertFalse(DeckUtils.JAPANESE_DECK.equals(5));

        // different deck -> returns false
        assertFalse(DeckUtils.JAPANESE_DECK.equals(DeckUtils.MALAY_DECK));

        // different name -> returns false
        Deck renamed = new DeckBuilder(DeckUtils.JAPANESE_DECK).withName(VALID_DECK_NAME_KOREAN_1).build();
        assertFalse(DeckUtils.JAPANESE_DECK.equals(renamed));

    }
}
