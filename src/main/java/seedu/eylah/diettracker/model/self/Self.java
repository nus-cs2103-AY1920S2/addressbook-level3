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
    private Height height;
    private Weight weight;
    private Mode mode;

    /**
     * Every field must be present
     * height and weight fields must not be null.
     * mode set to default value of MAINTAIN
     */
    public Self(Height height, Weight weight) {
        requireAllNonNull(height, weight);
        this.height = height;
        this.weight = weight;
        this.mode = Mode.MAINTAIN;
    }

    public Self() {
        this.height = new Height(0);
        this.weight = new Weight(0);
        this.mode = Mode.MAINTAIN;

    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    public Mode getMode() {
        return mode;
    }

    public void setHeight(Height newHeight) {
        height = newHeight;
    }

    public void setWeight(Weight newWeight) {
        weight = newWeight;
    }

    public void setMode(Mode newMode) {
        mode = newMode;
    }

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
