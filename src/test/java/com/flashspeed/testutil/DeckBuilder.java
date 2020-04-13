package com.flashspeed.testutil;

import java.util.ArrayList;
import java.util.List;

import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.Card;

/**
 * A utility class to help with building Deck objects.
 */
public class DeckBuilder {

    public static final String DEFAULT_NAME = "Japanese";
    public static final Deck DEFAULT_DECK = new Deck(new Name("Default deck"));

    private Name name;
    private List<Card> cards = new ArrayList<>();

    public DeckBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the DeckBuilder with the data of {@code personToCopy}.
     */
    public DeckBuilder(Deck deckToCopy) {
        name = deckToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Deck} that we are building.
     */
    public DeckBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Adds a list of cards to the deck that we are building.
     */
    public DeckBuilder withCards(List<Card> cards) {
        for (Card card : cards) {
            this.cards.add(card);
        }
        return this;
    }

    /**
     * Adds a card to the deck that we are building.
     */
    public DeckBuilder withCard(Card card) {
        cards.add(card);
        return this;
    }

    /**
     * Builds a deck for testing purposes.
     *
     * @return The deck object built.
     */
    public Deck build() {
        Deck deck = new Deck(name);
        for (Card card : cards) {
            deck.add(card);
        }
        return deck;
    }

}
