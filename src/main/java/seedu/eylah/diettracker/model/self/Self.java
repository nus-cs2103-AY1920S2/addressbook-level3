package seedu.eylah.diettracker.model.self;

import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.eylah.diettracker.model.Mode;

/**
 * Represents a User in the diet tracker of EYLAH.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Self {

    // Data fields
    private static Height height;
    private static Weight weight;
    private static Mode mode;

    /**
     * Every field must be present
     * height and weight fields must not be null.
     */
    public Self(Height height, Weight weight) {
        requireAllNonNull(height, weight);
        this.height = height;
        this.weight = weight;
    }

    public static Height getHeight() {
        return height;
    }

    public static Weight getWeight() {
        return weight;
    }

    public static Mode getMode() { return mode; }

    public static void setHeight(Height newHeight) {
        height = newHeight;
    }

    public static void setWeight(Weight newWeight) {
        weight = newWeight;
    }

    public static void setMode(Mode newMode) { mode = newMode; }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(height, weight, mode);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Height: ")
                .append(getHeight())
                .append(" Weight: ")
                .append(getWeight())
                .append(" Mode: ")
                .append(getMode());
        return builder.toString();
    }
}
