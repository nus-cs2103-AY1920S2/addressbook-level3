package com.flashspeed.model.deck.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class CardTest {

    private FrontFace ff = new FrontFace("front value");
    private BackFace bf = new BackFace("back value");
    private Card card1 = new Card(ff, bf);
    private Card card2 = new Card(ff, bf);

    @Test
    void getFrontFace() {
        assertSame(card1.getFrontFace(), ff);
    }

    @Test
    void getBackFace() {
        assertSame(card1.getBackFace(), bf);
    }

    @Test
    void testEquals() {
        assertEquals(card1, card2);
    }

    @Test
    void testToString() {
        assertEquals(card1.toString(), "front value : back value");
    }
}
