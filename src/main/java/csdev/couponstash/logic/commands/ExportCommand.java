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
 * Exports a coupon identified using it's displayed index from the CouponStash.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the coupon identified by the index number used in the displayed coupon list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EXPORT_COUPON_SUCCESS = "Exported coupon: %1$s.\n"
            + "Copied to your clipboard! Ctrl + v to paste this coupon!";

    private final Index targetIndex;
    private Coupon coupon;


    public ExportCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Coupon> lastShownList = model.getFilteredCouponList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }
        this.coupon = lastShownList.get(targetIndex.getZeroBased());
        String exportCommand = getExportCommand(coupon);
        copyToClipboard(exportCommand);
        return new CommandResult(String.format(MESSAGE_EXPORT_COUPON_SUCCESS, coupon.getName()));
    }

    public String getExportCommand(Coupon coupon) {
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

        String exportCommand = AddCommand.COMMAND_WORD + " "
                + PREFIX_NAME.getPrefix() + name + " "
                + PREFIX_PROMO_CODE + promoCode + " "
                + PREFIX_EXPIRY_DATE + expiryDate + " "
                + totalSavings
                + PREFIX_LIMIT + limit + " ";
        return exportCommand;
    }

    private String addPrefixAndDetails(Prefix prefix, String details) {
        String result = prefix.getPrefix();
        result += (details + " ");
        return result;
    }

    /**
     * Copies the {@String exportCommand} to the system's clipboard;
     * @param exportCommand Exported add command.
     */
    private void copyToClipboard(String exportCommand) {
        System.setProperty("java.awt.headless", "false");
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(new StringSelection(exportCommand), null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && targetIndex.equals(((ExportCommand) other).targetIndex)); // state check
    }
}
