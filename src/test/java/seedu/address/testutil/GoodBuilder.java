package seedu.address.testutil;

import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;

/**
 * A utility class to help with building Good objects.
 */
public class GoodBuilder {

    public static final String DEFAULT_GOOD_NAME = "Fuji Apple";

    private GoodName goodName;

    public GoodBuilder() {
        goodName = new GoodName(DEFAULT_GOOD_NAME);
    }

    /**
     * Initializes the GoodBuilder with the data of {@code goodToCopy}.
     */
    public GoodBuilder(Good goodToCopy) {
        goodName = goodToCopy.getGoodName();
    }

    /**
     * Sets the {@code GoodName} of the {@code Good} that we are building.
     */
    public GoodBuilder withGoodName(String goodName) {
        this.goodName = new GoodName(goodName);
        return this;
    }

    public Good build() {
        return new Good(goodName);
    }

}
