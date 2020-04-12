package seedu.address.logic.commands.commandFind;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.AssignmentNameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all Assignments in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "find-assignment";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Finds all Assignments whose names contain any of "
                    + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
                    + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                    + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final AssignmentNameContainsKeywordsPredicate predicate;

    public FindAssignmentCommand(AssignmentNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAssignmentList(predicate);
        model.getMainWindow().callSwitchToAssignment();
        return new CommandResult(
                String.format(Messages.MESSAGE_ASSIGNMENT_LISTED_OVERVIEW,
                        model.getFilteredAssignmentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAssignmentCommand // instanceof handles nulls
                && predicate.equals(((FindAssignmentCommand) other).predicate)); // state check
    }
}
