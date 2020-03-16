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
    private final int count;

    /**
     * Constructor for a Saveable, given a String
     * that represents the saved item. The count of
     * the Saveable will be set to 1, to represent
     * the existence of a singular item.
     * @param s String representing saved item.
     */
    public Saveable(String s) {
        this.savedItem = s;
        this.count = 1;
    }

    /**
     * Constructor for a Saveable, given a String
     * that represents the saved item as well as
     * the number of such items
     * @param s String representing saved item.
     * @param count Int representing number of the
     *              saved item.
     */
    public Saveable(String s, int count) {
        this.savedItem = s;
        this.count = count;
    }

    /**
     * Checks whether this String and this int forms
     * a valid Saveable value. Used by JsonAdaptedSaveable
     * and Savings classes.
     * @param s The String to be checked.
     */
    public static boolean isValidSaveableValue(String s, int count) {
        return s != null && !s.isBlank() && count > 0;
    }

    /**
     * Gets the value of this Saveable as a String.
     * The difference from the toString() method is that
     * getValue() may return null in exceptional cases,
     * but toString() will never return null.
     * @return String stored in the Saveable.
     */
    public String getValue() {
        if (this.savedItem == null) {
            return null;
        } else {
            return this.count + "x " + this.savedItem;
        }
    }

    /**
     * Gets the count value of this Saveable as an
     * int, which represents the quantity of the item
     * that is represented by the Saveable.
     * @return Int representing count, or quantity,
     *     of this Saveable item.
     */
    public int getCount() {
        return this.count;
    }

    @Override
    public int compareTo(Saveable p) {
        // compare count first, before comparing length of string
        if (this.count == p.count) {
            return (int) Math.signum(this.savedItem.length() - p.savedItem.length());
        } else {
            return (int) Math.signum(this.count - p.count);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Saveable) {
            Saveable s = (Saveable) o;
            return this.savedItem.equals(s.savedItem)
                    && this.count == s.count;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.savedItem, this.count);
    }

    @Override
    public String toString() {
        return count + "x " + this.savedItem;
    }
}
