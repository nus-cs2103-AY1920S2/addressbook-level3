package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.todolist.Task;

/**
 * Adds a deadline.
 */

public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds deadline. Format of input should be:"
            + " deadline desc/<description> by/{DD-MM-YYYY} cat/<Category>\n"
            + "Example: deadline desc/CS2101 presentation script by/02-04-2020 cat/School Work";

    public static final String MESSAGE_SUCCESS = "Deadline added: ";

    public static final String MESSAGE_INVALID = "Your deadline seems to be empty!";


    private final Task deadlineToAdd;

    public AddDeadlineCommand (Task deadline) {
        requireNonNull(deadline);
        deadlineToAdd = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Task.getDeadlineTaskList().add(deadlineToAdd);
        model.updateDeadlineTaskList(PREDICATE_SHOW_ALL_TASK);

        if (model.isEmptyDeadline(deadlineToAdd)) {
            throw new CommandException("There is no task to be added!");
        }

        model.addDeadline(deadlineToAdd);
        return new CommandResult(MESSAGE_SUCCESS +  deadlineToAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && deadlineToAdd.equals(((AddDeadlineCommand) other).deadlineToAdd));
    }

}
