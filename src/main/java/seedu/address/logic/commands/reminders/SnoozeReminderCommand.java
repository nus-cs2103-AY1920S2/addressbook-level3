package seedu.address.logic.commands.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_MINUTE;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.Reminder;

/**
 * Extends a reminder deadline using it's displayed index from the Reminder TAble.
 */
public class SnoozeReminderCommand extends Command {

    public static final String COMMAND_WORD = "snoozeReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Extends the deadline of the reminder identified by the index number "
            + "used in the displayed reminder list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMINDER_DAY + "DAY] "
            + "[" + PREFIX_REMINDER_HOUR + "HOUR] "
            + "[" + PREFIX_REMINDER_MINUTE + "MINUTE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMINDER_DAY + "3 "
            + PREFIX_REMINDER_HOUR + "2 "
            + PREFIX_REMINDER_MINUTE + "20";

    public static final String MESSAGE_SNOOZE_REMINDER_SUCCESS = "Snoozed reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one of day, hour or minute must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "Such a reminder already exists in TAble.";
    public static final String MESSAGE_MAXIMUM_DATE = "The maximum supported date is '+999999999-12-31'.";

    private final Index index;
    private final long dayToSnooze;
    private final long hourToSnooze;
    private final long minuteToSnooze;

    /**
     * @param index of the reminder in the filtered reminder list to snooze
     * @param dayToSnooze day to snooze the reminder by
     * @param hourToSnooze hour to snooze the reminder by
     * @param minuteToSnooze minute to snooze the reminder by
     */
    public SnoozeReminderCommand(Index index, long dayToSnooze, long hourToSnooze, long minuteToSnooze) {
        requireNonNull(index);
        requireAllNonNull(dayToSnooze, hourToSnooze, minuteToSnooze);

        this.index = index;
        this.dayToSnooze = dayToSnooze;
        this.hourToSnooze = hourToSnooze;
        this.minuteToSnooze = minuteToSnooze;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToSnooze = lastShownList.get(index.getZeroBased());

        if (reminderToSnooze.getDone()) {
            throw new CommandException(Messages.MESSAGE_REMINDER_ALREADY_DONE);
        }

        LocalDateTime dateTime = LocalDateTime.of(reminderToSnooze.getDate(), reminderToSnooze.getTime());;
        LocalDateTime updatedDateTime;

        try {
            updatedDateTime = dateTime.plusDays(dayToSnooze).plusHours(hourToSnooze).plusMinutes(minuteToSnooze);
        } catch (DateTimeException e) {
            throw new CommandException(MESSAGE_MAXIMUM_DATE);
        }

        EditReminderCommand.EditReminderDescriptor editReminderDescriptor =
                new EditReminderCommand.EditReminderDescriptor();
        editReminderDescriptor.setDate(updatedDateTime.toLocalDate());
        editReminderDescriptor.setTime(updatedDateTime.toLocalTime());

        Reminder snoozedReminder = createSnoozedReminder(reminderToSnooze, editReminderDescriptor);

        if (!reminderToSnooze.equals(snoozedReminder) && model.hasReminder(snoozedReminder)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        model.setReminder(reminderToSnooze, snoozedReminder);
        return new CommandResult(String.format(MESSAGE_SNOOZE_REMINDER_SUCCESS, snoozedReminder));
    }

    /**
     * Creates and returns a {@code Reminder} with the details of {@code reminderToSnooze}
     * edited with {@code editReminderDescriptor}.
     */
    private static Reminder createSnoozedReminder(Reminder reminderToSnooze, EditReminderCommand.EditReminderDescriptor
            editReminderDescriptor) {
        assert reminderToSnooze != null;

        Description description = reminderToSnooze.getDescription();
        LocalDate updatedDate = editReminderDescriptor.getDate()
                .orElse(reminderToSnooze.getDate());
        LocalTime updatedTime = editReminderDescriptor.getTime()
                .orElse(reminderToSnooze.getTime());
        boolean done = reminderToSnooze.getDone();

        return new Reminder(description, updatedDate, updatedTime, done);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SnoozeReminderCommand)) {
            return false;
        }

        // state check
        SnoozeReminderCommand e = (SnoozeReminderCommand) other;
        return index.equals(e.index)
                && dayToSnooze == e.dayToSnooze
                && hourToSnooze == e.hourToSnooze
                && minuteToSnooze == e.minuteToSnooze;
    }
}
