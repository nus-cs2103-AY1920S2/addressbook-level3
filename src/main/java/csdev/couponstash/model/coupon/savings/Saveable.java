package csdev.couponstash.model.coupon.savings;

/**
 * Represents benefits (tangible and intangible) that coupons
 * may offer that are not necessarily associated with a
 * quantifiable monetary value.
 */
public class Saveable implements Comparable<Saveable> {
    private final String savedItem;

    /**
     * Constructor for a Saveable, given a String
     * that represents the saved item.
     * @param s String representing saved item.
     */
    public Saveable(String s) {
        this.savedItem = s;
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
        return this.savedItem;
    }
}
