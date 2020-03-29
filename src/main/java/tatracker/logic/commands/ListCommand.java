package tatracker.logic.commands;

import tatracker.model.Model;

import static java.util.Objects.requireNonNull;
import static tatracker.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

/**
 * Lists all students in the TA-Tracker to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all sessions";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
        model.updateFilteredDoneSessionList(PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
