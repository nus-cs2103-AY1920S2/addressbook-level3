//@@author chuayijing
package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;

import java.time.format.DateTimeFormatter;
import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.session.SessionPredicate;
import tatracker.model.session.SessionType;

/**
 * Filters sessions based on user's inputs.
 */
public class FilterSessionCommand extends Command {

    //@@author PotatoCombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.FILTER_MODEL,
            "Filters all the sessions inside TA-Tracker",
            List.of(),
            List.of(MODULE, DATE, SESSION_TYPE),
            MODULE, DATE, SESSION_TYPE
    );

    //@@author Chuayijing
    public static final String MESSAGE_FILTERED_SESSIONS_SUCCESS = "Filtered session list";

    // public static final String MESSAGE_NO_SESSIONS_IN_MODULE = "There are no sessions"
    //         + " for the module with the given module code.";
    // public static final String MESSAGE_INVALID_DATE = "There are no sessions with the given date";
    // public static final String MESSAGE_INVALID_SESSIONTYPE = "There are no sessions with the given session type";

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final SessionPredicate predicate;

    public FilterSessionCommand(SessionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder returnMsg = new StringBuilder(MESSAGE_FILTERED_SESSIONS_SUCCESS);

        String dateFilter = predicate.getDate().map(dateFormat::format).orElse("");

        if (predicate.getDate().isPresent()) {
            returnMsg.append("\nDate: ").append(dateFilter);
        }

        String moduleFilter = predicate.getModuleCode().orElse("");

        if (predicate.getModuleCode().isPresent()) {
            returnMsg.append("\nModule: ").append(moduleFilter);
        }

        String sessionTypeFilter = predicate.getSessionType().map(SessionType::toString).orElse("");

        if (predicate.getSessionType().isPresent()) {
            returnMsg.append("\nType: ").append(sessionTypeFilter);
        }

        model.setCurrSessionDateFilter(dateFilter);
        model.setCurrSessionModuleFilter(moduleFilter);
        model.setCurrSessionTypeFilter(sessionTypeFilter);
        model.updateFilteredSessionList(predicate);

        return new CommandResult(returnMsg.toString(), Action.FILTER_SESSION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterSessionCommand // instanceof handles nulls
                && predicate.equals(((FilterSessionCommand) other).predicate)); // state check
    }
}
