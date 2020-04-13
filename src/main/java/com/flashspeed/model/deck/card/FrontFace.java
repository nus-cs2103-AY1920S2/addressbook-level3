package com.flashspeed.model.deck.card;

/**
 * Represents the front face of a card.
 */
public class FrontFace extends Face {

    public FrontFace(String value) {
        super(value.strip());
    }
}
