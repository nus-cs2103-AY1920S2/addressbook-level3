package com.flashspeed.storage;

import static com.flashspeed.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.exceptions.IllegalValueException;
import com.flashspeed.model.deck.Name;
import com.flashspeed.testutil.DeckUtils;

public class JsonAdaptedDeckTest {
    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(DeckUtils.MALAY_DECK);
        assertEquals(DeckUtils.MALAY_DECK, deck.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDeck jsonDeck = new JsonAdaptedDeck(DeckUtils.getTypicalJapDeck());
        jsonDeck.setName(null);
        String expectedMessage = String.format(
                JsonAdaptedDeck.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, jsonDeck::toModelType);
    }

    @Test
    public void toModelType_nullDeck_throwsNullPointerException() {
        String expectedMessage = String.format(JsonAdaptedDeck.MISSING_FIELD_MESSAGE_FORMAT);
        assertThrows(NullPointerException.class, () -> new JsonAdaptedDeck("Japanese", null));
    }

}
