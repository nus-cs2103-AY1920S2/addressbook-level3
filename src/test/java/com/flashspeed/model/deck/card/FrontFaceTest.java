package com.flashspeed.model.deck.card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FrontFaceTest {

    private FrontFace ff1 = new FrontFace("front value");
    private FrontFace ff2 = new FrontFace("front value");

    @Test
    void getValue() {
        assertEquals(ff1.getValue(), "front value");
    }

    @Test
    void testToString() {
        assertEquals(ff1.toString(), "front value");
    }

    @Test
    void testEquals() {
        assertEquals(ff1, ff2);
    }
}
