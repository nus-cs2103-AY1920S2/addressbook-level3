package csdev.couponstash.logic.commands;

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
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
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
            + "Copied to your clipboard! Ctrl + v to paste this coupon!";

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
        String copyCommand = getCopyCommand(coupon);
        copyToClipboard(copyCommand);
        return new CommandResult(String.format(MESSAGE_COPY_COUPON_SUCCESS, coupon.getName()));
    }

    public String getCopyCommand(Coupon coupon) {
        Name name = coupon.getName();
        PromoCode promoCode = coupon.getPromoCode();
        ExpiryDate expiryDate = coupon.getExpiryDate();
        Limit limit = coupon.getLimit();
        Savings savings = coupon.getSavingsForEachUse();
        String totalSavings = "";
        if (savings.hasMonetaryAmount()) {
            totalSavings += addPrefixAndDetails(PREFIX_SAVINGS, savings.getMonetaryAmount().get().toString());
        }
        if (savings.hasPercentageAmount()) {
            totalSavings += addPrefixAndDetails(PREFIX_SAVINGS, savings.getPercentageAmount().get().toString());
        }
        if (savings.hasSaveables()) {
            List<Saveable> saveableList = savings.getSaveables().get();
            for (Saveable s: saveableList) {
                String saving = s.toString().substring(3);
                totalSavings += addPrefixAndDetails(PREFIX_SAVINGS, saving);
            }
        }

        String copyCommand = AddCommand.COMMAND_WORD + " "
                + PREFIX_NAME.getPrefix() + name + " "
                + PREFIX_PROMO_CODE + promoCode + " "
                + PREFIX_EXPIRY_DATE + expiryDate + " "
                + totalSavings
                + PREFIX_LIMIT + limit + " ";
        return copyCommand;
    }

    private String addPrefixAndDetails(Prefix prefix, String details) {
        String result = prefix.getPrefix();
        result += (details + " ");
        return result;
    }

    /**
     * Copies the {@String copyCommand} to the system's clipboard;
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
