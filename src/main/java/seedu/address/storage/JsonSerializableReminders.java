package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderTAble;

/**
 * An Immutable ReminderTAble that is serializable to JSON format.
 */
@JsonRootName(value = "reminders")
class JsonSerializableReminders {

    public static final String MESSAGE_DUPLICATE_REMINDER = "Reminders list contains duplicate reminder(s).";

    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableReminders} with the given reminders.
     */
    @JsonCreator
    public JsonSerializableReminders(@JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        this.reminders.addAll(reminders);
    }

    /**
     * Converts a given {@code ReadOnlyReminder} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableReminders}.
     */
    public JsonSerializableReminders(ReadOnlyReminder source) {
        reminders.addAll(source.getAllReminders().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this ReminderTAble into the model's {@code ReminderStorage} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReminderTAble toModelType() throws IllegalValueException {
        ReminderTAble reminderTAble = new ReminderTAble();
        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (reminderTAble.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDER);
            }
            reminderTAble.addReminder(reminder);
        }
        return reminderTAble;
    }

}
