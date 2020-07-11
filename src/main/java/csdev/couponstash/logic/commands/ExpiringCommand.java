package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.parser.CliSyntax;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.DateIsEqualsPredicate;
import csdev.couponstash.model.coupon.DateIsInMonthYearPredicate;

/**
 * This class represents the "expiring" command in Coupon Stash. It shows the user all expiring coupons on the
 * specified date in the CouponStash.
 */
public class ExpiringCommand extends Command {

    public static final String COMMAND_WORD = "expiring";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all coupons whose expiry date is "
            + "the specified expiry date (in D-M-YYYY format) or in the specified Month Year (in M-YYYY format) "
            + "and displays them as a list with index numbers.\n\n"
            + "Parameters: "
            + CliSyntax.PREFIX_EXPIRY_DATE + "EXPIRY_DATE or "
            + CliSyntax.PREFIX_MONTH_YEAR + "MONTH_YEAR\n\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_EXPIRY_DATE + "31-12-2020 or " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_MONTH_YEAR + "12-2020";

    private final Predicate<Coupon> predicate;
    private final String date;

    public ExpiringCommand(DateIsEqualsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
        this.date = predicate.getDate();
    }

    public ExpiringCommand(DateIsInMonthYearPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
        this.date = predicate.getDate();
    }

    /**
     * Executes the ExpiringCommand with a given Model representing the current state of the Coupon Stash application
     *
     * @param model {@code Model} which the command should operate on.
     * @return Returns the CommandResult that encompasses the message that is shown to the user, and any
     * external actions that should occur.
     */
    @Override
    public CommandResult execute(Model model, String commandText) {
        requireNonNull(model);
        // Put non-archived at the top first
        model.sortCoupons(Model.COMPARATOR_NON_ARCHIVED_FIRST);

        model.updateFilteredCouponList(predicate);
        int filteredListSize = model.getFilteredCouponList().size();
        if (DateUtil.isValidDate(date)) {
            model.updateMonthView(DateUtil.formatDateStringToYearMonthString(date));
            return new CommandResult(String.format(Messages.MESSAGE_COUPONS_EXPIRING_ON_DATE, filteredListSize, date));
        } else {
            assert DateUtil.isValidYearMonth(date) : "Should be a valid month year!";
            model.updateMonthView(date);
            return new CommandResult(String.format(Messages.MESSAGE_COUPONS_EXPIRING_DURING_YEAR_MONTH,
                    filteredListSize, date));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiringCommand // instanceof handles nulls
                && predicate.equals(((ExpiringCommand) other).predicate)); // state check
    }
}

