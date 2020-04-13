//@@author yapdianhao
package seedu.jelphabot.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderDay;
import seedu.jelphabot.model.reminder.ReminderHour;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder %s's field is missing!";
    public static final String NEGATIVE_INDEX_MESSAGE_FORMAT = "Index should not be negative!";
    public static final String INVALID_INDEX_MESSAGE_FORMAT = "Index should be a positive number!";

    private final String index;
    private final String reminderDay;
    private final String reminderHour;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder (
        @JsonProperty("index") String index,
        @JsonProperty("reminderDay") String reminderDay,
        @JsonProperty("reminderHour") String reminderHour
    ) {
        this.index = index;
        this.reminderDay = reminderDay;
        this.reminderHour = reminderHour;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder reminder) {
        this.index = reminder.getIndex().getZeroBased() + "";
        this.reminderDay = reminder.getDaysToRemind().getReminderDay() + "";
        this.reminderHour = reminder.getHoursToRemind().getReminderHour() + "";
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's
     * {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted reminder.
     */
    public Reminder toModelType() throws IllegalValueException {
        try {
            if (index == null) {
                throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Index.class.getSimpleName()));
            }

            if (Integer.parseInt(index) < 0) {
                throw new IllegalValueException(NEGATIVE_INDEX_MESSAGE_FORMAT);
            }
            if (reminderDay == null) {
                throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ReminderDay.class.getSimpleName()));
            }
            if (!ReminderDay.isValidReminderDay(reminderDay)) {
                throw new IllegalValueException(ReminderDay.MESSAGE_CONSTRAINTS);
            }
            if (reminderHour == null) {
                throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ReminderHour.class.getSimpleName()));
            }
            if (!ReminderHour.isValidReminderHour(reminderHour)) {
                throw new IllegalValueException(
                    String.format(ReminderHour.MESSAGE_CONSTRAINTS));
            }
            return new Reminder(
                Index.fromZeroBased(Integer.parseInt(index)),
                new ReminderDay(reminderDay),
                new ReminderHour(reminderHour)
            );
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException(NEGATIVE_INDEX_MESSAGE_FORMAT);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(INVALID_INDEX_MESSAGE_FORMAT);
        }
    }
}
