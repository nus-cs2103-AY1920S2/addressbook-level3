package seedu.recipe.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.util.QuantityUtil;

/**
 * Jackson-friendly version of {@link Protein}.
 */
public class JsonAdaptedProtein {

    private final String proteinName;
    private final Quantity quantity;

    /**
     * Constructs a {@code JsonAdaptedProtein} with the given {@code protein}
     */
    @JsonCreator
    public JsonAdaptedProtein(String protein) {
        String[] details = protein.split(",", 2);
        this.proteinName = details[1].trim();
        this.quantity = QuantityUtil.parseQuantity(details[0].trim());
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
        return quantity + ",  " + proteinName;
    }

    /**
     * Converts this Jackson-friendly adapted protein object into the model's {@code Protein} object.
     * @return Protein object that the adapted protein was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Protein.
     */
    public Protein toModelType() throws IllegalValueException {
        if (!Protein.isValidIngredientName(proteinName)) {
            throw new IllegalValueException(Protein.MESSAGE_CONSTRAINTS);
        }

        return new Protein(proteinName, quantity);
    }

}
