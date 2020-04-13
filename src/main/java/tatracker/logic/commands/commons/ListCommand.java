//@@author Chuayijing
package tatracker.logic.commands.commons;

import static java.util.Objects.requireNonNull;
import static tatracker.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;

/**
 * Lists all students in the TA-Tracker to the user.
 */
public class ListCommand extends Command {

    //@@author PotatoCombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.LIST,
            "Removes all session and claim filters inside TA-Tracker",
            List.of(),
            List.of()
    );

    public static final String MESSAGE_LISTED_SESSIONS = "Removed all filters";

    //@@author Chuayijing
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCurrSessionDateFilter("");
        model.setCurrSessionModuleFilter("");
        model.setCurrSessionTypeFilter("");
        model.updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
        model.updateFilteredDoneSessionList(PREDICATE_SHOW_ALL_SESSIONS, "");
        return new CommandResult(MESSAGE_LISTED_SESSIONS, Action.LIST);
    }
}
