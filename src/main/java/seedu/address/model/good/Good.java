package seedu.address.model.good;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
/**
 * Represents a Good in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Good {

    // Identity fields
    private final GoodName goodName;


    public Good(GoodName goodName) {
        requireNonNull(goodName);
        this.goodName = goodName;
    }

    public GoodName getGoodName() {
        return goodName;
    }

    /**
     * Returns true if both goods have the same name.
     */
    public boolean isSameGood(Good otherGood) {
        if (otherGood == this) {
            return true;
        }

        return otherGood != null
                && otherGood.getGoodName().equals(getGoodName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Good)) {
            return false;
        }

        Good otherGood = (Good) other;
        return otherGood.getGoodName().equals(getGoodName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(goodName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getGoodName());
        return builder.toString();
    }

}
