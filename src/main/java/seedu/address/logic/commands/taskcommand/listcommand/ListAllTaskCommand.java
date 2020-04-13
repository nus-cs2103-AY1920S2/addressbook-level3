package seedu.address.logic.commands.taskcommand.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Find tasks in calendar by specific module code.
 */
public class ListAllTaskCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Listed all Tasks";
    public static final String COMMAND_WORD = "listTask";


    public ListAllTaskCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateDeadlineTaskList(Model.PREDICATE_SHOW_ALL_TASK);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {

        return false;
    }
}
