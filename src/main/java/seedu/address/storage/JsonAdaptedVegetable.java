package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Jackson-friendly version of {@link Vegetable}.
 */
class JsonAdaptedVegetable {

    private final String vegetableName;
    private final double quantity;

    /**
     * Constructs a {@code JsonAdaptedGrain} with the given {@code ingredientName},
     * {@code quantity} and {@code type}.
     */
    @JsonCreator
    public JsonAdaptedVegetable(String grain) {
        String[] details = grain.split(",");
        this.vegetableName = details[0].trim();
        this.quantity = Double.parseDouble(details[1].trim());
    }

    /**
     * Converts a given {@code Vegetable} into this class for Jackson use.
     */
    public JsonAdaptedVegetable(Vegetable source) {
        vegetableName = source.getIngredientName();
        quantity = source.getQuantity();
    }

    @JsonValue
    public String getVegetable() {
        return vegetableName + ",  " + quantity;
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Vegetable} object.
     * @return Vegetable object that the adapted goal was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Vegetable.
     */
    public Vegetable toModelType() throws IllegalValueException {
        if (!Vegetable.isValidIngredientName(vegetableName)) {
            throw new IllegalValueException(Vegetable.MESSAGE_CONSTRAINTS);
        }

        return new Vegetable(vegetableName, quantity);
    }

}
