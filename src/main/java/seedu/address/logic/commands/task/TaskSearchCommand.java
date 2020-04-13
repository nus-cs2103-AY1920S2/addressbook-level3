package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.TaskSearchPredicate;

/**
 * Finds and lists all tasks in Mod Manager whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class TaskSearchCommand extends TaskCommand {

    public static final String MESSAGE_COMMAND_EXAMPLE = "Example: "
            + COMMAND_GROUP_TASK + " " + COMMAND_WORD_SEARCH + " "
            + PREFIX_DATE + " 11 "
            + PREFIX_MONTH + " 5 "
            + PREFIX_YEAR + " 2020\n";

    public static final String MESSAGE_USAGE = COMMAND_GROUP_TASK + " " + COMMAND_WORD_SEARCH
            + ": Searches for all tasks that occur on your specified date, month, or year "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_DATE + " dd] "
            + "[" + PREFIX_MONTH + " MM] "
            + "[" + PREFIX_YEAR + " yy] \n"
            + MESSAGE_COMMAND_EXAMPLE;

    public static final String MESSAGE_INVALID_DAY_MONTH_YEAR = "The provided date, month, or year "
            + "must be a valid number. "
            + "It cannot contain letters or words. Please try again. \n"
            + MESSAGE_COMMAND_EXAMPLE;

    public static final String MESSAGE_OUT_OF_BOUNDS_VALUES = "The provided date, month, year, "
            + "or any of their combination "
            + "is out of bounds. "
            + "Please try again. \n"
            + MESSAGE_COMMAND_EXAMPLE;
    private final TaskSearchPredicate predicate;

    public TaskSearchCommand(TaskSearchPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_TASKS_FILTERED_OVERVIEW,
                model.getFilteredTaskList().size()), CommandType.TASK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskSearchCommand // instanceof handles nulls
                && predicate.equals(((TaskSearchCommand) other).predicate)); // state check
    }

}
