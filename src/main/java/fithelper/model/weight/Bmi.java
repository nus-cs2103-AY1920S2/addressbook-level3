package fithelper.model.weight;

import static fithelper.commons.util.AppUtil.checkArgument;
import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import fithelper.model.profile.Height;

/**
 * Represents a User's BMI in one weight record.
 * Guarantees: immutable; is valid as declared in {@link #isValidBMI(Height, WeightValue)}
 */
public class Bmi {

    public static final String MESSAGE_CONSTRAINTS = "BMI should be computed by both height and weight.";
    public static final double T = 0.0001; // tolerance for double comparision.

    public final Height height;
    public final WeightValue weightValue;

    public final double value;

    /**
     * Constructs an {@code BMI}.
     *
     * @param height A valid height of user.
     * @param weightValue A valid weight of user
     */
    public Bmi(Height height, WeightValue weightValue) {
        requireAllNonNull(height, weightValue);
        checkArgument(isValidBMI(height, weightValue), MESSAGE_CONSTRAINTS);
        this.height = height;
        this.weightValue = weightValue;
        value = weightValue.value / (height.value * height.value);
    }

    /**
     * Returns true if a given string is a valid BMI.
     */
    public static boolean isValidBMI(Height testHeight, WeightValue testWeight) {
        return testHeight.value != 0 && testWeight.value != 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bmi // instanceof handles nulls
                && Math.abs(value - ((Bmi) other).value) < T); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
