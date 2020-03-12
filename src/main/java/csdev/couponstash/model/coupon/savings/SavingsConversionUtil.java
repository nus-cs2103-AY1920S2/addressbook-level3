package csdev.couponstash.model.coupon.savings;

/**
 * A class that contains static methods for
 * converting Savings into PureMonetarySavings.
 * This disentangles the two classes and makes
 * them easier to test individually, avoiding
 * circular dependency.
 */
public class SavingsConversionUtil {
    /**
     * Converts a Savings to a PureMonetarySavings. If
     * it does not already have a monetary amount, all
     * other fields will be discarded and monetary amount
     * set to 0.
     * @param impureSavings The Savings that could possibly
     *                      contain percentage amounts.
     * @return Returns a PureMonetarySavings that represents
     *     monetary amount saved if possible. If it is not
     *     possible, monetary amount saved is set to 0.
     */
    public static PureMonetarySavings convertToPure(Savings impureSavings) {
        if (impureSavings.hasMonetaryAmount() && impureSavings.hasSaveables()) {
            return new PureMonetarySavings(impureSavings.getMonetaryAmount().get(),
                    impureSavings.getSaveables().get());
        } else if (impureSavings.hasMonetaryAmount()) {
            return new PureMonetarySavings(impureSavings.getMonetaryAmount().get());
        } else if (impureSavings.hasSaveables()) {
            return new PureMonetarySavings(impureSavings.getSaveables().get());
        } else {
            return new PureMonetarySavings();
        }
    }

    /**
     * Converts a Savings to a PureMonetarySavings. Requires
     * the price of the original item so that the actual
     * monetary amount saved from the percentage amount
     * can be calculated. If the Savings does not have
     * percentage amounts, it will be converted anyway.
     * @param impureSavings The Savings that could possibly
     *                      contain percentage amounts.
     * @param priceOfOriginal Price of the original item
     *                        represented by a MonetaryAmount.
     * @return Returns a PureMonetarySavings that represents
     *     monetary amount saved if possible. If it is not
     *     possible, monetary amount saved is set to 0.
     */
    public static PureMonetarySavings convertToPure(Savings impureSavings, MonetaryAmount priceOfOriginal) {
        if (impureSavings.hasPercentageAmount()) {
            double percentage = impureSavings.getPercentageAmount().get().getValue();
            double originalPrice = priceOfOriginal.getValue();
            double amount = Math.round(percentage * originalPrice) / 100d;
            MonetaryAmount ma = new MonetaryAmount(amount);
            if (impureSavings.hasSaveables()) {
                return new PureMonetarySavings(ma, impureSavings.getSaveables().get());
            } else {
                return new PureMonetarySavings(ma);
            }
        } else {
            return SavingsConversionUtil.convertToPure(impureSavings);
        }
    }
}
