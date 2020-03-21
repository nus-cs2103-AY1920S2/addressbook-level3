package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Quantity;
import seedu.address.model.util.QuantityUtil;

/**
 * Jackson-friendly version of {@link Other}.
 */
class JsonAdaptedOther {

    private final String otherName;
    private final Quantity quantity;

    /**
     * Constructs a {@code JsonAdaptedGrain} with the given {@code ingredientName},
     * {@code quantity} and {@code type}.
     */
    @JsonCreator
    public JsonAdaptedOther(String grain) {
        String[] details = grain.split(",");
        this.otherName = details[0].trim();
        this.quantity = QuantityUtil.parseQuantity(details[1].trim());
    }

    /**
     * Converts a given {@code Other} into this class for Jackson use.
     */
    public JsonAdaptedOther(Other source) {
        otherName = source.getIngredientName();
        quantity = source.getQuantity();
    }

    @JsonValue
    public String getOther() {
        return otherName + ",  " + quantity;
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Other} object.
     * @return Other object that the adapted goal was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Other.
     */
    public Other toModelType() throws IllegalValueException {
        if (!Other.isValidIngredientName(otherName)) {
            throw new IllegalValueException(Other.MESSAGE_CONSTRAINTS);
        }

        return new Other(otherName, quantity);
    }
}
