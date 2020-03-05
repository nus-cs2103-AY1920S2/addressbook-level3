package seedu.address.model.good;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_NAME_AVOCADO;
import static seedu.address.testutil.TypicalGoods.APPLE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GoodBuilder;

/**
 * A utility class to help with building Good objects.
 */
public class GoodTest {

    @Test
    public void isSameGood() {
        // same object -> returns true
        assertTrue(APPLE.isSameGood(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameGood(null));

        // different phone and email -> returns false
        Good editedApple = new GoodBuilder(APPLE).withGoodName(VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(APPLE.isSameGood(editedApple));

    }

}
