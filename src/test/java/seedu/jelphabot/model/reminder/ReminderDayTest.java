//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ReminderDayTest {

    @Test
    public void isValidReminderDay() {

        // invalid ReminderDay
        assertFalse(ReminderDay.isValidReminderDay("-1")); // negative days
        assertFalse(ReminderDay.isValidReminderDay("8")); // too far a reminder
        assertFalse(ReminderDay.isValidReminderDay("not valid"));

        // valid ReminderDay
        assertTrue(ReminderDay.isValidReminderDay("1")); // valid day
    }
}
