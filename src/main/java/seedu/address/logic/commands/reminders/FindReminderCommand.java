package seedu.address.logic.commands.reminders;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.reminder.DatePredicate;
import seedu.address.model.reminder.DescriptionContainsKeywordsPredicate;

/**
 * Finds and lists all reminders in TAble whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindReminderCommand extends Command {

    public static final String COMMAND_WORD = "findReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reminders whose description contain any of "
            + "the specified keywords (case-insensitive) or date matching the specified date and displays "
            + "them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_REMINDER_DESCRIPTION + "KEYWORD [MORE_KEYWORDS]...] "
            + "[" + PREFIX_REMINDER_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER_DESCRIPTION + "midterms "
            + PREFIX_REMINDER_DATE + "2020-03-15";

    private final DescriptionContainsKeywordsPredicate descriptionPredicate;
    private final DatePredicate datePredicate;

    public FindReminderCommand(DescriptionContainsKeywordsPredicate descriptionPredicate) {
        this.descriptionPredicate = descriptionPredicate;
        this.datePredicate = null;
    }

    public FindReminderCommand(DatePredicate datePredicate) {
        this.datePredicate = datePredicate;
        this.descriptionPredicate = null;
    }

    public FindReminderCommand(DescriptionContainsKeywordsPredicate descriptionPredicate, DatePredicate datePredicate) {
        this.datePredicate = datePredicate;
        this.descriptionPredicate = descriptionPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (descriptionPredicate != null && datePredicate != null) {
            model.updateFilteredReminderList(datePredicate, descriptionPredicate);
        } else if (descriptionPredicate != null) {
            model.updateFilteredReminderList(descriptionPredicate);
        } else if (datePredicate != null) {
            model.updateFilteredReminderList(datePredicate);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW, model.getFilteredReminderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindReminderCommand // instanceof handles nulls
                && descriptionPredicate.equals(((FindReminderCommand) other).descriptionPredicate)
                && datePredicate.equals(((FindReminderCommand) other).datePredicate)); // state check
    }
}
