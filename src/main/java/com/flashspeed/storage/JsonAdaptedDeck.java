package com.flashspeed.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.flashspeed.commons.exceptions.IllegalValueException;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

/**
 * Jackson-friendly version of {@link Deck}.
 */
class JsonAdaptedDeck {

    public static final String MISSING_FIELD_MESSAGE_FORMAT =
            "Names should contain at least one (non-whitespace) character, i.e. should not be blank";
    private String name;
    private List<JsonAdaptedCard> cards = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDeck} with the given deck details.
     */
    @JsonCreator
    public JsonAdaptedDeck(@JsonProperty("name") String name, @JsonProperty("cards") List<JsonAdaptedCard> cards) {
        this.name = name;
        this.cards.addAll(cards);
    }

    /**
     * Converts a given {@code Deck} into this class for Jackson use.
     */
    public JsonAdaptedDeck(Deck source) {
        name = source.getName().name;
        this.cards = source.asUnmodifiableObservableList()
                .stream()
                .map(card -> new JsonAdaptedCard(card))
                .collect(Collectors.toList());
    }

    public JsonAdaptedDeck(Name name, List<Card> cards) throws IllegalValueException {
        this.name = name.name;
        if (cards == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        this.cards = cards.stream().map(card -> new JsonAdaptedCard(card))
                .collect(Collectors.toList());

    }

    /**
     * Changes the name of the JSON deck.
     *
     * @param name The new name of the deck.
     * @return The deck with the new name.
     */
    public JsonAdaptedDeck setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Converts this Jackson-friendly adapted deck object into the model's {@code Deck} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deck.
     */
    public Deck toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }

        Deck modelDeck = new Deck(new Name(name));
        for (JsonAdaptedCard card : cards) {
            FrontFace newFrontFace = new FrontFace(card.getFrontFace());
            BackFace newBackFace = new BackFace(card.getBackFace());
            modelDeck.add(new Card(newFrontFace, newBackFace));
        }

        return modelDeck;
    }
}
