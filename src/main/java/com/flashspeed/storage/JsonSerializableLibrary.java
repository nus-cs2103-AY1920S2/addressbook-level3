package com.flashspeed.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.flashspeed.commons.exceptions.IllegalValueException;
import com.flashspeed.model.Library;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.deck.Deck;

/**
 * An Immutable Library that is serializable to JSON format.
 */
@JsonRootName(value = "decks")
class JsonSerializableLibrary {

    public static final String MESSAGE_DUPLICATE_DECK = "Decks list contains duplicate deck(s).";

    private final List<JsonAdaptedDeck> decks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLibrary} with the given decks.
     */
    @JsonCreator
    public JsonSerializableLibrary(@JsonProperty("decks") List<JsonAdaptedDeck> decks) {
        this.decks.addAll(decks);
    }

    /**
     * Converts a given {@code ReadOnlyLibrary} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableLibrary}.
     */
    public JsonSerializableLibrary(ReadOnlyLibrary source) {
        decks.addAll(source.getDeckList().stream().map(JsonAdaptedDeck::new).collect(Collectors.toList()));
    }

    /**
     * Converts this library into the model's {@code Library} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Library toModelType() throws IllegalValueException {
        Library library = new Library();
        for (JsonAdaptedDeck jsonAdaptedDeck : decks) {
            Deck deck = jsonAdaptedDeck.toModelType();
            if (library.hasDeck(deck)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DECK);
            }
            library.addDeck(deck);
        }
        return library;
    }
}
