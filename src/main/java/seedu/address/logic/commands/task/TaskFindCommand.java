package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

/**
 * Finds and lists all tasks in Mod Manager whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class TaskFindCommand extends TaskCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_TASK + " " + COMMAND_WORD_FIND
            + ": Finds all tasks for which the description contains any of the "
            + "specified task description (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: DESCRIPTION [MORE_DESCRIPTIONS]... \n"
            + "Example: " + COMMAND_GROUP_TASK + " " + COMMAND_WORD_FIND + " assignment";

    private final TaskContainsKeywordsPredicate predicate;

    public TaskFindCommand(TaskContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
                model.getFilteredTaskList().size()), CommandType.TASK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskFindCommand // instanceof handles nulls
                && predicate.equals(((TaskFindCommand) other).predicate)); // state check
    }
}
