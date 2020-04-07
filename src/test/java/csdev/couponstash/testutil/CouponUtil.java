package csdev.couponstash.testutil;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_CONDITION;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_LIMIT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_PROMO_CODE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_SAVINGS;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_START_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_TAG;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_USAGE;
import static csdev.couponstash.model.coupon.savings.PercentageAmount.PERCENT_SUFFIX;

import java.util.Set;

import csdev.couponstash.logic.commands.AddCommand;
import csdev.couponstash.logic.commands.EditCommand.EditCouponDescriptor;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * A utility class for Coupon.
 */
public class CouponUtil {

    /**
     * Returns an add command string for adding the {@code coupon}.
     * @param coupon The coupon to be converted into an command.
     * @param moneySymbol The money symbol to be used (for savings).
     */
    public static String getAddCommand(Coupon coupon, String moneySymbol) {
        return AddCommand.COMMAND_WORD + " " + getCouponDetails(coupon, moneySymbol);
    }

    /**
     * Returns the part of command string for the given {@code coupon}'s details.
     * @param coupon The coupon to be converted into details.
     * @param moneySymbol The money symbol to be used (for savings details).
     */
    public static String getCouponDetails(Coupon coupon, String moneySymbol) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + coupon.getName().fullName + " ");
        sb.append(PREFIX_PROMO_CODE + coupon.getPromoCode().value + " ");
        sb.append(PREFIX_EXPIRY_DATE + coupon.getExpiryDate().value + " ");
        sb.append(PREFIX_START_DATE + coupon.getStartDate().value + " ");
        sb.append(convertSavingsToCommand(coupon.getSavingsForEachUse(), moneySymbol));
        sb.append("" + PREFIX_LIMIT + coupon.getLimit().value + " ");
        sb.append(PREFIX_REMIND + coupon.getRemindDate().toString() + " ");
        coupon.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCouponDescriptor}'s details.
     * @param descriptor The EditCouponDescriptor to be converted into part of the command string.
     * @param moneySymbol The money symbol to be used (for editing savings).
     */
    public static String getEditCouponDescriptorDetails(EditCouponDescriptor descriptor, String moneySymbol) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPromoCode()
                .ifPresent(promoCode -> sb.append(PREFIX_PROMO_CODE).append(promoCode.value).append(" "));
        descriptor.getExpiryDate().ifPresent(expiryDate -> sb.append(PREFIX_EXPIRY_DATE).append(expiryDate.value)
                .append(" "));
        descriptor.getStartDate().ifPresent(startDate -> sb.append(PREFIX_START_DATE).append(startDate.value)
                .append(" "));
        descriptor.getSavings().ifPresent(sv -> sb.append(convertSavingsToCommand(sv, moneySymbol)));
        descriptor.getUsage().ifPresent(usage -> sb.append(PREFIX_USAGE).append(usage.value).append(" "));
        descriptor.getLimit().ifPresent(limit -> sb.append(PREFIX_LIMIT).append(limit.value).append(" "));
        descriptor.getRemindDate().ifPresent(remindDate -> sb.append(PREFIX_REMIND).append(remindDate.toString())
                .append(" "));
        descriptor.getCondition().ifPresent(cond -> sb.append(PREFIX_CONDITION).append(cond.toString() + " "));

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Converts a Savings object into prefixes and strings
     * that can be used in either the "add" command or the
     * "edit" command to construct a Coupon with the same
     * Savings object.
     * @param sv The Savings to be converted.
     * @param moneySymbol String representing MoneySymbol of
     *                    the parser.
     * @return String representing prefixes and raw
     *     values originally in the Savings sv.
     */
    private static String convertSavingsToCommand(Savings sv, String moneySymbol) {
        StringBuilder sb = new StringBuilder();
        sv.getMonetaryAmount().ifPresent(ma ->
                sb.append(PREFIX_SAVINGS).append(moneySymbol).append(ma.getValue()).append(" ")
        );
        sv.getPercentageAmount().ifPresent(pc ->
                sb.append(PREFIX_SAVINGS).append(pc.getValue()).append(PERCENT_SUFFIX).append(" ")
        );
        sv.getSaveables().ifPresent(svaList -> svaList.stream()
                .forEach(sva -> sb.append(PREFIX_SAVINGS).append(sva.getValue()).append(" "))
        );
        return sb.toString();
    }
}
