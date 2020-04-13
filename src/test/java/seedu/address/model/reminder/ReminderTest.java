package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalUser.ALICE;
import static seedu.address.testutil.TypicalUser.BOB;

import org.junit.jupiter.api.Test;

public class ReminderTest {

    @Test
    public void constructor_nullSender_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, BENSON));
    }

    @Test
    public void constructor_nullReceiver_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(ALICE, null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder original = new Reminder(ALICE, BENSON);
        Reminder reminderCopy = new Reminder(ALICE, BENSON);
        assertTrue(original.equals(reminderCopy));

        // same object -> returns true
        assertTrue(original.equals(original));

        // null -> returns false
        assertFalse(original.equals(null));

        // different type -> returns false
        assertFalse(original.equals(5));

        // different sender -> returns false
        assertFalse(original.equals(new Reminder(BOB, BENSON)));

        // different receiver -> returns false
        assertFalse(original.equals(new Reminder(ALICE, CARL)));
    }

    @Test
    public void hashcode() {
        Reminder original = new Reminder(ALICE, BENSON);
        Reminder reminderCopy = new Reminder(ALICE, BENSON);
        Reminder differentSender = new Reminder(BOB, BENSON);
        Reminder differentReceiver = new Reminder(ALICE, CARL);

        // same values -> returns same hashcode
        assertEquals(original.hashCode(), reminderCopy.hashCode());

        // different sender -> returns different hashcode
        assertNotEquals(original.hashCode(), differentSender.hashCode());

        // different receiver -> returns different hashcode
        assertNotEquals(original.hashCode(), differentReceiver.hashCode());
    }
}
