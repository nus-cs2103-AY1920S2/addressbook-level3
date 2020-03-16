package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_SAVINGS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.Phone;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.coupon.savings.SavingsConversionUtil;
import csdev.couponstash.model.tag.Tag;

/**
 * Increases the usage of a coupon.
 */
public class UsedCommand extends Command {
    public static final String COMMAND_WORD = "used";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increases the usage of the coupon "
            + "identified by the index number used in the displayed coupon list. "
            + "This increases the value of its usage by one.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SAVINGS + "Original amount of purchase (optional)\n"
            + "Example: " + COMMAND_WORD + " 1"
            + "Example with Savings: " + COMMAND_WORD + "1 " + PREFIX_SAVINGS + "$100";

    public static final String MESSAGE_USED_COUPON_SUCCESS = "Used Coupon: %1$s";
    public static final String MESSAGE_USAGE_LIMIT_REACHED = "Coupon usage limit has been reached!\n"
            + "You can only use it for a maximum of %s time(s).";
    public static final String MESSAGE_MISSING_ORIGINAL_AMOUNT = "Coupon has percentage savings "
            + "that requires the input of the original amount of purchase.\n"
            + "Example: " + COMMAND_WORD + " 1 $100";

    private final Index targetIndex;
    private final MonetaryAmount originalAmount;

    /**
     * Creates a UsedCommand to increase the usage of the specified {@code Coupon}
     */
    public UsedCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.originalAmount = new MonetaryAmount(0.0);
    }

    /**
     * Creates a UsedCommand to increase the usage of the specified {@code Coupon},
     * and also keep track of the amount saved by the usage of this coupon.
     */
    public UsedCommand(Index targetIndex, MonetaryAmount originalAmount) {
        this.targetIndex = targetIndex;
        this.originalAmount = originalAmount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Coupon> lastShownList = model.getFilteredCouponList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }

        Coupon couponToBeUsed = lastShownList.get(targetIndex.getZeroBased());
        Usage currentUsage = couponToBeUsed.getUsage();
        Limit limit = couponToBeUsed.getLimit();
        boolean hasPercentageSavings = couponToBeUsed.getSavings().hasPercentageAmount();

        if (Usage.isUsageAtLimit(currentUsage, limit)) {
            throw new CommandException(String.format(MESSAGE_USAGE_LIMIT_REACHED, limit.getLimit()));
        }

        // checks if original amount of purchase is provided if the type of Savings is of percentage amount
        if (hasPercentageSavings && (originalAmount.equals(new MonetaryAmount(0.0)))) {
            throw new CommandException(String.format(MESSAGE_MISSING_ORIGINAL_AMOUNT));
        }

        Coupon newUsedCoupon;
        if (hasPercentageSavings) {
            Savings newSavings = SavingsConversionUtil.convertToPure(couponToBeUsed.getSavings(), originalAmount);
            newUsedCoupon = createUsedCouponWithSavings(couponToBeUsed, newSavings);
        } else {
            newUsedCoupon = createUsedCoupon(couponToBeUsed);
        }

        model.setCoupon(couponToBeUsed, newUsedCoupon);
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_COUPONS);
        return new CommandResult((String.format(MESSAGE_USED_COUPON_SUCCESS, newUsedCoupon.getName())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UsedCommand
            && targetIndex.equals(((UsedCommand) other).targetIndex))
            && originalAmount.equals(((UsedCommand) other).originalAmount);
    }

    /**
     * Creates and returns a {@code Coupon} with an increase in usage by one,
     * and also add the savings accompanied by the usage of this coupon.
     */
    private static Coupon createUsedCouponWithSavings(Coupon couponToBeUsed, Savings newSavings) {
        Name name = couponToBeUsed.getName();
        Phone phone = couponToBeUsed.getPhone();
        ExpiryDate expiryDate = couponToBeUsed.getExpiryDate();
        Limit limit = couponToBeUsed.getLimit();
        Set<Tag> tags = couponToBeUsed.getTags();
        Usage updatedUsage = couponToBeUsed.getUsage().increaseUsageByOne();

        return new Coupon(name, phone, newSavings, expiryDate, updatedUsage, limit, tags);
    }

    /**
     * Creates and returns a {@code Coupon} with an increase in usage by one.
     */
    private static Coupon createUsedCoupon(Coupon couponToBeUsed) {
        return createUsedCouponWithSavings(couponToBeUsed, couponToBeUsed.getSavings());
    }
}
