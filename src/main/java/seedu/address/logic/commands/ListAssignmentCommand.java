package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ESTHOURS_LIST;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;


/**
 * Lists all persons in the address book to the user.
 */
public class ListAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "(st)list";
    public static final String COMMAND_FUNCTION = "Shows a list of all assignments in the scheduler "
            + "sorted by alphabetical order, chronological order based on deadline or estimated hours.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  " + COMMAND_FUNCTION + "\n"
            + "Parameters: ["
            + PREFIX_DEADLINE_LIST + "] ["
            + PREFIX_ESTHOURS_LIST + "]\n"
            + "-d to sort by DEADLINE, -e to sort by"
            + " ESTIMATED HOURS, or neither to sort by "
            + "alphabetical order.\n"
            + "Example: " + COMMAND_WORD + " OR "
            + COMMAND_WORD + " " + PREFIX_DEADLINE_LIST
            + " OR " + COMMAND_WORD + " " + PREFIX_ESTHOURS_LIST;

    public static final String MESSAGE_SUCCESS = "Listed all assignments";

    private final Comparator<Assignment> comparator;

    /**
     * @param comparator to be sorted in the filtered assignment list
     */
    public ListAssignmentCommand(Comparator<Assignment> comparator) {
        requireNonNull(comparator);

        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortAssignment(comparator);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true, false, false, false, false);
    }
}
