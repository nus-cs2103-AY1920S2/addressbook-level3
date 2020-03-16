package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.DateIsBeforePredicate;

/**
 * Lists all expiring coupons before the specified date in the CouponStash to the user.
 */
public class ExpiringCommand extends Command {

    public static final String COMMAND_WORD = "expiring";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all coupons whose expiry date is before "
            + "the specified dates (in D-M-YYYY format) and displays them as a list with index numbers.\n"
            + "Parameters: Future date in D-M-YYYY format\n"
            + "Example: " + COMMAND_WORD + " 31-12-2020";

    private final DateIsBeforePredicate predicate;
    private final String date;

    public ExpiringCommand(DateIsBeforePredicate predicate) {
        this.predicate = predicate;
        this.date = predicate.getDate();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCouponList(predicate);
        int filteredListSize = model.getFilteredCouponList().size();
        if (filteredListSize > 0) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_COUPONS_LISTED_OVERVIEW, filteredListSize)
                            + " " + String.format(Messages.MESSAGE_COUPONS_EXPIRING_BEFORE_DATE, date));
        } else { //Empty list
            return new CommandResult(String.format(Messages.MESSAGE_COUPONS_LISTED_OVERVIEW, filteredListSize)
                    + " Try a later date!");
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiringCommand // instanceof handles nulls
                && predicate.equals(((ExpiringCommand) other).predicate)); // state check
    }
}

