package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HOME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
import static fithelper.model.Model.PREDICATE_SHOW_NO_ENTRIES;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Displays the reminders stored by FitHelper.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display all undone plans specified by the date keyword.\n"
            + "If not specified, all undone entries are displayed.\n"
            + "Parameters: "
            + PREFIX_DATE + "Date (optional)"
            + "Example: " + COMMAND_WORD
            + PREFIX_DATE + "2020-03-25 ";

    public static final String MESSAGE_SUCCESS = "Now you can view all undone tasks ~";
    public static final String MESSAGE_SUCCESS_DATE = "Now you can view all undone tasks on your specified date ~";

    private final String dateStr;

    public ReminderCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (this.dateStr == null) {
            //if no date specified, show the whole reminder list
            model.updateFilteredEntryList(PREDICATE_SHOW_NO_ENTRIES);
            model.updateFilteredReminderEntryList(PREDICATE_SHOW_ALL_ENTRIES);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredEntryList(PREDICATE_SHOW_NO_ENTRIES);
            model.updateFilteredReminderEntryList(model.someDatePredicate(this.dateStr));
            return new CommandResult(String.format(MESSAGE_SUCCESS_DATE), HOME, false);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand);
    }
}
