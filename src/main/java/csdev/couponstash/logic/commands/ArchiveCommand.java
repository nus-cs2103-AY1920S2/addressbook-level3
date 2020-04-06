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
 * Archives a coupon.
 */
public class ArchiveCommand extends IndexedCommand {
    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the coupon identified by "
            + "the index number used in the displayed coupon list. "
            + "This removes the coupon from the active list, and place it into the program archives.\n\n"
            + "Parameters: INDEX (must be a positive integer)\n\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_COUPON_SUCCESS = "Archived Coupon: %1$s";
    public static final String MESSAGE_COUPON_ALREADY_ARCHIVED = "Coupon: %1$s is already archived!";

    /**
     * Creates an ArchiveCommand to archive the {@code Coupon} at the specified {@code targetIndex}.
     * @param targetIndex Index of the coupon to be archived.
     */
    public ArchiveCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model, commandText);
        List<Coupon> lastShownList = model.getFilteredCouponList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }

        Coupon couponToBeArchived = lastShownList.get(targetIndex.getZeroBased());
        Archived currentStateOfArchival = couponToBeArchived.getArchived();

        if (currentStateOfArchival.state) {
            throw new CommandException(String.format(MESSAGE_COUPON_ALREADY_ARCHIVED,
                    couponToBeArchived.getName()));
        }

        Coupon archivedCoupon = couponToBeArchived.archive();
        model.setCoupon(couponToBeArchived, archivedCoupon, commandText);
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_COUPON_SUCCESS, archivedCoupon.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ArchiveCommand
                && targetIndex.equals(((ArchiveCommand) other).targetIndex));
    }
}
