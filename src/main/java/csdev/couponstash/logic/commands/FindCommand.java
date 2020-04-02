package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.NameContainsKeywordsPredicate;

/**
 * Finds and lists all coupons in CouponStash whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all coupons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, String commandText) {
        requireNonNull(model);
        // Put non-archived at the top first
        model.sortCoupons(Model.COMPARATOR_NON_ARCHVIED_FIRST);

        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_COUPONS);
        model.updateFilteredCouponList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_COUPONS_LISTED_OVERVIEW, model.getFilteredCouponList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
