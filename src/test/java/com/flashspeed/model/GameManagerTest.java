package com.flashspeed.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.testutil.DeckUtils;

public class GameManagerTest {

    private GameManager gameManager = new GameManager(DeckUtils.JAPANESE_DECK);

    @Test
    public void flip_alreadyFlip_returnsNull() {
        gameManager.flip();
        BackFace backFace = gameManager.flip();
        assertEquals(backFace, null);
    }

    @Test
    public void getBackFace_flip_returnsTrue() {
        BackFace backFace = gameManager.flip();
        Card card = DeckUtils.JAPANESE_DECK.asUnmodifiableObservableList().get(gameManager.getCurrCardIdx());
        assertEquals(backFace, card.getBackFace());
    }

    @Test
    public void getNextCard_answerYes_returnsTrue() {
        gameManager.flip();
        Card nextCard = gameManager.answerYes();
        Card card = gameManager.getCards().get(gameManager.getCurrCardIdx());
        assertEquals(nextCard, card);
    }

    @Test
    public void getNextCard_answerNo_returnsTrue() {
        gameManager.flip();
        Card nextCard = gameManager.answerNo();
        Card card = gameManager.getCards().get(gameManager.getCurrCardIdx());
        assertEquals(nextCard, card);
    }



}
