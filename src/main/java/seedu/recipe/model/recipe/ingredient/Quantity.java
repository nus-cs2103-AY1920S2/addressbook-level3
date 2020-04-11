package seedu.recipe.model.recipe.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Quantity in an Ingredient.
 */
public class Quantity {
    public static final String MESSAGE_CONSTRAINTS_MAGNITUDE = "The quantity must be more than zero and less than "
            + "ten thousand.";
    public static final String MESSAGE_CONSTRAINTS_UNITS = "The available units of measurement are "
            + "g, ml, tbsp, tsp and cup.";
    public static final int CUP_TO_GRAM_GRAIN = 145;
    public static final int CUP_TO_GRAM_PROTEIN = 225;
    public static final int CUP_TO_GRAM_VEG = 200;
    public static final int CUP_TO_GRAM_FRUIT = 160;
    public static final int TBSP_TO_GRAM = 15;
    public static final int TSP_TO_GRAM = 4;
    private double magnitude;
    private Unit unit;

    public Quantity(double magnitude, Unit unit) {
        requireNonNull(unit);
        checkArgument(isValidQuantity(magnitude), MESSAGE_CONSTRAINTS_MAGNITUDE);
        this.magnitude = magnitude;
        this.unit = unit;
    }

    public Quantity() {}

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(double magnitude) {
        return magnitude > 0 && magnitude < 10000;
    }

    /**
     * According to Metric measurements and based on ingredient type (averages denser ingredients).
     * @param ingredientType from 4 main ingredients.
     * @return new Quantity with unit in gram.
     * @see <a href="https://www.thecalculatorsite.com/cooking/cups-grams.php">measurementConverter</a>
     */
    public Quantity convertToGram(MainIngredientType ingredientType) {
        double newMagnitude = this.magnitude;
        switch(this.unit) {
        case CUP:
            if (ingredientType == MainIngredientType.GRAIN) {
                //eg rice
                newMagnitude = this.magnitude * CUP_TO_GRAM_GRAIN;
            } else if (ingredientType == MainIngredientType.PROTEIN) {
                //eg minced beef
                newMagnitude = this.magnitude * CUP_TO_GRAM_PROTEIN;
            } else if (ingredientType == MainIngredientType.VEGETABLE) {
                //eg beans
                newMagnitude = this.magnitude * CUP_TO_GRAM_VEG;
            } else if (ingredientType == MainIngredientType.FRUIT) {
                //eg apple
                newMagnitude = this.magnitude * CUP_TO_GRAM_FRUIT;
            }
            break;
        case MILLILITER:
            //same magnitude
            break;
        case TABLESPOON:
            newMagnitude = this.magnitude * TBSP_TO_GRAM;
            break;
        case TEASPOON:
            newMagnitude = this.magnitude * TSP_TO_GRAM;
            break;
        case GRAM:
            //same magnitude
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + this.unit);
        }
        return new Quantity(newMagnitude, Unit.GRAM);
    }

    public Unit getUnit() {
        return unit;
    }

    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Sums up the quantity in {@code toAdd} with the current quantity.
     */
    public Quantity addQuantityInGram(Quantity toAdd, MainIngredientType mainIngredientType) {
        Quantity currentInGram = convertToGram(mainIngredientType);
        Quantity toAddInGram = toAdd.convertToGram(mainIngredientType);
        double sumMagnitude = currentInGram.magnitude + toAddInGram.magnitude;
        return new Quantity(sumMagnitude, Unit.GRAM);
    }

    @Override
    public String toString() {
        return String.format("%.01f", magnitude) + unit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && unit.equals(((Quantity) other).unit) // state check
                && magnitude == ((Quantity) other).magnitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(magnitude, unit);
    }
}
