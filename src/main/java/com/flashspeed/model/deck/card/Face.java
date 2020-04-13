package com.flashspeed.model.deck.card;

/**
 * Represents a face of a card.
 */
public abstract class Face {
    private final String value;

    public Face(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if both faces have the same value.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Face)) {
            return false;
        }

        Face otherFace = (Face) other;
        return otherFace.getValue().equals(getValue());
    }
}
