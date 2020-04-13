//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static seedu.jelphabot.commons.util.AppUtil.checkArgument;

/**
 * Represents the days before the task dues in JelphaBot.
 */
public class ReminderDay {

    public static final String MESSAGE_CONSTRAINTS = "Reminder date should be within one week from now, and an integer";

    private final int reminderDay;

    public ReminderDay(String reminderDay) {
        checkArgument(isValidReminderDay(reminderDay), MESSAGE_CONSTRAINTS);
        this.reminderDay = Integer.parseInt(reminderDay);
    }

    public int getReminderDay() {
        return this.reminderDay;
    }

    /**
     * Returns a boolean to determine whether the days to remind is within 7 days.
     * @param dayToTest
     * @return boolean
     */
    public static boolean isValidReminderDay(String dayToTest) {
        try {
            int convertedDayToTest = Integer.parseInt(dayToTest);
            return 0 <= convertedDayToTest && convertedDayToTest <= 7;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns a boolean to determine whether two reminders share the same day.
     * @param other
     * @return boolean
     */
    public boolean equals(Object other) {
        return other == this
                   || (other instanceof ReminderDay && reminderDay
                                                           == ((ReminderDay) other).reminderDay);
    }
}
