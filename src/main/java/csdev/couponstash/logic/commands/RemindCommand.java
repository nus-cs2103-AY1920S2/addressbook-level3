package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Archived;
import csdev.couponstash.model.coupon.Condition;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.RemindDate;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;
import csdev.couponstash.ui.RemindWindow;

/** --13
 * This class represents the remind command in Coupon Stash
 * The remind command allows the user to set date to be reminded
 * for a coupon. Upon the date of the reminder, Coupon Stash will
 * notify the user in a form of a pop when Coupon Stash is launched.
 */
public class RemindCommand extends IndexedCommand {

    public static final String COMMAND_WORD = "remind";
    private static String messageSuccess = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set a reminder of a coupon, identified by the index number "
            + "used in coupon listing. "
            + "Existing reminder will be overwritten by the input.\n\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMIND + " [Date] or [String]\n"
            + "Examples:\n"
            + COMMAND_WORD + " 1 " + PREFIX_REMIND + " 25-12-2020\n\n"
            + COMMAND_WORD + " 2 " + PREFIX_REMIND + " 2 days before";

    private static final String MESSAGE_ARGUMENTS = "Reminder has been set on %2$s for Coupon %1$s";

    private LocalDate remindDate;
    private String input;

    /**
     * @param index of the coupon in the coupon lists to edit
     * @param input details to remind the coupon on
     */
    public RemindCommand(Index index, String input) {
        super(index);
        requireNonNull(input);

        this.input = input;
        this.remindDate = LocalDate.now();
    }

    /**
     * Executes the RemindCommand with a given Model representing
     * the current state of the Coupon Stash application
     * @param model {@code Model} which the command should operate on.
     * @return Returns the CommandResult that encompasses the
     *     message that should be shown to the user, as well as
     *     any other external actions that should occur.
     * @throws CommandException is thrown either when
     *     the remind date is set after the coupon's expiry date or the coupon
     *     index is out of range (Coupon does not exist).
     */
    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {

        List<Coupon> lastShownList = model.getFilteredCouponList();
        Coupon remindCoupon;

        // index is out of range
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }

        Coupon couponToBeRemind = lastShownList.get(targetIndex.getZeroBased());

        // if "days before scenario", straightaway calculate the remindDate;
        if (input.contains("days before")) {

            int daysBefore = Integer.parseInt(input.replaceAll("[^0-9]", ""));

            remindDate = (couponToBeRemind.getExpiryDate().date).minusDays(daysBefore);

            remindCoupon = createRemindCoupon(couponToBeRemind, remindDate);

            model.setCoupon(couponToBeRemind, remindCoupon, commandText);
            model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);

            messageSuccess = "Reminder has been set to remind on "
                    + remindCoupon.getRemindDate().toString()
                    + " (" + daysBefore + " days before coupon's expiry: "
                    + remindCoupon.getExpiryDate().value + ")";
        } else {

            LocalDate tempDate;
            try {
                tempDate = LocalDate.parse(input, DateUtil.DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                throw new CommandException(DateUtil.MESSAGE_DATE_WRONG_FORMAT);
            }

            //check if input's date is not after the coupon's expiry date
            if (tempDate.isAfter(couponToBeRemind.getExpiryDate().date)) {
                throw new CommandException(Messages.MESSAGE_REMIND_DATE_EXCEED_EXPIRY_DATE);
            } else if (tempDate.isBefore((LocalDate.now()))) {
                throw new CommandException(Messages.MESSAGE_REMIND_DATE_BEFORE_TODAYS);
            } else {
                remindDate = tempDate;

                remindCoupon = createRemindCoupon(couponToBeRemind, remindDate);

                model.setCoupon(couponToBeRemind, remindCoupon, commandText);
                model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);

                messageSuccess = "Reminder has been set to remind on "
                        + remindCoupon.getRemindDate().toString()
                        + " (Coupon's expiry : "
                        + remindCoupon.getExpiryDate().value + ")";
            }
        }
        if (remindCoupon.getRemindDate().hasReminder()) {
            return new CommandResult(messageSuccess);
        }
        return new CommandResult(String.format(messageSuccess, remindCoupon));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemindCommand)) {
            return false;
        }

        // state check
        RemindCommand e = (RemindCommand) other;
        return targetIndex.equals(e.targetIndex)
                && input.equals(e.input);
    }

    /**
     * This method is to check all coupon's remind date against today's and
     * formulate the entire coupons that has to be reminded today as a string
     * @param list - the current coupon's list
     */
    public static void showRemind(List<Coupon> list) {
        String remindMessage = "";
        int count = 1;
        boolean remindFlag = false;

        for (Coupon temp : list) {
            if (temp.getRemindDate().getDate().equals(LocalDate.now())) {
                remindFlag = true;
                remindMessage = remindMessage + count + ". "
                        + temp.getName().toString()
                        + " (Expires on "
                        + temp.getExpiryDate().toString()
                        + ")" + "\n";
                count = count + 1;
            }
        }
        if (remindFlag) {
            RemindWindow.displayRemind(remindMessage);
        }
    }
    /**
     * Creates and returns a {@code Coupon} with the new remind date
     */
    private static Coupon createRemindCoupon(Coupon couponToBeReminded, LocalDate date) {
        Name name = couponToBeReminded.getName();

        Savings savingsForEachUse = couponToBeReminded.getSavingsForEachUse();
        PromoCode promoCode = couponToBeReminded.getPromoCode();
        ExpiryDate expiryDate = couponToBeReminded.getExpiryDate();
        StartDate startDate = couponToBeReminded.getStartDate();
        Limit limit = couponToBeReminded.getLimit();
        RemindDate remindDate = new RemindDate();
        remindDate.setRemindDate(date);
        Set<Tag> tags = couponToBeReminded.getTags();
        Usage updatedUsage = couponToBeReminded.getUsage();
        DateSavingsSumMap totalSavings = couponToBeReminded.getSavingsMap();
        Condition condition = couponToBeReminded.getCondition();

        Archived archived = couponToBeReminded.getArchived();

        return new Coupon(name, promoCode, savingsForEachUse, expiryDate, startDate, updatedUsage, limit,
                tags, totalSavings, remindDate, condition, archived);
    }
}
