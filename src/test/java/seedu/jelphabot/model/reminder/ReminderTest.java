//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_ASSESSMENT_REMINDER_DAY;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_ASSESSMENT_REMINDER_HOUR;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_REMINDER_INDEX;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_BOOK_REPORT_REMINDER_DAY;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_BOOK_REPORT_REMINDER_HOUR;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_BOOK_REPORT_REMINDER_INDEX;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalReminders.BOOK_REPORT_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.testutil.ReminderBuilder;

public class ReminderTest {

    @Test
    public void isSameReminder() {
        assertTrue(ASSESSMENT_REMINDER.isSameReminder(ASSESSMENT_REMINDER));

        assertFalse(ASSESSMENT_REMINDER.isSameReminder(null));

        // different reminder day -> returns True if index same
        Reminder editedAssessmentReminder = new ReminderBuilder(ASSESSMENT_REMINDER)
                                                .withReminderDay(VALID_BOOK_REPORT_REMINDER_DAY)
                                                .build();
        assertTrue(ASSESSMENT_REMINDER.isSameReminder(editedAssessmentReminder));

        // different reminder hour  -> returns true
        editedAssessmentReminder = new ReminderBuilder(ASSESSMENT_REMINDER)
                                       .withReminderHour(VALID_BOOK_REPORT_REMINDER_HOUR).build();
        assertTrue(ASSESSMENT_REMINDER.isSameReminder(editedAssessmentReminder));

        // different index -> returns false
        editedAssessmentReminder = new ReminderBuilder(ASSESSMENT_REMINDER)
                                       .withIndex(VALID_BOOK_REPORT_REMINDER_INDEX).build();
        assertFalse(ASSESSMENT_REMINDER.isSameReminder(editedAssessmentReminder));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder assessmentReminderCopy = new ReminderBuilder(ASSESSMENT_REMINDER).build();
        assertEquals(ASSESSMENT_REMINDER, assessmentReminderCopy);

        // same object -> returns true
        assertEquals(ASSESSMENT_REMINDER, ASSESSMENT_REMINDER);

        // null -> returns false
        assertNotEquals(null, ASSESSMENT_REMINDER);

        // different type -> returns false
        assertNotEquals(5, ASSESSMENT_REMINDER);

        // different reminder -> returns false
        assertNotEquals(ASSESSMENT_REMINDER, BOOK_REPORT_REMINDER);

        // different reminder day -> returns true
        Reminder editedAssessmentReminder = new ReminderBuilder(ASSESSMENT_REMINDER)
                                                .withReminderDay(VALID_BOOK_REPORT_REMINDER_DAY)
                                                .build();
        assertEquals(ASSESSMENT_REMINDER, editedAssessmentReminder);

        // different reminder hour  -> returns true
        Reminder editedBookReminder = new ReminderBuilder(BOOK_REPORT_REMINDER)
                                          .withReminderHour(VALID_ASSESSMENT_REMINDER_HOUR)
                                          .build();
        assertEquals(BOOK_REPORT_REMINDER, editedBookReminder);

        editedBookReminder = new ReminderBuilder(BOOK_REPORT_REMINDER)
                                 .withIndex(VALID_ASSIGNMENT_REMINDER_INDEX)
                                 .build();
        assertNotEquals(BOOK_REPORT_REMINDER, editedBookReminder);

        editedBookReminder = new ReminderBuilder(BOOK_REPORT_REMINDER)
                                 .withReminderDay(VALID_ASSESSMENT_REMINDER_DAY)
                                 .withReminderHour(VALID_ASSESSMENT_REMINDER_HOUR)
                                 .build();
        assertEquals(BOOK_REPORT_REMINDER, editedBookReminder);

    }
}
