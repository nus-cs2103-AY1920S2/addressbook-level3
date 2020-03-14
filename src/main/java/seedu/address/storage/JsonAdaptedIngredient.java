package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Grain;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientType;
import seedu.address.model.ingredient.Other;
import seedu.address.model.ingredient.Protein;
import seedu.address.model.ingredient.Vegetable;

/**
 * Jackson-friendly version of {@link Ingredient}.
 */
class JsonAdaptedIngredient {

    private final String ingredientName;
    private final double quantity;
    private final IngredientType ingredientType;

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given {@code ingredientName}, {@code quantity} and {@code type}.
     */
    @JsonCreator
    public JsonAdaptedIngredient(String ingredientName, double quantity, IngredientType ingredientType) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.ingredientType = ingredientType;
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        ingredientName = source.ingredientName;
        quantity = source.getQuantity();
        ingredientType = source.ingredientType;
    }

    @JsonValue
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Goal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted goal.
     */
    public Ingredient toModelType() throws IllegalValueException {
        if (!Ingredient.isValidIngredientName(ingredientName)) {
            throw new IllegalValueException(Ingredient.MESSAGE_CONSTRAINTS);
        }
        switch (ingredientType) {
        case (GRAIN):
            return new Grain(quantity, ingredientName);
        case (VEGETABLE):
            return new Vegetable(quantity, ingredientName);
        case (PROTEIN):
            return new Protein(quantity, ingredientName);
        case (OTHER):
            return new Other(quantity, ingredientName);
        default:
            System.out.println("Error: ingredient type not instantiated");
        }
    }

}
