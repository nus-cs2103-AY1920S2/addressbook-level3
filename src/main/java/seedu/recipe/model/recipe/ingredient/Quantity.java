package seedu.recipe.model.recipe.ingredient;

/**
 * Represents a Quantity in an Ingredient.
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS = "The available units of measurement are g, ml, tbsp, tsp and cup.";
    private double magnitude;
    private Unit unit;

    public Quantity(double magnitude, Unit unit) {
        this.magnitude = magnitude;
        this.unit = unit;
    }

    public Quantity() {
    }

    /**
     * According to Metric measurements and based on ingredient type (averages denser ingredients).
     * from https://www.thecalculatorsite.com/cooking/cups-grams.php.
     * @param ingredientType
     */
    public double convertToGram(MainIngredientType ingredientType) {
        double newMagnitude = this.magnitude;
        switch(this.unit) {
            case CUP:
                if(ingredientType == MainIngredientType.GRAIN) {
                    //eg rice
                    newMagnitude = this.magnitude * 145;
                } else if(ingredientType == MainIngredientType.PROTEIN) {
                    //eg minced beef
                    newMagnitude = this.magnitude * 225;
                } else if(ingredientType == MainIngredientType.VEGETABLE) {
                    //eg beans
                    newMagnitude = this.magnitude * 200;
                } else if (ingredientType == MainIngredientType.FRUIT) {
                    //eg apple
                    newMagnitude = this.magnitude * 160;
                }
                break;
            case MILLILITER:
                //same magnitude
                break;
            case TABLESPOON:
                newMagnitude = this.magnitude * 15;
                break;
            case TEASPOON:
                newMagnitude = this.magnitude * 4;
                break;
            case GRAM:
                //same magnitude
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.unit);
        }
        return newMagnitude;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return String.format("%.01f", magnitude) + unit;
    }
}
