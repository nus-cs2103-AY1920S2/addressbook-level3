package com.flashspeed.model.deck.card;

/**
 * Represents the back face of a card.
 */
public class BackFace extends Face {

    public BackFace(String value) {
        super(value.strip());
    }
}
