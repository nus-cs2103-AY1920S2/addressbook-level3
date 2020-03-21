package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Quantity;
import seedu.address.model.util.QuantityUtil;

/**
 * Jackson-friendly version of {@link Protein}.
 */
class JsonAdaptedProtein {

    private final String proteinName;
    private final Quantity quantity;

    /**
     * Constructs a {@code JsonAdaptedGrain} with the given {@code ingredientName},
     * {@code quantity} and {@code type}.
     */
    @JsonCreator
    public JsonAdaptedProtein(String grain) {
        String[] details = grain.split(",");
        this.proteinName = details[0].trim();
        this.quantity = QuantityUtil.parseQuantity(details[1].trim());
    }

    /**
     * Converts a given {@code Protein} into this class for Jackson use.
     */
    public JsonAdaptedProtein(Protein source) {
        proteinName = source.getIngredientName();
        quantity = source.getQuantity();
    }

    @JsonValue
    public String getProtein() {
        return proteinName + ",  " + quantity;
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Protein} object.
     * @return Protein object that the adapted goal was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Protein.
     */
    public Protein toModelType() throws IllegalValueException {
        if (!Protein.isValidIngredientName(proteinName)) {
            throw new IllegalValueException(Protein.MESSAGE_CONSTRAINTS);
        }

        return new Protein(proteinName, quantity);
    }

}
