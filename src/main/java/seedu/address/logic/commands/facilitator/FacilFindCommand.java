package seedu.address.logic.commands.facilitator;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.facilitator.NameContainsKeywordsPredicate;

/**
 * Finds and lists all facilitators in Mod Manager whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FacilFindCommand extends FacilCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_FACIL + " " + COMMAND_WORD_FIND
            + ": Finds all facilitators whose names contain any of the "
            + "specified facilitator name (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: FACILITATOR_NAME [MORE_FACILITATOR_NAMES]...\n"
            + "Example: " + COMMAND_GROUP_FACIL + " " + COMMAND_WORD_FIND + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FacilFindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFacilitatorList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_FACILITATORS_LISTED_OVERVIEW,
                model.getFilteredFacilitatorList().size()), CommandType.FACILITATOR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacilFindCommand // instanceof handles nulls
                && predicate.equals(((FacilFindCommand) other).predicate)); // state check
    }
}
