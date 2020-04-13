package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * Jackson-friendly version of {@link Ingredient}.
 */
class JsonAdaptedIngredient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    private final String name;
    private final String quantity;

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given {@code name} and {@code quantity}.
     */
    @JsonCreator
    public JsonAdaptedIngredient(@JsonProperty("name") String name,
            @JsonProperty("quantity") String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        name = source.getName().toString();
        quantity = source.getQuantity().toString();
    }

    /**
     * Converts this Jackson-friendly adapted ingredient object into the model's {@code Ingredient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient.
     */
    public Ingredient toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IngredientName.class.getSimpleName()));
        } else if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IngredientQuantity.class.getSimpleName()));
        } else if (!IngredientName.isValidIngredientName(name)) {
            throw new IllegalValueException(IngredientName.MESSAGE_CONSTRAINTS);
        } else if (!IngredientQuantity.isValidIngredientQuantity(quantity)) {
            throw new IllegalValueException(IngredientQuantity.MESSAGE_CONSTRAINTS);
        }

        final IngredientName modelIngredientName = new IngredientName(name);
        final IngredientQuantity modelIngredientQuantity = new IngredientQuantity(quantity);
        return new Ingredient(modelIngredientName, modelIngredientQuantity);
    }

}
