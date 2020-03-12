package seedu.address.testutil;

import seedu.address.model.good.Good;

/**
 * A utility class containing a list of {@code Good} objects to be used in tests.
 */
public class TypicalGoods {

    public static final Good APPLE = new GoodBuilder().withGoodName("Fuji apple")
            .withGoodQuantity(15).build();

    public static final Good BANANA = new GoodBuilder().withGoodName("Brazil banana")
            .withGoodQuantity(10).build();

}
