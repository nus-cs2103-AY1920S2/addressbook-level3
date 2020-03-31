package tatracker.logic.commands.commons;

import static java.util.Objects.requireNonNull;
import static tatracker.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.model.Model;

/**
 * Lists all students in the TA-Tracker to the user.
 */
public class ListCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            "list",
            "Listed all students",
            List.of(),
            List.of()
    );

    public static final String MESSAGE_SUCCESS = "Listed all sessions";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
        model.updateFilteredDoneSessionList(PREDICATE_SHOW_ALL_SESSIONS, "");
        return new CommandResult(MESSAGE_SUCCESS, Action.NONE);
    }
}
