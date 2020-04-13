package seedu.eylah.diettracker.model.self;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

/**
 * Represents the BMI in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Bmi {

    public static final String MESSAGE_CONSTRAINTS =
            "Please input a height and a weight to calculate BMI";

    /*
     * The first character of the height must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[-+]?\\d*\\.?\\d*$*";

    public final Float height;
    public final Float weight;
    public final String bmi;
    public final Float bmiFloat;
    public final String category;

    /**
     * Constructs a {@code Bmi} using {@code Height} and {@code Weight} values.
     *
     * @param height A valid height.
     * @param weight A valid weight.
     */
    public Bmi(Height height, Weight weight) {
        requireNonNull(height);
        checkArgument(isValidHeight(height.toString()), MESSAGE_CONSTRAINTS);
        checkArgument(isValidWeight(weight.toString()), MESSAGE_CONSTRAINTS);

        this.height = height.getHeightFloat();
        this.weight = weight.getWeightFloat();
        this.bmiFloat = calcBmi();
        this.bmi = this.bmiFloat.toString();
        if (bmiFloat < 25.0 && bmiFloat >= 18.5) {
            this.category = "Normal";
        } else if (bmiFloat >= 16.0 && bmiFloat < 18.5) {
            this.category = "Underweight";
        } else if (bmiFloat < 16.0) {
            this.category = "Severely Underweight";
        } else if (bmiFloat >= 25.0 && bmiFloat < 30.0) {
            this.category = "Overweight";
        } else {
            this.category = "Obese";
        }
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Method to calculate BMI. Formula used to calculate BMI is
     * Weight (in kilograms) divided by Height squared (in metres^2).
     * @return Float of the BMI value.
     */
    private Float calcBmi() {
        // Weight/Height^2
        float result = (float) (this.weight / Math.pow(this.height / 100, 2));
        return result;
    }

    public float getBmi() {
        return this.bmiFloat;
    }

    public String getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return "" + bmi;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
                && bmi.equals(((Bmi) other).bmi)); // state check
    }

    @Override
    public int hashCode() {
        return bmi.hashCode();
    }

}
