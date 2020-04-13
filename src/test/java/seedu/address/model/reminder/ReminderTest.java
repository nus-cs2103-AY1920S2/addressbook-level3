package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME2;
import static seedu.address.testutil.TypicalReminders.getReminder1;
import static seedu.address.testutil.TypicalReminders.getReminder2;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReminderBuilder;

public class ReminderTest {

    @Test
    public void equals() {
        Reminder reminder1 = getReminder1();
        Reminder reminder2 = getReminder2();

        // same values -> returns true
        Reminder reminderCopy = new ReminderBuilder(reminder1).build();
        assertTrue(reminder1.equals(reminderCopy));

        // same object -> returns true
        assertTrue(reminder1.equals(reminder1));

        // null -> returns false
        assertFalse(reminder1.equals(null));

        // different type -> returns false
        assertFalse(reminder1.equals(5));

        // different reminder -> returns false
        assertFalse(reminder1.equals(reminder2));

        // different description -> returns false
        Reminder editedReminder1 = new ReminderBuilder(reminder1).withDescription(VALID_DESCRIPTION2).build();
        assertFalse(reminder1.equals(editedReminder1));

        // different date -> returns false
        editedReminder1 = new ReminderBuilder(reminder1).withDate(VALID_DATE2).build();
        assertFalse(reminder1.equals(editedReminder1));

        // different time -> returns false
        editedReminder1 = new ReminderBuilder(reminder1).withTime(VALID_TIME2).build();
        assertFalse(reminder1.equals(editedReminder1));
    }
}
