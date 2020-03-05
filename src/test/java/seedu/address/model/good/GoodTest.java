package seedu.address.model.good;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOOD_NAME_AVOCADO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GoodBuilder;

/**
 * A utility class to help with building Good objects.
 */
public class GoodTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Good(null));
    }

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

    @Test
    public void equals() {
        // same values -> returns true
        Good appleCopy = new GoodBuilder(APPLE).build();
        assertTrue(APPLE.equals(appleCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different person -> returns false
        assertFalse(APPLE.equals(BOB));

        // different name -> returns false
        Good editedApple = new GoodBuilder(APPLE).withGoodName(VALID_GOOD_NAME_AVOCADO).build();
        assertFalse(APPLE.equals(editedApple));

    }

    @Test
    public void toStringTest() {
        Good testGood = new GoodBuilder().withGoodName(VALID_GOOD_NAME_AVOCADO).build();
        assertTrue(testGood.toString().equals(VALID_GOOD_NAME_AVOCADO));
    }

}
