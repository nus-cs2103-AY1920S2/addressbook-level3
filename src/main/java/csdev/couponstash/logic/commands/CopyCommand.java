package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_CONDITION;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_LIMIT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_PROMO_CODE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_SAVINGS;
import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.Prefix;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Condition;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;

/**
 * Copies a coupon identified using it's displayed index from the CouponStash.
 */
public class CopyCommand extends IndexedCommand {

    public static final String COMMAND_WORD = "copy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the coupon identified by the index number used in the displayed coupon list.\n\n"
            + "Parameters: INDEX (must be a positive integer)\n\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_COUPON_SUCCESS = "Copied coupon: %1$s\n\n"
            + "Copied to your clipboard! Ctrl + V to paste this coupon! (Cmd + V for macOS)";

    private Coupon coupon;

    public CopyCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model);
        List<Coupon> lastShownList = model.getFilteredCouponList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }
        this.coupon = lastShownList.get(targetIndex.getZeroBased());
        String copyCommand = getCopiedString(coupon);
        copyToClipboard(copyCommand);
        return new CommandResult(String.format(MESSAGE_COPY_COUPON_SUCCESS, coupon.getName()));
    }

    public String getCopiedString(Coupon coupon) {
        assert coupon != null : "coupon should not be null";
        Savings savings = coupon.getSavingsForEachUse();
        String totalSavings = "";
        if (savings.hasMonetaryAmount()) {
            totalSavings += addPrefixAndDetails(PREFIX_SAVINGS, savings.getMonetaryAmount().get().toString());
        }
        if (savings.hasPercentageAmount()) {
            totalSavings += addPrefixAndDetails(PREFIX_SAVINGS, savings.getPercentageAmount().get().getValue()
                    + PercentageAmount.PERCENT_SUFFIX);
        }
        if (savings.hasSaveables()) {
            List<Saveable> saveableList = savings.getSaveables().get();
            for (Saveable s : saveableList) {
                String saving = s.toString().substring(3);
                totalSavings += addPrefixAndDetails(PREFIX_SAVINGS, saving);
            }
        }

        Condition condition = coupon.getCondition();
        String conditionString = "";
        if (!condition.value.equals(Condition.DEFAULT_NO_CONDITION)) {
            conditionString = PREFIX_CONDITION + condition.value + " ";
        }

        return couponAsAddCommand(coupon, totalSavings, conditionString);
    }

    /**
     * Returns an appended String of the specified coupon as an user input add command.
     * @param totalSavings String of all the savings with s/ prefix before each item.
     * @param conditionString String of the condition with c/ prefix, if any.
     * @return Appended String of user input to add an identical coupon.
     */
    private String couponAsAddCommand (Coupon coupon, String totalSavings, String conditionString) {
        Name name = coupon.getName();
        PromoCode promoCode = coupon.getPromoCode();
        ExpiryDate expiryDate = coupon.getExpiryDate();
        Limit limit = coupon.getLimit();

        return AddCommand.COMMAND_WORD + " "
                + PREFIX_NAME.getPrefix() + name + " "
                + PREFIX_PROMO_CODE + promoCode + " "
                + PREFIX_EXPIRY_DATE + expiryDate + " "
                + totalSavings
                + conditionString
                + PREFIX_LIMIT + limit + " ";
    }


    private String addPrefixAndDetails(Prefix prefix, String details) {
        String result = prefix.getPrefix();
        result += (details + " ");
        return result;
    }

    /**
     * Copies the {@String copyCommand} to the system's clipboard;
     *
     * @param copyCommand Copied add command.
     */
    private void copyToClipboard(String copyCommand) {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(new StringSelection(copyCommand), null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && targetIndex.equals(((CopyCommand) other).targetIndex)); // state check
    }
}
