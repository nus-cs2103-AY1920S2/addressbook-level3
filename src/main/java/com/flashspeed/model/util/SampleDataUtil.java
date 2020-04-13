package com.flashspeed.model.util;

import com.flashspeed.model.Library;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

/**
 * Contains utility methods for populating {@code Library} with sample data.
 */
public class SampleDataUtil {
    public static Deck[] getSampleDecks() {
        Deck sampleDeck = new Deck(new Name("Sample Deck 1"));
        sampleDeck.add(new Card(new FrontFace("front"), new BackFace("back")));
        return new Deck[] {
            sampleDeck
        };
    }

    public static ReadOnlyLibrary getSampleLibrary() {
        Library sampleLib = new Library();
        for (Deck sampleDeck : getSampleDecks()) {
            sampleLib.addDeck(sampleDeck);
        }
        return sampleLib;
    }
}
