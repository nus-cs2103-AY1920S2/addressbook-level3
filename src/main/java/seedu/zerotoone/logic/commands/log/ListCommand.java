package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_LOGS;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.model.Model;

/**
 * Lists all sessions in the log list to the user.
 */
public class ListCommand extends LogCommand {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all logs";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLogList(PREDICATE_SHOW_ALL_LOGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
