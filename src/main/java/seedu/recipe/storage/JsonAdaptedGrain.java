package seedu.recipe.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.util.QuantityUtil;

/**
 * Jackson-friendly version of {@link Grain}.
 */
public class JsonAdaptedGrain {

    private final String grainName;
    private final Quantity quantity;

    /**
     * Constructs a {@code JsonAdaptedGrain} with the given {@code grainName},
     * {@code quantity}.
     */
    @JsonCreator
    public JsonAdaptedGrain(String grain) {
        String[] details = grain.split(",", 2);
        this.grainName = details[1].trim();
        this.quantity = QuantityUtil.parseQuantity(details[0].trim());
    }

    /**
     * Converts a given {@code Grain} into this class for Jackson use.
     */
    public JsonAdaptedGrain(Grain source) {
        grainName = source.getIngredientName();
        quantity = source.getQuantity();
    }

    @JsonValue
    public String getGrain() {
        return quantity + ",  " + grainName;
    }

    /**
     * Converts this Jackson-friendly adapted grain object into the model's {@code Grain} object.
     * @return Grain object that the adapted grain was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Grain.
     */
    public Grain toModelType() throws IllegalValueException {
        if (!Grain.isValidIngredientName(grainName)) {
            throw new IllegalValueException(Grain.MESSAGE_CONSTRAINTS);
        }

        return new Grain(grainName, quantity);
    }

}
