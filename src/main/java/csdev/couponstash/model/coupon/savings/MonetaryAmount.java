package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;

/**
 * Represents a monetary amount that
 * can be saved by a coupon.
 * Immutable.
 */
public class MonetaryAmount implements Comparable<MonetaryAmount> {
    public static final String MESSAGE_CONSTRAINTS =
            "Monetary amount should be positive, and accurate to at most 2 decimal places";

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
        return monetaryAmount >= 0 && ((monetaryAmount * 100) % 1 == 0);
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
        return "$" + String.format("%.2f", this.monetaryAmount);
    }
}
