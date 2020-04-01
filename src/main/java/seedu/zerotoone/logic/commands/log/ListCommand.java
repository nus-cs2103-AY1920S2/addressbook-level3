package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.model.Model;

/**
 * Lists all sessions in the session list to the user.
 */
public class ListCommand extends LogCommand {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all sessions";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
