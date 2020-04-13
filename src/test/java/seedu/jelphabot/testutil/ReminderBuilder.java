//@@author yapdianhao
package seedu.jelphabot.testutil;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderDay;
import seedu.jelphabot.model.reminder.ReminderHour;

/**
 * A utility class to help build Reminder objects.
 */
public class ReminderBuilder {

    public static final int DEFAULT_INDEX = 1;
    public static final String DEFAULT_REMINDERDAY = "1";
    public static final String DEFAULT_REMINDERHOUR = "1";

    private Index index;
    private ReminderDay reminderDay;
    private ReminderHour reminderHour;

    public ReminderBuilder() {
        index = Index.fromOneBased(DEFAULT_INDEX);
        reminderDay = new ReminderDay(DEFAULT_REMINDERDAY);
        reminderHour = new ReminderHour(DEFAULT_REMINDERHOUR);
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     * @param reminderToCopy
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        index = reminderToCopy.getIndex();
        reminderDay = reminderToCopy.getDaysToRemind();
        reminderHour = reminderToCopy.getHoursToRemind();
    }

    /**
     * Sets the {@code Index} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withIndex(String index) {
        this.index = Index.fromZeroBased(Integer.parseInt(index));
        return this;
    }

    /**
     * Sets the {@code ReminderDay} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminderDay(String reminderDay) {
        this.reminderDay = new ReminderDay(reminderDay);
        return this;
    }

    /**
     * Sets the {@code ReminderHour} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminderHour(String reminderHour) {
        this.reminderHour = new ReminderHour(reminderHour);
        return this;
    }

    public Reminder build() {
        return new Reminder(index, reminderDay, reminderHour);
    }
}
