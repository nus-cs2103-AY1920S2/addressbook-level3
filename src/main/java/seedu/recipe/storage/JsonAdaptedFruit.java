package seedu.recipe.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.util.QuantityUtil;

/**
 * Jackson-friendly version of {@link Fruit}.
 */
public class JsonAdaptedFruit {

    private final String fruitName;
    private final Quantity quantity;

    /**
     * Constructs a {@code JsonAdaptedFruit} with the given {@code fruit}.
     */
    @JsonCreator
    public JsonAdaptedFruit(String fruit) {
        String[] details = fruit.split(",", 2);
        this.fruitName = details[1].trim();
        this.quantity = QuantityUtil.parseQuantity(details[0].trim());
    }

    /**
     * Converts a given {@code Fruit} into this class for Jackson use.
     */
    public JsonAdaptedFruit(Fruit source) {
        fruitName = source.getIngredientName();
        quantity = source.getQuantity();
    }

    @JsonValue
    public String getFruit() {
        return quantity + ",  " + fruitName;
    }

    /**
     * Converts this Jackson-friendly adapted fruit object into the model's {@code Fruit} object.
     * @return Fruit object that the adapted fruit was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Fruit.
     */
    public Fruit toModelType() throws IllegalValueException {
        if (!Fruit.isValidIngredientName(fruitName)) {
            throw new IllegalValueException(Fruit.MESSAGE_CONSTRAINTS);
        }

        return new Fruit(fruitName, quantity);
    }

}
