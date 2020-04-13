package com.flashspeed.model.deck;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.UniqueCardList;

import javafx.collections.ObservableList;

/**
 * Represents a Deck in the library.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deck {
    // Identity fields
    private Name name;

    // Data fields
    private UniqueCardList cards = new UniqueCardList();

    /**
     * Every field must be present and not null.
     */
    public Deck(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public int getSize() {
        return cards.getSize();
    }

    /**
     * Returns true if the list contains an equivalent card as the given argument.
     */
    public boolean contains(Card toCheck) {
        return cards.contains(toCheck);
    }

    public Card getCard(Index index) {
        return cards.getCard(index);
    }

    /**
     * Adds a card to the deck.
     * The card must not already exist in the list.
     */
    public void add(Card toAdd) {
        cards.add(toAdd);
    }

    /**
     * Removes the equivalent card from the list.
     * The card must exist in the list.
     */
    public void remove(Card toRemove) {
        cards.remove(toRemove);
    }

    public void setName(Name newName) {
        this.name = newName;
    }

    /**
     * Replaces the equivalent card from the list with the new card.
     * The old card must exist in the list and the new card must not already exist in the list.
     */
    public void replace(Card toRemove, Card toAdd) {
        cards.replace(toRemove, toAdd);
    }

    /**
     * Returns true if both decks have the same name.
     * This defines a weaker notion of equality between two decks.
     */
    public boolean isSameDeck(Deck otherDeck) {
        if (otherDeck == this) {
            return true;
        }

        return otherDeck != null
                && otherDeck.getName().equalsLowerCase(getName());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Card> asUnmodifiableObservableList() {
        return cards.asUnmodifiableObservableList();
    }

    public ObservableList<Card> asObservableList() {
        return cards.asObservableList();
    }

    /**
     * Returns true if both decks have the same identity and data fields.
     * This defines a stronger notion of equality between two decks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deck)) {
            return false;
        }

        Deck otherDeck = (Deck) other;
        return otherDeck.getName().equalsLowerCase(getName());
    }

    @Override
    public int hashCode() {
        // use method for custom field hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());

        return builder.toString();
    }
}
