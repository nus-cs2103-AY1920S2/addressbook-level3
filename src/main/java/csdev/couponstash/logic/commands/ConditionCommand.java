package csdev.couponstash.logic.commands;

import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_CONDITION;

import java.util.List;
import java.util.Set;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
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


/**
 * This class represents the "condition" command in Coupon Stash. It set command of a coupon in a CouponStash
 */
public class ConditionCommand extends Command {

    public static final String COMMAND_WORD = "condition";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the Terms & Condition of the identified coupon "
            + "by the index number used in the coupon listing."
            + "Existing Terms & Condition will be overwritten by the input.\n\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_CONDITION + " [T&C]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + "c/ Min Spending of $15.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    private static String messageSuccess = "";

    private final Index index;
    private final Condition condition;

    /**
     * @param index of the coupon in the filtered coupon list to edit the condition
     * @param condition of the coupon to be updated to
     **/
    public ConditionCommand(Index index, Condition condition) {
        requireAllNonNull(index, condition);

        this.index = index;
        this.condition = condition;
    }


    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {

        List<Coupon> lastShownList = model.getFilteredCouponList();
        Coupon conditionCoupon;

        // index is out of range
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }
        if (!commandText.contains("c/")) {
            throw new CommandException(MESSAGE_USAGE + "\n" + Condition.MESSAGE_CONSTRAINTS);
        }
        commandText = commandText.substring(commandText.lastIndexOf("c/") + 2);
        if (commandText.equals("") || commandText.equals(" ")) {
            throw new CommandException(Condition.MESSAGE_CONSTRAINTS);
        }
        String edittedText = commandText.replaceAll("\\p{Punct}", " ");
        int wordCount = edittedText.split("\\s+").length;
        if (wordCount > 50) {
            throw new CommandException(Condition.MESSAGE_CONSTRAINTS);
        }
        Coupon couponToBeConditioned = lastShownList.get(index.getZeroBased());
        conditionCoupon = createConditionCoupon(couponToBeConditioned, commandText);
        model.setCoupon(couponToBeConditioned, conditionCoupon, commandText);
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);

        messageSuccess = "Changes have been made to coupon: "
                + conditionCoupon.getName().toString() + "'s condition statement.\n"
                + "Edited condition: " + commandText;
        return new CommandResult(String.format(messageSuccess, conditionCoupon));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), condition));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConditionCommand)) {
            return false;
        }

        // state check
        ConditionCommand e = (ConditionCommand) other;
        return index.equals(e.index)
                && condition.equals(e.condition);
    }

    /**
     * Creates and returns a {@code Coupon} with the new condition
     */
    private static Coupon createConditionCoupon(Coupon couponToBeConditioned, String commandText) {
        Name name = couponToBeConditioned.getName();
        Savings savingsForEachUse = couponToBeConditioned.getSavingsForEachUse();
        PromoCode promoCode = couponToBeConditioned.getPromoCode();
        ExpiryDate expiryDate = couponToBeConditioned.getExpiryDate();
        StartDate startDate = couponToBeConditioned.getStartDate();
        Limit limit = couponToBeConditioned.getLimit();
        RemindDate remindDate = couponToBeConditioned.getRemindDate();
        Set<Tag> tags = couponToBeConditioned.getTags();
        Usage updatedUsage = couponToBeConditioned.getUsage();
        DateSavingsSumMap totalSavings = couponToBeConditioned.getSavingsMap();
        Archived archived = couponToBeConditioned.getArchived();
        Condition condition = new Condition(commandText);
        return new Coupon(name, promoCode, savingsForEachUse, expiryDate, startDate, updatedUsage, limit,
                tags, totalSavings, remindDate, condition, archived);
    }
}
