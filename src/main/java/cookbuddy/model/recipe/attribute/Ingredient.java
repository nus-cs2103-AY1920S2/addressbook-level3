package cookbuddy.model.recipe.attribute;

import static cookbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.List;

/**
 * Represents a Recipe's ingredient in its {@code IngredientList}. Guarantees:
 * immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Ingredient {

    public static final String MESSAGE_CONSTRAINTS = "Each ingredient should be of the form:"
            + "'ing/ing1, ing1qty; ing2, ing2qty;...'."
            + "Spaces are optional. Valid ingredient quantities are specified in the User Guide. ";

    public final String name;
    private String quantity;
    private Quantity quantity2;

    /**
     * Construct an Ingredient from {@code ingredientString}; the parameter is
     * required to be non-null, and must follow a pattern.
     *
     * @param ingredientString the {@link String} to be decoded
     */
    public Ingredient(String ingredientString) {
        requireNonNull(ingredientString);

        List<String> ingredientParts = List.of(ingredientString.split(","));
        ingredientParts.forEach(String::trim);

        checkArgument(isValidName(ingredientParts.get(0)), MESSAGE_CONSTRAINTS);
        checkArgument(isValidName(ingredientParts.get(1)), MESSAGE_CONSTRAINTS);

        this.name = ingredientParts.get(0);
        this.quantity = ingredientParts.get(1);
        // new Quantity(ingredientParts.get(1));
    }

    /**
     * Returns {@code true} if {@code nameString} is <em>not</em> blank.
     *
     * @param nameString the {@link String} to be tested.
     */
    private Boolean isValidName(String nameString) {
        return !nameString.isBlank();
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantityString) {
        this.quantity = quantityString;
        // new Quantity(quantityString);
    }

    @Override
    public String toString() {
        return this.name + ", " + this.quantity;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Ingredient // instanceof handles nulls
                        && name.equals(((Ingredient) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
