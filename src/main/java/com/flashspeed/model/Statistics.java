package com.flashspeed.model;

import java.util.HashMap;
import java.util.Map;

import com.flashspeed.model.deck.card.Card;

import javafx.collections.ObservableList;

/**
 * Represents statistics report for one game session.
 */
public class Statistics {

    private static final String NEW_GAME_ERR_MSG = "The game has not started yet!";

    // number of correct answers
    private int correctAns;
    // number of wrong answer
    private int wrongAns;

    //Total number of questions played
    private int totalQns;

    // hash map to keep track the number of attempts to get the correct answer for each card
    private Map<Card, Integer> totalAttempts;
    private Map<Card, Integer> correctAttempts;
    private Map<Card, Integer> wrongAttempts;
    private Map<Card, Integer> cardTracker;

    Statistics(int correctAns, int wrongAns, int totalQns, Map<Card, Integer> totalAttempts,
               Map<Card, Integer> correctAttempts,
               Map<Card, Integer> wrongAttempts, ObservableList<Card> cards) {
        this.correctAns = correctAns;
        this.wrongAns = wrongAns;
        this.totalQns = totalQns;
        this.totalAttempts = totalAttempts;
        this.correctAttempts = correctAttempts;
        this.wrongAttempts = wrongAttempts;
        this.cardTracker = new HashMap<>() {
            {
                for (Card card: cards) {
                    put(card, 1);
                }
            }
        };
    }

    public Statistics(ObservableList<Card> cards) {
        this.correctAns = 0;
        this.wrongAns = 0;
        this.totalQns = 0;
        this.totalAttempts = new HashMap<>();
        this.correctAttempts = new HashMap<>();
        this.wrongAttempts = new HashMap<>();
        this.cardTracker = new HashMap<>();

        // initialize the number of attempt for each card as 0
        for (int i = 0; i < cards.size(); i++) {
            totalAttempts.put(cards.get(i), 0);
            correctAttempts.put(cards.get(i), 0);
            wrongAttempts.put(cards.get(i), 0);
            cardTracker.put(cards.get(i), 1);
        }
    }

    /**
     * Returns the number of correct answers so far.
     */
    public int getCorrectAns() {
        assert(correctAns >= 0);
        return this.correctAns;
    }

    /**
     * Returns the number of incorrect answers so far.
     */
    public int getWrongAns() {
        assert(wrongAns >= 0);
        return this.wrongAns;
    }

    /**
     * Returns the total number of cards played so far.
     */
    public int getTotalQns() {
        assert(totalQns >= 0);
        return this.totalQns;
    }

    /**
     * Calculates the current score of the game.
     * @return the current score of the game thus far.
     */
    public long getScore() throws ArithmeticException {
        if (totalQns == 0) {
            throw new ArithmeticException(NEW_GAME_ERR_MSG);
        }
        return Math.round(Double.valueOf(correctAns) / Double.valueOf(totalQns) * 100);
    }

    /**
     * Increments the number of attempts of a certain card.
     */
    private void incrementAttempt(Card card) {
        ++totalQns;
        totalAttempts.merge(card, 1, Integer::sum);
        assert(totalAttempts.get(card) > 0);
        assert(totalQns > 0);
    }

    /**
     * Increments the number of correct attempts of a certain card.
     */
    public void incrementCorrectAttempt(Card card) {
        ++correctAns;
        correctAttempts.merge(card, 1, Integer::sum);
        cardTracker.merge(card, -1, Integer::sum);
        incrementAttempt(card);
    }

    /**
     * Increments the number of correct attempts of a certain card.
     *
     * @return whether there are 2 cards in the current game
     */
    public boolean incrementWrongAttempt(Card card) {
        ++wrongAns;
        assert(wrongAns > 0);
        wrongAttempts.merge(card, 1, Integer::sum);
        incrementAttempt(card);
        int numCardsInDeck = cardTracker.containsKey(card) ? cardTracker.get(card) : 0;

        if (numCardsInDeck > 2) {
            // should never happen, but reset to 2 so no cards can be added.
            numCardsInDeck = 2;
            cardTracker.replace(card, 2);
        }

        if (numCardsInDeck < 2) {
            cardTracker.merge(card, 1, Integer::sum);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        int wrongCounter = 0;
        String output = "Cards Attempted: " + this.totalQns + "\n"
                + "Correct Attempts: " + this.correctAns + "\n"
                + "Wrong Attempts: " + this.wrongAns + "\n"
                + "Score: " + String.format("%2d", getScore()) + "% \n";

        for (Map.Entry<Card, Integer> entry: wrongAttempts.entrySet()) {
            if (entry.getValue() > 0) {
                wrongCounter++;
            }
        }

        if (this.totalQns == 0) {
            output += "You did not attempt any card!" + "\n";
        } else if (wrongCounter > 0) {
            output += "Here is a list of cards you guessed incorrectly:" + "\n";
            for (Map.Entry<Card, Integer> entry: wrongAttempts.entrySet()) {
                if (entry.getValue() > 0) {
                    output += String.format("%s : %s (%d %s)\n",
                            entry.getKey().getFrontFace().getValue(),
                            entry.getKey().getBackFace().getValue(),
                            entry.getValue(),
                            entry.getValue() == 1 ? "time" : "times");
                }
            }
        } else {
            output += "Congratulations! You got them all correct!" + "\n";
        }

        return output;
    }

}
