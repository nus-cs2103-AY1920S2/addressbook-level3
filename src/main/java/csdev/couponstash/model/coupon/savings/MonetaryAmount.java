package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;

/**
 * Represents a monetary amount that
 * can be saved by a coupon.
 * Immutable.
 */
public class MonetaryAmount implements Comparable<MonetaryAmount> {
    public static final String MESSAGE_CONSTRAINTS =
            "Monetary amount should be a positive integer, and accurate to at most 2 decimal places";
    // to avoid floating point errors
    private static final double THRESHHOLD = 0.0000000000001;

    private final double monetaryAmount;

    public MonetaryAmount(double amount) {
        checkArgument(MonetaryAmount.isValidMonetaryAmount(amount),
                MonetaryAmount.MESSAGE_CONSTRAINTS);
        this.monetaryAmount = amount;
    }

    /**
     * Gets the value of this MonetaryAmount
     * as a non-negative double that has only
     * 2 decimal places at most.
     * @return Double representing MonetaryAmount.
     */
    public double getValue() {
        return this.monetaryAmount;
    }

    /**
     * Checks if this double is suitable for use
     * in the MonetaryAmount (should be at most
     * 2 decimal places).
     * @param monetaryAmount The double to be checked.
     */
    public static boolean isValidMonetaryAmount(double monetaryAmount) {
        // when multiplied by 100, it should be a whole number
        double remainder = (monetaryAmount * 100) % 1;
        // use THRESHHOLD to avoid floating point errors
        return monetaryAmount >= 0 && ((remainder < THRESHHOLD) || (remainder > (1 - THRESHHOLD)));
    }

    /**
     * Adds this MonetaryAmount to another MonetaryAmount,
     * resulting in a new MonetaryAmount with value
     * greater than the original two.
     * @param ma The other MonetaryAmount to add to.
     * @return Returns a new MonetaryAmount with
     *     value that was obtained by adding both
     *     original MonetaryAmounts.
     */
    public MonetaryAmount add(MonetaryAmount ma) {
        return new MonetaryAmount(this.monetaryAmount + ma.monetaryAmount);
    }

    /**
     * Given a custom money symbol, represent this
     * MonetaryAmount as a String using that money
     * symbol (e.g. "$2.50", "£4.20").
     * @param symbol String representing money symbol
     *               to be used in the final String.
     * @return Returns a String that has the amount
     *     set to 2 decimal places, and prefixed by
     *     the given money symbol in String symbol.
     */
    public String getStringWithMoneySymbol(String symbol) {
        return symbol + String.format("%.2f", this.monetaryAmount);
    }

    @Override
    public int compareTo(MonetaryAmount m) {
        return (int) Math.signum(this.monetaryAmount - m.monetaryAmount);
    }

    @Override
    public boolean equals(Object o) {
        return o == this
                || (o instanceof MonetaryAmount
                && this.monetaryAmount == ((MonetaryAmount) o).monetaryAmount);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.monetaryAmount);
    }

    @Override
    public String toString() {
        // use $ as default symbol just to denote a monetary amount
        return this.getStringWithMoneySymbol("$");
    }
}
