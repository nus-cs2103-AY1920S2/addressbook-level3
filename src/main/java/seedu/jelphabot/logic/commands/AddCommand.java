package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.summary.Summary;
import seedu.jelphabot.model.task.Task;

/**
 * Adds a Task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list.\n"
            + "Parameters: \n"
            + "    " + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + "    " + PREFIX_DATETIME + "DATETIME in format MMM-dd-YYYY HH mm\n"
            + "    " + PREFIX_MODULE_CODE + "MODULE_CODE\n"
            + "    " + "[" + PREFIX_PRIORITY + "PRIORITY (-1, 0 or 1), DEFAULTS TO 1]\n"
            + "    " + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Assignment 1 " + PREFIX_DATETIME + "Jan-01-2020 23 59 "
            + PREFIX_MODULE_CODE + "CS3230 " + PREFIX_PRIORITY + "1\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        model.setProductivity(new Productivity(model.getFilteredTaskList(), true, false, false));
        model.setSummary(new Summary(model.getFilteredTaskList()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
