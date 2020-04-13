package seedu.address.testutil;

import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * A utility class to help with building {@code Ingredient} objects.
 */
public class IngredientBuilder {

    public static final String DEFAULT_INGREDIENT_NAME = "Apples";
    public static final String DEFAULT_INGREDIENT_QUANTITY = "10";

    private IngredientName ingredientName;
    private IngredientQuantity ingredientQuantity;

    public IngredientBuilder() {
        ingredientName = new IngredientName(DEFAULT_INGREDIENT_NAME);
        ingredientQuantity = new IngredientQuantity(DEFAULT_INGREDIENT_QUANTITY);
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        ingredientName = ingredientToCopy.getName();
        ingredientQuantity = ingredientToCopy.getQuantity();
    }

    /**
     * Sets the {@code IngredientName} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withName(String ingredientName) {
        this.ingredientName = new IngredientName(ingredientName);
        return this;
    }

    /**
     * Sets the {@code IngredientQuantity} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withQuantity(String ingredientQuantity) {
        this.ingredientQuantity = new IngredientQuantity(ingredientQuantity);
        return this;
    }

    public Ingredient build() {
        return new Ingredient(ingredientName, ingredientQuantity);
    }

}
