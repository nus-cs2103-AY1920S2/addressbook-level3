package com.flashspeed.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class CardUtils {

    public static final String EMPTY_FACE = "";

    public static final Card JAP_CARD_1 = new Card(new FrontFace("Hello"), new BackFace(" こんにちは"));

    public static final Card JAP_CARD_2 = new Card(new FrontFace("Goodbye"),
            new BackFace(" さよなら"));

    public static final Card JAP_CARD_3 = new Card(new FrontFace("Thank you"),
            new BackFace(" ありがとう"));

    public static final Card MALAY_CARD_1 = new Card(new FrontFace("I"), new BackFace("Saya"));

    public static final Card MALAY_CARD_2 = new Card(new FrontFace("You"), new BackFace("Awak"));

    public static final Card MALAY_CARD_3 = new Card(new FrontFace("Thank you"),
            new BackFace("Terima Kasih"));

    public static final List<Card> JAP_CARDS = getJapCards();
    public static final List<Card> MALAY_CARDS = getMalayCards();

    private CardUtils() {} // prevents instantiation

    /**
     * Gets the Japanese test deck.
     */
    public static List<Card> getJapCards() {
        return new ArrayList<>(Arrays.asList(JAP_CARD_1, JAP_CARD_2, JAP_CARD_3));
    }

    /**
     * Gets the Malay test deck.
     */
    public static List<Card> getMalayCards() {
        return new ArrayList<>(Arrays.asList(MALAY_CARD_1, MALAY_CARD_2, MALAY_CARD_3));
    }
}
