package com.flashspeed.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.flashspeed.commons.exceptions.IllegalValueException;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

/**
 * Jackson-friendly version of {@link Card}.
 */
class JsonAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card name is missing!";

    private final String frontFace;
    private final String backFace;

    /**
     * Constructs a {@code JsonAdaptedCard} with the given deck details.
     */
    @JsonCreator
    public JsonAdaptedCard(@JsonProperty("frontFace") String frontFace, @JsonProperty("backFace") String backFace) {
        this.frontFace = frontFace;
        this.backFace = backFace;
    }

    /**
     * Converts a given {@code Card} into this class for Jackson use.
     */
    public JsonAdaptedCard(Card card) {
        this.frontFace = card.getFrontFace().getValue();
        this.backFace = card.getBackFace().getValue();
    }

    /**
     * Getters for front face value.
     */
    public String getFrontFace() {
        return frontFace;
    }

    /**
     * Getters for Back face value.
     */
    public String getBackFace() {
        return backFace;
    }

    /**
     * Converts this Jackson-friendly adapted deck object into the model's {@code Card} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deck.
     */
    public Card toModelType() throws IllegalValueException {
        if (frontFace == null || backFace == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        final FrontFace front = new FrontFace(frontFace);
        final BackFace back = new BackFace(backFace);

        return new Card(front, back);
    }
}
