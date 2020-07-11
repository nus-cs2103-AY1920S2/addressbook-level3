package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;

import java.util.Objects;


/**
 * Represents a monetary amount that
 * can be saved by a coupon.
 * Immutable.
 */
public class MonetaryAmount implements Comparable<MonetaryAmount> {
    public static final String MESSAGE_CONSTRAINTS = "Invalid format for monetary amount! "
            + "Monetary amount should be positive, and accurate to at most 2 decimal places";
    private final int integerAmount;
    private final int decimalAmount;

    /**
     * Constructs a MonetaryAmount, only if the integer amount
     * and decimal amount specified are valid. Integer amount
     * has to be a non-negative integer, and decimal amount can
     * only be from 0 to 99 (inclusive).
     *
     * @param integerAmount The integer amount which represents
     *                      whole units of currency (e.g. dollars,
     *                      pounds, euros).
     * @param decimalAmount The decimal amount which represents
     *                      decimal units of currency (e.g. cents,
     *                      pennies, centavos).
     */
    public MonetaryAmount(int integerAmount, int decimalAmount) {
        checkArgument(MonetaryAmount.isValidMonetaryAmount(integerAmount, decimalAmount),
                MonetaryAmount.MESSAGE_CONSTRAINTS);
        this.integerAmount = integerAmount;
        this.decimalAmount = decimalAmount;
    }

    /**
     * Constructs a MonetaryAmount using a double argument. This
     * will convert the double into two integers, and round the
     * value if it is not exact to 2 decimal places.
     *
     * This is used by the storage component, as MonetaryAmounts
     * are stored as a double within the couponStash JSON file.
     *
     * @param monetaryAmount The double to be used.
     */
    public MonetaryAmount(double monetaryAmount) {
        int intAmount = (int) Math.floor(monetaryAmount); // get rid of decimal
        int decAmount = ((int) Math.round(monetaryAmount * 100)) % 100;
        checkArgument(MonetaryAmount.isValidMonetaryAmount(intAmount, decAmount),
                MonetaryAmount.MESSAGE_CONSTRAINTS);
        this.integerAmount = intAmount;
        this.decimalAmount = decAmount;
    }

    /**
     * Constructs a MonetaryAmount using a String. This will
     * attempt to parse the integer amount and decimal amount
     * separately, and throw an error if this fails.
     *
     * @param monetaryAmount The String to be used.
     */
    public MonetaryAmount(String monetaryAmount) {
        String[] intAndDecimalAmounts = monetaryAmount.split("\\.");
        if (intAndDecimalAmounts.length == 0 || intAndDecimalAmounts.length > 2
                // throw exception if dot present with nothing after
                || monetaryAmount.endsWith(".")) {
            throw new IllegalArgumentException(MonetaryAmount.MESSAGE_CONSTRAINTS);
        }

        int intAmount;
        int decAmount;
        try {
            intAmount = Integer.parseInt(intAndDecimalAmounts[0]);
            if (intAndDecimalAmounts.length == 1) {
                decAmount = 0;
            } else if (intAndDecimalAmounts[1].length() == 1) {
                // add a zero, so that 8.3 means 8.30 instead of 8.03
                decAmount = Integer.parseInt(intAndDecimalAmounts[1] + "0");
            } else if (intAndDecimalAmounts[1].length() > 2) {
                // restrict monetary amounts to 1 or 2 decimal places
                throw new IllegalArgumentException(MonetaryAmount.MESSAGE_CONSTRAINTS);
            } else {
                decAmount = Integer.parseInt(intAndDecimalAmounts[1]);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MonetaryAmount.MESSAGE_CONSTRAINTS);
        }

        checkArgument(MonetaryAmount.isValidMonetaryAmount(intAmount, decAmount),
                MonetaryAmount.MESSAGE_CONSTRAINTS);
        this.integerAmount = intAmount;
        this.decimalAmount = decAmount;
    }

    /**
     * Cloning constructor for an MonetaryAmount.
     *
     * @param ma The MonetaryAmount to be cloned.
     */
    public MonetaryAmount(MonetaryAmount ma) {
        this.integerAmount = ma.integerAmount;
        this.decimalAmount = ma.decimalAmount;
    }

    /**
     * Gets the value of this MonetaryAmount
     * as a non-negative double that has only
     * 2 decimal places at most.
     *
     * @return Double representing MonetaryAmount.
     */
    public double getValue() {
        if (this.decimalAmount < 10) {
            return Double.parseDouble(this.integerAmount + ".0" + this.decimalAmount);
        } else {
            return Double.parseDouble(this.integerAmount + "." + this.decimalAmount);
        }
    }

    /**
     * Gets the integer value of this MonetaryAmount
     * as an integer (amount representing full units
     * of a certain currency, e.g. dollars, pounds).
     *
     * @return Integer representing the integer value
     *         stored in this MonetaryAmount.
     */
    public int getRawIntegerValue() {
        return this.integerAmount;
    }

    /**
     * Gets the decimal value of this MonetaryAmount as
     * an integer (amount representing fractional units
     * of a certain currency, e.g. cents, pennies).
     *
     * @return Integer representing the decimal value
     *         stored in this MonetaryAmount.
     */
    public int getRawDecimalValue() {
        return this.decimalAmount;
    }

    /**
     * Checks if this double is suitable for use
     * in the MonetaryAmount (should be at most
     * 2 decimal places).
     *
     * @param integerAmount The integer amount which represents
     *                      whole units of currency (e.g. dollars,
     *                      pounds, euros).
     * @param decimalAmount The decimal amount which represents
     *                      decimal units of currency (e.g. cents,
     *                      pennies, centavos).
     */
    public static boolean isValidMonetaryAmount(int integerAmount, int decimalAmount) {
        return integerAmount >= 0 && decimalAmount >= 0 && decimalAmount < 100;
    }

    /**
     * Adds this MonetaryAmount to another MonetaryAmount,
     * resulting in a new MonetaryAmount with value
     * greater than the original two.
     *
     * If this results in integer overflow, return this
     * monetary amount, unchanged.
     *
     * @param ma The other MonetaryAmount to add to.
     * @return Returns a new MonetaryAmount with
     *     value that was obtained by adding both
     *     original MonetaryAmounts.
     */
    public MonetaryAmount add(MonetaryAmount ma) {
        int newIntAmount = this.integerAmount + ma.integerAmount;
        int newDecimalAmount = this.decimalAmount + ma.decimalAmount;
        // check for overflow
        if (newIntAmount < 0) {
            return this;
        }

        if (newDecimalAmount >= 100) {
            newIntAmount = newIntAmount + newDecimalAmount / 100;
            newDecimalAmount = newDecimalAmount % 100;
        }

        return new MonetaryAmount(newIntAmount, newDecimalAmount);
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
        return symbol + String.format("%.2f", this.getValue());
    }

    @Override
    public int compareTo(MonetaryAmount m) {
        return (int) Math.signum(this.getValue() - m.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof MonetaryAmount) {
            MonetaryAmount ma = (MonetaryAmount) o;
            return this.integerAmount == ma.integerAmount
                    && this.decimalAmount == ma.decimalAmount;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.integerAmount, this.decimalAmount);
    }

    @Override
    public String toString() {
        // use $ as default symbol just to denote a monetary amount
        return this.getStringWithMoneySymbol("$");
    }
}
