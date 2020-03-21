package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Quantity;
import seedu.address.model.util.QuantityUtil;

/**
 * Jackson-friendly version of {@link Fruit}.
 */
class JsonAdaptedFruit {

    private final String fruitName;
    private final Quantity quantity;

    /**
     * Constructs a {@code JsonAdaptedFruit} with the given {@code fruit}.
     */
    @JsonCreator
    public JsonAdaptedFruit(String fruit) {
        String[] details = fruit.split(",");
        this.fruitName = details[0].trim();
        this.quantity = QuantityUtil.parseQuantity(details[1].trim());
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
        return fruitName + ",  " + quantity;
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
