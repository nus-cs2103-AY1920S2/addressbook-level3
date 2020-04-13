package com.flashspeed.model.deck.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.model.deck.exceptions.CardNotFoundException;
import com.flashspeed.model.deck.exceptions.DuplicateCardException;
import com.flashspeed.testutil.Assert;

class UniqueCardListTest {

    private UniqueCardList ucl = new UniqueCardList();
    private UniqueCardList ucl2 = new UniqueCardList();
    private UniqueCardList ucl3 = new UniqueCardList();

    private FrontFace ff1 = new FrontFace("front value 1");
    private FrontFace ff2 = new FrontFace("front value 2");
    private BackFace bf1 = new BackFace("back value 1");
    private BackFace bf2 = new BackFace("back value 2");

    private Card nonExistentCard = new Card(new FrontFace("new front 1"), new BackFace("new back 2"));

    private Card card1 = new Card(ff1, bf1);
    private Card card2 = new Card(ff1, bf2);
    private Card card3 = new Card(ff2, bf1);
    private Card card4 = new Card(ff2, bf2);

    UniqueCardListTest() {
        ucl.add(card1);
        ucl.add(card2);
        ucl.add(card3);
        ucl.add(card4);

        ucl2.add(card1);
        ucl2.add(card2);
        ucl2.add(card3);
        ucl2.add(card4);

        ucl3.add(card2);
        ucl3.add(card3);
    }

    @Test
    void contains() {
        assertTrue(ucl.contains(card1));
        assertTrue(ucl.contains(card2));
        assertTrue(ucl.contains(card3));
        assertTrue(ucl.contains(card4));
    }

    @Test
    void getSize() {
        assertEquals(ucl.getSize(), 4);
    }

    @Test
    void add_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ucl.add(null));
    }

    @Test
    void add_duplicateCard_throwsDuplicateCardException() {
        Assert.assertThrows(DuplicateCardException.class, () -> ucl.add(card1));
        Assert.assertThrows(DuplicateCardException.class, () -> ucl.add(card2));
        Assert.assertThrows(DuplicateCardException.class, () -> ucl.add(card3));
        Assert.assertThrows(DuplicateCardException.class, () -> ucl.add(card4));
    }

    @Test
    void getCard_validIndex_success() {
        assertSame(ucl.getCard(Index.fromZeroBased(0)), card1);
        assertSame(ucl.getCard(Index.fromZeroBased(1)), card2);
        assertSame(ucl.getCard(Index.fromZeroBased(2)), card3);
        assertSame(ucl.getCard(Index.fromZeroBased(3)), card4);
    }

    @Test
    void getCard_invalidIndex_throwsIndexOutOfBoundsException() {
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> ucl.getCard(Index.fromZeroBased(4)));
    }

    @Test
    void replace_nonExistentCard_throwsCardNotFoundException() {
        Assert.assertThrows(CardNotFoundException.class, () -> ucl.replace(nonExistentCard, card1));
    }

    @Test
    void testEquals() {
        assertEquals(ucl, ucl2);
        assertNotEquals(ucl, ucl3);
    }
}
