package csdev.couponstash.model.coupon.savings;

import java.util.Objects;

/**
 * Represents benefits (tangible and intangible) that coupons
 * may offer that are not necessarily associated with a
 * quantifiable monetary value.
 * Guaranteed to be immutable.
 */
public class Saveable implements Comparable<Saveable> {
    public static final String MESSAGE_CONSTRAINTS = "Saveables should not be blank "
            + "or null.";

    private final String savedItem;

    /**
     * Constructor for a Saveable, given a String
     * that represents the saved item.
     * @param s String representing saved item.
     */
    public Saveable(String s) {
        this.savedItem = s;
    }

    /**
     * Checks whether this String is a valid
     * Saveable value. Used by JsonAdaptedSaveable
     * and Savings classes.
     * @param s The String to be checked.
     */
    public static boolean isValidSaveableValue(String s) {
        return s != null && !s.isBlank();
    }

    /**
     * Gets the value of this Saveable as a String.
     * The difference from the toString() method is that
     * getValue() may return null in exceptional cases,
     * but toString() will never return null.
     * @return String stored in the Saveable.
     */
    public String getValue() {
        return this.savedItem;
    }

    @Override
    public int compareTo(Saveable p) {
        return (int) Math.signum(this.savedItem.length() - p.savedItem.length());
    }

    @Override
    public boolean equals(Object o) {
        return o == this
                || (o instanceof Saveable)
                && this.savedItem.equals(((Saveable) o).savedItem);
    }

    @Override
    public int hashCode() {
        return this.savedItem.hashCode();
    }

    @Override
    public String toString() {
        return Objects.toString(this.savedItem);
    }
}
