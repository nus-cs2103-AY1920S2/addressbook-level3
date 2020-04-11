package seedu.address.logic.commands.taskcommand.sortcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTING;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;

/**
 * Sort tasks in calendar by date or priority.
 */
public class SortTasksCommand extends Command {
    public static final String COMMAND_WORD = "sortTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort the tasks as required "
            + "Parameters: "
            + PREFIX_SORTING + "Sorting parameter "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORTING + "priority";

    public static final String MESSAGE_SUCCESS = "Tasks sorted by ";

    private final String sortingParam;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SortTasksCommand(String sortingParam) {
        requireNonNull(sortingParam);
        this.sortingParam = sortingParam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.sortTask(sortingParam);

        return new CommandResult(MESSAGE_SUCCESS + sortingParam);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortTasksCommand // instanceof handles nulls
                && sortingParam.equals(((SortTasksCommand) other).sortingParam));
    }

}
