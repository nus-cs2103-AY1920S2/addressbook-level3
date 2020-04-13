//@@author yapdianhao
package seedu.jelphabot.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.reminder.Reminder;

/**
 * An Immutable JelphaBot that is serializable to JSON format.
 */
@JsonRootName(value = "reminders")
class JsonSerializableReminderJelphaBot {

    public static final String MESSAGE_DUPLICATE_REMINDERS = "Reminders list contains duplicate reminder(s).";

    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableJelphaBot} with the given reminders.
     */
    @JsonCreator
    public JsonSerializableReminderJelphaBot(@JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        this.reminders.addAll(reminders);
    }

    /**
     * Converts a given {@code ReadOnlyJelphaBot} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableJelphaBot}.
     */
    public JsonSerializableReminderJelphaBot(ReadOnlyJelphaBot source) {
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this task list into the model's {@code JelphaBot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public JelphaBot toModelType() throws IllegalValueException {
        JelphaBot jelphaBot = new JelphaBot();
        // jelphabot's reminders.
        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (jelphaBot.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDERS);
            }
            jelphaBot.addReminder(reminder);
        }
        return jelphaBot;
    }

}
