package seedu.address.logic.commands.commandFind;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelStaff.StaffNameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all teachers in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindStaffCommand extends Command {

    public static final String COMMAND_WORD = "find-staff";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Finds all staffs whose names contain any of "
                    + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
                    + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                    + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final StaffNameContainsKeywordsPredicate predicate;

    public FindStaffCommand(StaffNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStaffList(predicate);
        model.getMainWindow().callSwitchToStaff();
        return new CommandResult(
                String.format(Messages.MESSAGE_STAFFS_LISTED_OVERVIEW,
                        model.getFilteredStaffList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindStaffCommand // instanceof handles nulls
                && predicate.equals(((FindStaffCommand) other).predicate)); // state check
    }
}
