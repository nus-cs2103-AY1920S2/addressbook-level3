package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private final double quantity = 0;
    private final IngredientType ingredientType;
    // todo: save quantity and type (as string?)

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given {@code ingredientName}, {@code quantity} and {@code type}.
     */
    @JsonCreator
    public JsonAdaptedIngredient(String ingredientName) {//, @JsonProperty ("quantity") Double quantity,
                                 //@JsonProperty ("type") IngredientType ingredientType) {
        this.ingredientName = ingredientName;
        //this.quantity = quantity;
        this.ingredientType = IngredientType.VEGETABLE;//ingredientType;
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        ingredientName = source.ingredientName;
        //quantity = source.getQuantity();
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
     * @return
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
