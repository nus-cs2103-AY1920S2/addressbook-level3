package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalUser.ALICE;
import static seedu.address.testutil.TypicalUser.BOB;

import org.junit.jupiter.api.Test;

public class ConfirmationEmailTest {

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ConfirmationEmail(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        ConfirmationEmail original = new ConfirmationEmail(ALICE);
        ConfirmationEmail confirmationEmailCopy = new ConfirmationEmail(ALICE);
        assertTrue(original.equals(confirmationEmailCopy));

        // same object -> returns true
        assertTrue(original.equals(original));

        // null -> returns false
        assertFalse(original.equals(null));

        // different type -> returns false
        assertFalse(original.equals(5));

        // different user -> returns false
        assertFalse(original.equals(new ConfirmationEmail(BOB)));
    }

    @Test
    public void hashcode() {
        ConfirmationEmail original = new ConfirmationEmail(ALICE);
        ConfirmationEmail confirmationEmailCopy = new ConfirmationEmail(ALICE);
        ConfirmationEmail differentUser = new ConfirmationEmail(BOB);

        // same values -> returns same hashcode
        assertEquals(original.hashCode(), confirmationEmailCopy.hashCode());

        // different user -> returns different hashcode
        assertNotEquals(original.hashCode(), differentUser.hashCode());
    }
}
