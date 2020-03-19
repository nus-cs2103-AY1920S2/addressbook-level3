package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "(st)list";
    public static final String COMMAND_FUNCTION = "Shows a list of all assignments in the scheduler.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  " + COMMAND_FUNCTION + "\n"
            +"Parameters: "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_ESTHOURS + "ESTIMATED HOURS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "CS2103 Team Project "
            + PREFIX_DEADLINE + "04-18 23:59 ";

    public static final String MESSAGE_SUCCESS = "Listed all assignments";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
