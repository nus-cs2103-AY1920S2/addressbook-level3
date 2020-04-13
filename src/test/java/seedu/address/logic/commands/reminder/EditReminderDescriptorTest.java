package seedu.address.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REMINDER1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REMINDER2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.reminders.EditReminderCommand.EditReminderDescriptor;
import seedu.address.testutil.EditReminderDescriptorBuilder;

public class EditReminderDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditReminderDescriptor descriptorWithSameValues = new EditReminderDescriptor(DESC_REMINDER1);
        assertTrue(DESC_REMINDER1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_REMINDER1.equals(DESC_REMINDER1));

        // null -> returns false
        assertFalse(DESC_REMINDER1.equals(null));

        // different types -> returns false
        assertFalse(DESC_REMINDER1.equals(5));

        // different values -> returns false
        assertFalse(DESC_REMINDER1.equals(DESC_REMINDER2));

        // different description -> returns false
        EditReminderDescriptor editedReminder1 = new EditReminderDescriptorBuilder(DESC_REMINDER1)
                .withDescription(VALID_DESCRIPTION2).build();
        assertFalse(DESC_REMINDER1.equals(editedReminder1));

        // different date -> returns false
        editedReminder1 = new EditReminderDescriptorBuilder(DESC_REMINDER1).withDate(VALID_DATE2).build();
        assertFalse(DESC_REMINDER1.equals(editedReminder1));

        // different time -> returns false
        editedReminder1 = new EditReminderDescriptorBuilder(DESC_REMINDER1).withTime(VALID_TIME2).build();
        assertFalse(DESC_REMINDER1.equals(editedReminder1));
    }
}
