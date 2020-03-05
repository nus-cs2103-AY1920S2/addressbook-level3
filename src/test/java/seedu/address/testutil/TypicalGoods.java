package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_NAME_AVOCADO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_NAME_BLUEBERRY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.good.Good;

/**
 * A utility class containing a list of {@code Good} objects to be used in tests.
 */
public class TypicalGoods {

    public static final Good APPLE = new GoodBuilder().withGoodName("Fuji apple").build();

}
