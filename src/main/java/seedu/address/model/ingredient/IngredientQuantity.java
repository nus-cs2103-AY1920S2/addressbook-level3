package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.fraction.MixedFraction;
import seedu.address.model.ingredient.exceptions.NonPositiveIngredientQuantityException;

/**
 * Represents the quantity of an ingredient.
 * Guarantees: immutable; is always valid
 */
public class IngredientQuantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Ingredient quantities should only contain a positive value and a unit, where the value can be "
            + "whole numbers, decimals, or fractions, and the unit should only contain alphabets";

    private static final String DECIMAL_REGEX = "([1-9][\\p{Digit}]*(\\.[\\p{Digit}]+)?)"
            + "|(0?\\.(?=.*[1-9])[\\p{Digit}]+)";
    private static final String FRACTION_REGEX = "[1-9][\\p{Digit}]*( +[1-9][\\p{Digit}]*)?/[1-9][\\p{Digit}]*";
    private static final String UNIT_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    /*
     * The ingredient quantity must consist of a whole number, decimal, or fraction, and an optional unit.
     */
    public static final String VALIDATION_REGEX = String.format("((%s)|(%s)) *(%s)?",
            DECIMAL_REGEX, FRACTION_REGEX, UNIT_REGEX);

    private static final Pattern DECIMAL_PATTERN = Pattern.compile(DECIMAL_REGEX);
    private static final Pattern FRACTION_PATTERN = Pattern.compile(FRACTION_REGEX);
    private static final Pattern UNIT_PATTERN = Pattern.compile(UNIT_REGEX);

    private static final int LARGEST_DENOMINATOR = 6;

    public final Number value;
    public final String unit;

    /**
     * Constructs an {@code IngredientQuantity}.
     *
     * @param ingredientQuantity A valid ingredient quantity.
     */
    public IngredientQuantity(String ingredientQuantity) {
        requireNonNull(ingredientQuantity);
        checkArgument(isValidIngredientQuantity(ingredientQuantity), MESSAGE_CONSTRAINTS);
        this.value = parseValue(ingredientQuantity);
        this.unit = parseUnit(ingredientQuantity);
    }

    IngredientQuantity(Number value, String unit) {
        requireAllNonNull(value, unit);
        this.value = value;
        this.unit = unit;
    }

    /**
     * Returns true if a given string is a valid ingredient quantity.
     */
    public static boolean isValidIngredientQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the specified ingredient quantity has the same unit as the ingredient quantity.
     */
    public boolean hasSameUnitAs(IngredientQuantity other) {
        return this.unit.equals(other.unit);
    }

    /**
     * Adds the specified ingredient quantity to the ingredient quantity, if the ingredient quantities are compatible.
     *
     * @param other the ingredient quantity to be added.
     * @return a new ingredient quantity with the specified ingredient quantity added.
     */
    public IngredientQuantity add(IngredientQuantity other) {
        checkArgument(hasSameUnitAs(other));

        Number newValue = null;
        if (this.value instanceof BigDecimal && other.value instanceof BigDecimal) {
            newValue = ((BigDecimal) this.value).add((BigDecimal) other.value);
        } else if (this.value instanceof MixedFraction && other.value instanceof MixedFraction) {
            newValue = ((MixedFraction) this.value).add((MixedFraction) other.value);
        } else if (this.value instanceof BigDecimal && other.value instanceof MixedFraction) {
            newValue = MixedFraction.getFromBigDecimal(((BigDecimal) this.value)).add((MixedFraction) other.value);
        } else if (this.value instanceof MixedFraction && other.value instanceof BigDecimal) {
            newValue = ((MixedFraction) this.value).add(MixedFraction.getFromBigDecimal(((BigDecimal) other.value)));
        }

        if (newValue instanceof MixedFraction && ((MixedFraction) newValue).getDenominator() > LARGEST_DENOMINATOR) {
            MixedFraction mixedFraction = (MixedFraction) newValue;
            newValue = new BigDecimal(mixedFraction.getNumerator())
                    .divide(new BigDecimal(mixedFraction.getDenominator()));
        }

        assert newValue != null;
        return new IngredientQuantity(newValue, unit);
    }

    /**
     * Subtracts the specified ingredient quantity from the ingredient quantity, if the ingredient quantities are
     * compatible.
     * If the specified ingredient quantity is larger, the value of the ingredient quantity returned will be 0.
     *
     * @param other the ingredient quantity to be subtracted.
     * @return a new ingredient quantity with the specified ingredient quantity subtracted.
     */
    public IngredientQuantity subtract(IngredientQuantity other) {
        checkArgument(hasSameUnitAs(other));

        Number newValue = null;
        if (this.value instanceof BigDecimal && other.value instanceof BigDecimal) {
            newValue = ((BigDecimal) this.value).subtract((BigDecimal) other.value);
        } else if (this.value instanceof MixedFraction && other.value instanceof MixedFraction) {
            newValue = ((MixedFraction) this.value).subtract((MixedFraction) other.value);
        } else if (this.value instanceof BigDecimal && other.value instanceof MixedFraction) {
            newValue = MixedFraction.getFromBigDecimal(((BigDecimal) this.value))
                    .subtract((MixedFraction) other.value);
        } else if (this.value instanceof MixedFraction && other.value instanceof BigDecimal) {
            newValue = ((MixedFraction) this.value)
                    .subtract(MixedFraction.getFromBigDecimal(((BigDecimal) other.value)));
        }

        if (newValue instanceof MixedFraction && ((MixedFraction) newValue).getDenominator() > LARGEST_DENOMINATOR) {
            MixedFraction mixedFraction = (MixedFraction) newValue;
            newValue = new BigDecimal(mixedFraction.getNumerator())
                    .divide(new BigDecimal(mixedFraction.getDenominator()));
        }

        assert newValue != null;
        if (newValue.doubleValue() <= 0) {
            throw new NonPositiveIngredientQuantityException();
        }

        return new IngredientQuantity(newValue, unit);
    }

    /**
     * Returns the proportion of {@code other} with respect to the ingredient.
     * @return a double value between 0 and 1 (inclusive)
     */
    public double asProportionOf(IngredientQuantity other) {
        checkArgument(hasSameUnitAs(other));

        double proportion = 0;
        if (this.value instanceof BigDecimal && other.value instanceof BigDecimal) {
            try {
                proportion = ((BigDecimal) this.value).divide((BigDecimal) other.value).doubleValue();
            } catch (ArithmeticException e) {
                proportion = this.value.doubleValue() / other.value.doubleValue();
            }
        } else if (this.value instanceof MixedFraction && other.value instanceof MixedFraction) {
            proportion = ((MixedFraction) this.value).divide((MixedFraction) other.value).doubleValue();
        } else if (this.value instanceof BigDecimal && other.value instanceof MixedFraction) {
            proportion = MixedFraction.getFromBigDecimal(((BigDecimal) this.value))
                    .divide((MixedFraction) other.value).doubleValue();
        } else if (this.value instanceof MixedFraction && other.value instanceof BigDecimal) {
            proportion = ((MixedFraction) this.value)
                    .divide(MixedFraction.getFromBigDecimal(((BigDecimal) other.value))).doubleValue();
        } else {
            assert false;
        }

        if (proportion > 1) {
            return 1;
        }
        return proportion;
    }

    /**
     * Returns the value of an ingredient's quantity.
     *
     * @param ingredientQuantity The given string representing an ingredient's quantity.
     * @return The value of the ingredient's IngredientQuantity.
     */
    public static Number parseValue(String ingredientQuantity) {
        checkArgument(isValidIngredientQuantity(ingredientQuantity));

        Number parsedValue = null;
        Matcher decimalMatcher = DECIMAL_PATTERN.matcher(ingredientQuantity);
        Matcher mixedFractionMatcher = FRACTION_PATTERN.matcher(ingredientQuantity);
        if (mixedFractionMatcher.find()) {
            parsedValue = MixedFraction.parseUnsignedMixedFraction(mixedFractionMatcher.group().trim());
        } else if (decimalMatcher.find()) {
            parsedValue = new BigDecimal(decimalMatcher.group().trim());
        }

        if (parsedValue instanceof MixedFraction
                && ((MixedFraction) parsedValue).getDenominator() > LARGEST_DENOMINATOR) {
            parsedValue = ((MixedFraction) parsedValue).doubleValue();
        }

        assert parsedValue != null;
        return parsedValue;
    }

    /**
     * Returns the unit of an ingredient's quantity.
     *
     * @param ingredientQuantity The given string representing an ingredient's quantity.
     * @return The unit of the ingredient's IngredientQuantity.
     */
    public static String parseUnit(String ingredientQuantity) {
        checkArgument(isValidIngredientQuantity(ingredientQuantity));
        Matcher unitMatcher = UNIT_PATTERN.matcher(ingredientQuantity);
        if (unitMatcher.find()) {
            return unitMatcher.group().trim();
        }
        return "";
    }

    @Override
    public String toString() {
        if (unit.length() == 0) {
            return value.toString();
        }
        return String.format("%s %s", value, unit);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientQuantity // instanceof handles nulls
                && value.equals(((IngredientQuantity) other).value)
                && unit.equals(((IngredientQuantity) other).unit));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }

}
