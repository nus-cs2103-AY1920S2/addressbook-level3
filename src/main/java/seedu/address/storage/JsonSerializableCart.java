package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Cart;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ingredient.Ingredient;

/**
 * An Immutable Cart that is serializable to JSON format.
 */
@JsonRootName(value = "cart")
class JsonSerializableCart {

    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Cart contains duplicate ingredient(s).";

    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCart} with the given ingredients.
     */
    @JsonCreator
    public JsonSerializableCart(@JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    /**
     * Converts a given {@code ReadOnlyCart} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCart}.
     */
    public JsonSerializableCart(ReadOnlyCart source) {
        ingredients.addAll(source.getIngredientList()
                .stream()
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this cart into the model's {@code Cart} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Cart toModelType() throws IllegalValueException {
        Cart cart = new Cart();
        for (JsonAdaptedIngredient jsonAdaptedIngredient : ingredients) {
            Ingredient ingredient = jsonAdaptedIngredient.toModelType();
            if (cart.hasIngredient(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            cart.addIngredient(ingredient);
        }
        return cart;
    }

}
