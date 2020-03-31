package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_DATE;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_SESSION_TYPE;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.session.SessionPredicate;

/**
 * Filters sessions based on user's inputs.
 */
public class FilterSessionCommand extends Command {

    public static final String COMMAND_WORD = String.format("%s %s", CommandWords.SESSION, CommandWords.FILTER_MODEL);

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters session."
            + "Parameters: "
            + "[" + PREFIX_DATE + "DATE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-29 n/"
            + "[" + PREFIX_MODULE + "MODULE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103T "
            + "[" + PREFIX_SESSION_TYPE + "SESSION_TYPE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_TYPE + "consultation";


    public static final String MESSAGE_SUCCESS = "Filtered Session List: %1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no sessions with the given module code.";
    public static final String MESSAGE_INVALID_DATE = "There is no sessions with the given date.";
    public static final String MESSAGE_INVALID_SESSIONTYPE = "There is no sessions with the given session type.";

    private final SessionPredicate predicate;

    public FilterSessionCommand(SessionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSessionList(predicate);
        String keywords = predicate.getKeywords();
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, keywords),
                Action.FILTER_SESSION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterSessionCommand // instanceof handles nulls
                && predicate.equals(((FilterSessionCommand) other).predicate)); // state check
    }
}
