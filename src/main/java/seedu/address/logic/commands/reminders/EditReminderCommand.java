package seedu.address.logic.commands.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.Reminder;

/**
 * Edits the details of an existing reminder in TAble.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number used in the displayed reminder list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMINDER_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_REMINDER_DATE + "DATE] "
            + "[" + PREFIX_REMINDER_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMINDER_DESCRIPTION + "Return T02 midterms paper "
            + PREFIX_REMINDER_DATE + "2020-03-15 "
            + PREFIX_REMINDER_TIME + "15:00";

    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "Such a reminder already exists in TAble.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the reminder in the filtered reminder list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = new EditReminderDescriptor(editReminderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToEdit = lastShownList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        if (LocalDateTime.of(editedReminder.getDate(), editedReminder.getTime()).isBefore(LocalDateTime.now())) {
            throw new CommandException(Messages.MESSAGE_REMINDER_PAST_REMINDER);
        }

        if (!reminderToEdit.equals(editedReminder) && model.hasReminder(editedReminder)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        model.setReminder(reminderToEdit, editedReminder);
        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Reminder} with the details of {@code reminderToEdit}
     * edited with {@code editReminderDescriptor}.
     */
    private static Reminder createEditedReminder(Reminder reminderToEdit, EditReminderDescriptor
            editReminderDescriptor) {
        assert reminderToEdit != null;

        Description updatedDescription = editReminderDescriptor.getDescription()
                .orElse(reminderToEdit.getDescription());
        LocalDate updatedDate = editReminderDescriptor.getDate()
                .orElse(reminderToEdit.getDate());
        LocalTime updatedTime = editReminderDescriptor.getTime()
                .orElse(reminderToEdit.getTime());
        boolean done = reminderToEdit.getDone();

        return new Reminder(updatedDescription, updatedDate, updatedTime, done);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReminderCommand)) {
            return false;
        }

        // state check
        EditReminderCommand e = (EditReminderCommand) other;
        return index.equals(e.index)
                && editReminderDescriptor.equals(e.editReminderDescriptor);
    }

    /**
     * Stores the details to edit the reminder with. Each non-empty field value will replace the
     * corresponding field value of the reminder.
     */
    public static class EditReminderDescriptor {
        private Description description;
        private LocalDate date;
        private LocalTime time;

        public EditReminderDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            setDescription(toCopy.description);
            setDate(toCopy.date);
            setTime(toCopy.time);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, date, time);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime());
        }
    }
}
