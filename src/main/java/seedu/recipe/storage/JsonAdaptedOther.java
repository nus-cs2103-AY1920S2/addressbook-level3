package seedu.recipe.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.util.QuantityUtil;

/**
 * Jackson-friendly version of {@link Other}.
 */
public class JsonAdaptedOther {

    private final String otherName;
    private final Quantity quantity;

    /**
     * Constructs a {@code JsonAdaptedOther} with the given {@code other}
     */
    @JsonCreator
    public JsonAdaptedOther(String other) {
        String[] details = other.split(",", 2);
        this.otherName = details[1].trim();
        this.quantity = QuantityUtil.parseQuantity(details[0].trim());
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
        return quantity + ",  " + otherName;
    }

    /**
     * Converts this Jackson-friendly adapted other object into the model's {@code Other} object.
     * @return Other object that the adapted other was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Other.
     */
    public Other toModelType() throws IllegalValueException {
        if (!Other.isValidIngredientName(otherName)) {
            throw new IllegalValueException(Other.MESSAGE_CONSTRAINTS);
        }

        return new Other(otherName, quantity);
    }
}
