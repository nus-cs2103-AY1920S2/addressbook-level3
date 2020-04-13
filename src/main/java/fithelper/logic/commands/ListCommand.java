package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all entries";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all entries specified by the date keyword.\n"
            + "If not specified, all entries are displayed.\n"
            + "Parameters: "
            + PREFIX_DATE + "Date (optional)"
            + "Example: " + COMMAND_WORD
            + PREFIX_DATE + "2020-03-25 ";

    private final String dateStr;

    public ListCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (this.dateStr == null) {
            model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
            model.updateFilteredReminderEntryList(PREDICATE_SHOW_ALL_ENTRIES);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredEntryList(model.someDatePredicate(this.dateStr));
            model.updateFilteredReminderEntryList(model.someDatePredicate(this.dateStr));
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

}
