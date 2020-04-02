package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Archived;
import csdev.couponstash.model.coupon.Coupon;

/**
 * Unarchive a coupon.
 */
public class UnarchiveCommand extends IndexedCommand {
    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unarchive the coupon identified by the "
            + "index number used in the displayed coupon list. "
            + "This removes the coupon frmo the archives, and place it back into the active coupon list.\n"
            + "Parameters: INDEX (must be a postive integer)\n"
            + "Example: " + COMMAND_WORD + " ";

    public static final String MESSAGE_UNARCHIVE_COUPON_SUCCESS = "Unarchived Coupon: %1s";
    public static final String MESSAGE_COUPON_ALREADY_ACTIVE = "Coupon: %1s is already active!";

    /**
     * Creates an UnarchiveCommand to unarchive the {@code Coupon} at the specified {@code targetIndex}.
     * @param targetIndex   Index of the coupon to be unarchived.
     */
    public UnarchiveCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model, commandText);
        List<Coupon> lastShownList = model.getFilteredCouponList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }

        Coupon couponToBeUnarchived = lastShownList.get(targetIndex.getZeroBased());
        Archived currentStateOfArchival = couponToBeUnarchived.getArchived();

        if (!Boolean.parseBoolean(currentStateOfArchival.toString())) {
            throw new CommandException(MESSAGE_COUPON_ALREADY_ACTIVE);
        }

        Coupon activeCoupon = couponToBeUnarchived.unarchive();
        model.setCoupon(couponToBeUnarchived, activeCoupon, commandText);
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);
        return new CommandResult(String.format(MESSAGE_UNARCHIVE_COUPON_SUCCESS,
                couponToBeUnarchived.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UnarchiveCommand
                && targetIndex.equals(((UnarchiveCommand) other).targetIndex));
    }
}
