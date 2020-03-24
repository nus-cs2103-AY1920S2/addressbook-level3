package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.todolist.Deadline;

/**
 * Adds a deadline.
 */

public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds deadline. Format of input should be:" +
            " deadline <description> by {DD-MM-YYYY} \n" + "Example: deadline CS2101 presentation script by 02-04-2020";

    public static final String MESSAGE_SUCCESS = "Deadline added: ";

    private final Deadline deadlineToAdd;

    public AddDeadlineCommand (Deadline deadline) {
        requireNonNull(deadline);
        deadlineToAdd = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isEmptyDeadline(deadlineToAdd)) {
            throw new CommandException("There is no task to be added!");
        }

        model.addDeadline(deadlineToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, deadlineToAdd));;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && deadlineToAdd.equals(((AddDeadlineCommand) other).deadlineToAdd));
    }

}
