package seedu.address.model.transaction;

/**
 * Represents an Amount of money.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount implements Comparable<Amount> {
    public static final String MESSAGE_CONSTRAINTS =
            "Amount of money should be non-negative, have up to two decimal places, and not be too large"
            + " (not exceeding ninety quadrillion dollars)";
    public static final String VALIDATION_REGEX = "\\d+([.]\\d{1,2})?";

    public static final long MAX_VALUE = Long.MAX_VALUE;

    // Amount is stored in cents to prevent floating point errors.
    public final long amountInCents;

    /**
     * Constructs an {@code Amount}. Private constructor accepts a long.
     */
    private Amount(long amountInCents) {
        assert amountInCents >= 0 : "Amounts should be non-negative";
        this.amountInCents = amountInCents;
    }
    /**
     * Constructs an {@code Amount}. Public constructor accepts a double.
     *
     * @param amountInDollars A valid amount of money.
     */

    public Amount(double amountInDollars) {
        this(Math.round(amountInDollars * 100));
    }

    /**
     * Returns true if a given amount (in String form) is valid.
     */
    public static boolean isValidAmount(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        // Check for long overflow
        try {
            double doubleTest = Double.parseDouble(test);
            return doubleTest * 100 <= MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns an Amount equivalent to zero.
     */
    public static Amount zero() {
        return new Amount(0);
    }

    /**
     * Returns an Amount equivalent to the maximum amount.
     */
    public static Amount max() {
        return new Amount(MAX_VALUE);
    }

    // @@author cheyannesim
    /**
     * Adds {@code amountToAdd} to the current amount.
     *
     * @return the total amount after adding, or maximum amount if overflow occurs.
     */
    public Amount add(Amount amountToAdd) {
        try {
            long totalAmount = Math.addExact(amountToAdd.amountInCents, amountInCents);
            return new Amount(totalAmount);
        } catch (ArithmeticException e) {
            return Amount.max();
        }
    }
    // @@author

    /**
     * Returns the difference between this amount and a given amount.
     */
    public Amount difference(Amount o) {
        long difference = Math.abs(amountInCents - o.amountInCents);
        return new Amount(difference);
    }

    /**
     * Returns true if the amount is zero.
     */
    public boolean isZero() {
        return this.amountInCents == 0;
    }

    @Override
    public String toString() {
        return "$" + inDollars();
    }

    public String inDollars() {
        return String.format("%d.%02d", amountInCents / 100, amountInCents % 100);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amountInCents == ((Amount) other).amountInCents); // state check
    }

    @Override
    public int hashCode() {
        return Long.valueOf(amountInCents).hashCode();
    }

    @Override
    public int compareTo(Amount o) {
        return Long.compare(amountInCents, o.amountInCents);
    }

    public boolean isLessThan(Amount o) {
        return amountInCents < o.amountInCents;
    }
}
