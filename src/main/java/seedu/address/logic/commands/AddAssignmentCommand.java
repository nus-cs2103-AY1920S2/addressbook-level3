package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ESTHOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Adds a new assignment to the Schoolwork Tracker.
 */
public class AddAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "(st)add";
    public static final String COMMAND_FUNCTION = "Adds an assignment to the scheduler. ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_ESTHOURS + "ESTIMATED HOURS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "CS2103 Team Project "
            + PREFIX_DEADLINE + "2020-04-18 23:59 "
            + PREFIX_ESTHOURS + "180";

    public static final String MESSAGE_SUCCESS = "New assignment added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment has already been recorded. "
            + "Congratulations, you don't have a new assignment!! (=";

    private final Assignment toAdd;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Assignment assignment) {
        requireNonNull(assignment);
        toAdd = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAssignment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAssignment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd),
            false, false, false, true, false, false, false, false);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAssignmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAssignmentCommand) other).toAdd));
    }
}
