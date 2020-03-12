package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.Phone;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.tag.Tag;

/**
 * Increases the usage of a coupon.
 */
public class UsedCommand extends Command {
    public static final String COMMAND_WORD = "used";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increases the usage of the coupon "
            + "identified by the index number used in the displayed coupon list. "
            + "This increases the value of its usage by one. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USED_COUPON_SUCCESS = "Used Coupon: %1$s";
    public static final String MESSAGE_USAGE_LIMIT_REACHED = "Coupon usage limit has been reached!\n"
            + "You can only use it for a maximum of %d time(s).";

    private final Index targetIndex;

    /**
     * Creates a UsedCommand to increase the usage of the specified {@code Coupon}
     */
    public UsedCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
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

        if (Usage.isUsageAtLimit(currentUsage)) {
            throw new CommandException(String.format(MESSAGE_USAGE_LIMIT_REACHED, currentUsage.getMaxUsage()));
        }

        Coupon usedCoupon = createUsedCoupon(couponToBeUsed);

        model.setCoupon(couponToBeUsed, usedCoupon);
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_COUPONS);
        return new CommandResult((String.format(MESSAGE_USED_COUPON_SUCCESS, usedCoupon)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UsedCommand
            && targetIndex.equals(((UsedCommand) other).targetIndex));
    }

    /**
     * Creates and returns a {@code Coupon} with an increase in usage by one.
     */
    private static Coupon createUsedCoupon(Coupon couponToBeUsed) {
        Name name = couponToBeUsed.getName();
        Phone phone = couponToBeUsed.getPhone();
        Set<Tag> tags = couponToBeUsed.getTags();
        Usage updatedUsage = couponToBeUsed.getUsage().increaseUsageByOne();

        return new Coupon(name, phone, updatedUsage, tags);
    }
}
