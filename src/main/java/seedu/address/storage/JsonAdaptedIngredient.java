package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Ingredient;
import seedu.address.model.recipe.ingredient.IngredientType;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Jackson-friendly version of {@link Ingredient}.
 */
class JsonAdaptedIngredient {

    private final String ingredientName;
    private final double quantity;
    private final IngredientType ingredientType;

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given {@code ingredientName},
     * {@code quantity} and {@code type}.
     */
    @JsonCreator
    public JsonAdaptedIngredient(String ingredientDetails) {
        String[] details = ingredientDetails.split(",");
        this.ingredientName = details[0].trim();
        this.quantity = Double.parseDouble(details[1].trim());
        String type = details[2].trim();
        switch (type) {
        case "vegetable":
            this.ingredientType = IngredientType.VEGETABLE;
            break;
        case "protein":
            this.ingredientType = IngredientType.PROTEIN;
            break;
        case "grain":
            this.ingredientType = IngredientType.GRAIN;
            break;
        case "other":
            this.ingredientType = IngredientType.OTHER;
            break;
        default:
            System.out.println("Error: ingredient type does not belong to one of the categories");
            this.ingredientType = null;
        }

    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        ingredientName = source.getIngredientName();
        quantity = source.getQuantity();
        ingredientType = source.getType();
    }

    @JsonValue
    public String getIngredient() {
        return ingredientName + ",  " + quantity + ", " + ingredientType.toString();
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Ingredient} object.
     * @return Ingredient object that the adapted goal was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient.
     */
    public Ingredient toModelType() throws IllegalValueException {
        if (!Ingredient.isValidIngredientName(ingredientName)) {
            throw new IllegalValueException(Ingredient.MESSAGE_CONSTRAINTS);
        }
        switch (ingredientType) {
        case GRAIN:
            return new Grain(ingredientName, quantity);
        case VEGETABLE:
            return new Vegetable(ingredientName, quantity);
        case PROTEIN:
            return new Protein(ingredientName, quantity);
        case OTHER:
            return new Other(ingredientName, quantity);
        default:
            System.out.println("Error: ingredient type not instantiated");
        }
        return null;
    }

}
