package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;

/**
 * Unmodifiable view of a shopping cart
 */
public interface ReadOnlyCart {
    /**
     * Returns an unmodifiable view of the inventory.
     */
    ObservableList<Ingredient> getIngredientList();
}
