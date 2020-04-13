package com.flashspeed.model;

import static com.flashspeed.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.flashspeed.model.deck.Deck;
import com.flashspeed.testutil.CardUtils;
import com.flashspeed.testutil.DeckBuilder;
import com.flashspeed.testutil.DeckUtils;
import com.flashspeed.testutil.LibraryBuilder;

public class LibraryTest {

    private final Library library = new Library();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), library.getDeckList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> library.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyLibrary_replacesData() {
        Library newData = new LibraryBuilder().withDeck(DeckUtils.JAPANESE_DECK).build();
        library.resetData(newData);
        assertEquals(newData, library);
    }


    @Test
    public void hasDeck_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> library.hasDeck(null));
    }

    @Test
    public void hasDeck_deckNotIn_returnsFalse() {
        assertFalse(library.hasDeck(DeckUtils.JAPANESE_DECK));
    }

    @Test
    public void hasDeck_deckIn_returnsTrue() {
        library.addDeck(DeckUtils.JAPANESE_DECK);
        assertTrue(library.hasDeck(DeckUtils.JAPANESE_DECK));
    }

    @Test
    public void hasDeck_deckWithSameIdentityFields_returnsTrue() {
        library.addDeck(DeckUtils.JAPANESE_DECK);
        Deck newDeck = new DeckBuilder()
                .withName("Japanese")
                .withCards(CardUtils.JAP_CARDS).build();
        assertTrue(library.hasDeck(newDeck));
    }

    @Test
    public void getDeckList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> library.getDeckList().remove(0));
    }


}
