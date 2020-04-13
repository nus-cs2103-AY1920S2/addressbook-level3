//@@author yapdianhao
package seedu.jelphabot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jelphabot.storage.JsonAdaptedReminder.INVALID_INDEX_MESSAGE_FORMAT;
import static seedu.jelphabot.storage.JsonAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.jelphabot.storage.JsonAdaptedReminder.NEGATIVE_INDEX_MESSAGE_FORMAT;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.model.reminder.ReminderDay;
import seedu.jelphabot.model.reminder.ReminderHour;

public class JsonAdaptedReminderTest {

    private static final String NEGATIVE_INDEX = "-1";
    private static final String INVALID_INDEX = "!";
    private static final String INVALID_REMINDER_DAY = "30";
    private static final String INVALID_REMINDER_HOUR = "30";
    private static final String VALID_INDEX = "1";
    private static final String VALID_REMINDER_DAY = "3";
    private static final String VALID_REMINDER_HOUR = "10";

    @Test
    public void toModelType_validReminderDetails_returnsReminder() throws Exception {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(ASSESSMENT_REMINDER);
        assertEquals(ASSESSMENT_REMINDER, reminder.toModelType());
    }

    @Test
    public void toModelType_nullIndex_throwsIllegalValueException() {
        JsonAdaptedReminder nullIndexReminder = new JsonAdaptedReminder(null, VALID_REMINDER_DAY, VALID_REMINDER_HOUR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Index.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, nullIndexReminder::toModelType);
    }

    @Test
    public void toModelType_negativeIndex_throwsIllegalValueException() {
        JsonAdaptedReminder negativeIndexReminder =
            new JsonAdaptedReminder(NEGATIVE_INDEX, VALID_REMINDER_DAY, VALID_REMINDER_HOUR);
        String expectedMessage = NEGATIVE_INDEX_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, negativeIndexReminder::toModelType);
    }

    @Test
    public void toModelType_invalidIndex_throwsIllegalValueException() {
        JsonAdaptedReminder invalidIndexReminder =
            new JsonAdaptedReminder(INVALID_INDEX, VALID_REMINDER_DAY, VALID_REMINDER_HOUR);
        String expectedMessage = INVALID_INDEX_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, invalidIndexReminder::toModelType);
    }

    @Test
    public void toModelType_nullReminderDay_throwsIllegalValueException() {
        JsonAdaptedReminder nullReminderDayReminder =
            new JsonAdaptedReminder(VALID_INDEX, null, VALID_REMINDER_HOUR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ReminderDay.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, nullReminderDayReminder::toModelType);
    }

    @Test
    public void toModelType_invalidReminderDay_throwsIllegalValueException() {
        JsonAdaptedReminder invalidReminderDayReminder =
            new JsonAdaptedReminder(VALID_INDEX, INVALID_REMINDER_DAY, VALID_REMINDER_HOUR);
        String expectedMessage = ReminderDay.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, invalidReminderDayReminder::toModelType);
    }

    @Test
    public void toModelType_nullReminderHour_throwsIllegalValueException() {
        JsonAdaptedReminder nullReminderHourReminder =
            new JsonAdaptedReminder(VALID_INDEX, VALID_REMINDER_DAY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ReminderHour.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, nullReminderHourReminder::toModelType);
    }

    @Test
    public void toModelType_invalidReminderHour_throwsIllegalValueException() {
        JsonAdaptedReminder invalidReminderHourReminder =
            new JsonAdaptedReminder(VALID_INDEX, VALID_REMINDER_DAY, INVALID_REMINDER_HOUR);
        String expectedMessage = ReminderHour.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, invalidReminderHourReminder::toModelType);
    }
}
