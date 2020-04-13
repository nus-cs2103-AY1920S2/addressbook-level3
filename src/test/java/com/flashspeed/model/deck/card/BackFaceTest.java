package com.flashspeed.model.deck.card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BackFaceTest {

    private BackFace bf1 = new BackFace("back value");
    private BackFace bf2 = new BackFace("back value");

    @Test
    void getValue() {
        assertEquals(bf1.getValue(), "back value");
    }

    @Test
    void testToString() {
        assertEquals(bf1.toString(), "back value");
    }

    @Test
    void testEquals() {
        assertEquals(bf1, bf2);
    }
}
