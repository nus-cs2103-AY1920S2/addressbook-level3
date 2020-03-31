package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.session.SessionPredicate;

/**
 * Filters sessions based on user's inputs.
 */
public class FilterSessionCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.FILTER_MODEL,
            "Filters all the sessions.",
            List.of(),
            List.of(DATE, MODULE, SESSION_TYPE),
            DATE, MODULE, SESSION_TYPE
    );

    public static final String MESSAGE_SUCCESS = "Filtered Session List: %1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There are no sessions with the given module code.";
    public static final String MESSAGE_INVALID_DATE = "There are no sessions with the given date.";
    public static final String MESSAGE_INVALID_SESSIONTYPE = "There are no sessions with the given session type.";

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
