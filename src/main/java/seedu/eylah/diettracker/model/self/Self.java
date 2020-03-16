package seedu.eylah.diettracker.model.self;

import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a User in the diet tracker of EYLAH.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Self {

    // Data fields
    private static Height height;
    private static Weight weight;

    /**
     * Every field must be present
     * height and weight fields must not be null.
     */
    public Self(Height ht, Weight wt) {
        requireAllNonNull(ht, wt);
        this.height = ht;
        this.weight = wt;
    }

    public static Height getHeight() {
        return height;
    }

    public static Weight getWeight() {
        return weight;
    }

    public static void setHeight(Height ht) {
        height = ht;
    }

    public static void setWeight(Weight wt) {
        weight = wt;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(height, weight);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Height: ")
                .append(getHeight())
                .append(" Weight: ")
                .append(getWeight());
        return builder.toString();
    }
}
