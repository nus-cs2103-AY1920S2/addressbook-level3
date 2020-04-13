//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.AppUtil.checkArgument;

/**
 * Represent the hours before the task dues in JelphaBot.
 */
public class ReminderHour {

    public static final String MESSAGE_CONSTRAINTS = "Reminder hour should be within 24 hours from now, and an integer";

    private final int reminderHour;

    public ReminderHour(String reminderHour) {
        requireNonNull(reminderHour);
        checkArgument(isValidReminderHour(reminderHour), MESSAGE_CONSTRAINTS);
        this.reminderHour = Integer.parseInt(reminderHour);
    }

    /**
     * Returns a boolean to determine whether the days to remind is within 24 hours.
     * @param dayToTest
     * @return boolean
     */
    public static boolean isValidReminderHour(String hourToTest) {
        try {
            int convertedHourToTest = Integer.parseInt(hourToTest);
            return 0 <= convertedHourToTest && convertedHourToTest <= 24;
        } catch (Exception e) {
            return false;
        }
    }

    public int getReminderHour() {
        return this.reminderHour;
    }

    /**
     * Returns a boolean to determine whether two reminders share the same hour.
     * @param other
     * @return boolean
     */
    public boolean equals(Object other) {
        return other == this
                   || (other instanceof ReminderHour && reminderHour == ((ReminderHour) other).reminderHour);
    }
}
