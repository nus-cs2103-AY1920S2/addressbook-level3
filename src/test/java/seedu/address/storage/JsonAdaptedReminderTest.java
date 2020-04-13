package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.getReminder1;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedReminderTest {
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_DATE = "2020-25-36";
    private static final String INVALID_TIME = "55:55";

    private static final String VALID_DESCRIPTION = getReminder1().getDescription().toString();
    private static final String VALID_DATE = getReminder1().getDate().toString();
    private static final String VALID_TIME = getReminder1().getTime().toString();


    @Test
    public void toModelType_validReminderDetails_returnsReminder() throws Exception {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(getReminder1());
        assertEquals(getReminder1(), reminder.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(INVALID_DESCRIPTION, VALID_DATE, VALID_TIME, "false");
        String expectedMessage = JsonAdaptedReminder.INVALID_DESCRIPTION;
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(null, VALID_DATE, VALID_TIME, "false");
        String expectedMessage = String.format(JsonAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT, "DESCRIPTION");
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(VALID_DESCRIPTION, INVALID_DATE, VALID_TIME, "false");
        String expectedMessage = JsonAdaptedReminder.INVALID_DATE_TIME_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_DESCRIPTION, null, VALID_TIME, "false");
        String expectedMessage = String.format(JsonAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT, "DATE");
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(VALID_DESCRIPTION, VALID_DATE, INVALID_TIME, "false");
        String expectedMessage = JsonAdaptedReminder.INVALID_DATE_TIME_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_DESCRIPTION, VALID_DATE, null, "false");
        String expectedMessage = String.format(JsonAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT, "TIME");
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }
}
