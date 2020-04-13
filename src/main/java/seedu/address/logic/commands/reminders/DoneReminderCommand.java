package seedu.address.logic.commands.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Marks a identified reminder as done using it's displayed index from the Reminder TAble.
 */
public class DoneReminderCommand extends Command {

    public static final String COMMAND_WORD = "doneReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the reminder identified by the index number used in the displayed reminder list as done.\n"
            + "Parameters: " + PREFIX_INDEX + "\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_REMINDER_SUCCESS = "Reminder done: %1$s";

    private final Index targetIndex;

    public DoneReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToMark = lastShownList.get(targetIndex.getZeroBased());

        if (reminderToMark.getDone()) {
            throw new CommandException(Messages.MESSAGE_REMINDER_ALREADY_DONE);
        }

        Reminder doneReminder = model.doneReminder(reminderToMark);
        return new CommandResult(String.format(MESSAGE_DONE_REMINDER_SUCCESS, doneReminder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneReminderCommand // instanceof handles nulls
                && targetIndex.equals(((DoneReminderCommand) other).targetIndex)); // state check
    }
}
