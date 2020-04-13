package com.flashspeed.testutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_NAME = "Japanese";
    public static final Deck DEFAULT_DECK = new Deck(new Name("Default deck"));

    private FrontFace frontFace;
    private BackFace backFace;

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        this.frontFace = cardToCopy.getFrontFace();
        this.backFace = cardToCopy.getBackFace();
    }

    /**
     * Default constructor.
     */
    public CardBuilder() {

    }

    /**
     * Sets the front face of the card that we are building.
     * @param front
     */
    public CardBuilder withFrontFace(String front) {
        FrontFace frontFace = new FrontFace(front);
        this.frontFace = frontFace;
        return this;
    }

    /**
     * Sets the back face of the card that we are building.
     * @param back
     */
    public CardBuilder withBackFace(String back) {
        BackFace backFace = new BackFace(back);
        this.backFace = backFace;
        return this;
    }

    /**
     * Creates a CardBuilder of Cards with values given by parameters in input.
     *
     * @param input parameters of card faces
     * @return CardBuilder of Cards with values given by parameters in input
     */
    public CardBuilder withInput(String input) {
        final Pattern inputFormat = Pattern.compile("(?<front>.*)"
                + "(\\s*[\u003a\u02d0\u02d1\u02f8\u05c3\u2236\u2360\ua789\ufe13\uff1a\ufe55]\\s*)"
                + "(?<back>.*)");
        final Matcher matcher = inputFormat.matcher(input.strip());
        final String frontValue = matcher.group("front");
        final String backValue = matcher.group("back");

        this.frontFace = new FrontFace(frontValue);
        this.backFace = new BackFace(backValue);
        return this;
    }

    /**
     * Builds the card with the intended front face and back face value.
     *
     * @return A Card object.
     */
    public Card build() {
        return new Card(frontFace, backFace);
    }

}
