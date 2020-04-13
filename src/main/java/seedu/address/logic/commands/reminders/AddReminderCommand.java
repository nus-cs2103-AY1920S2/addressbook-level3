package seedu.address.logic.commands.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;

import java.time.LocalDateTime;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Adds a reminder into TAble.
 */
public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to TAble. \n"
                + "Parameters: "
                + PREFIX_REMINDER_DESCRIPTION + "DESC "
                + PREFIX_REMINDER_DATE + "DATE "
                + PREFIX_REMINDER_TIME + "TIME\n"
                + "Example: " + COMMAND_WORD + " "
                + PREFIX_REMINDER_DESCRIPTION + "Mark midterms papers "
                + PREFIX_REMINDER_DATE + "2020-03-05 "
                + PREFIX_REMINDER_TIME + "14:00 ";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in TAble";

    private final Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code Reminder}
     */
    public AddReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (LocalDateTime.of(toAdd.getDate(), toAdd.getTime()).isBefore(LocalDateTime.now())) {
            throw new CommandException(Messages.MESSAGE_REMINDER_PAST_REMINDER);
        }

        if (model.hasReminder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        model.addReminder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
