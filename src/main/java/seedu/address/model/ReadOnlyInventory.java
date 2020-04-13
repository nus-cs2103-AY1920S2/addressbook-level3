package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;

/**
 * Unmodifiable view of an inventory
 */
public interface ReadOnlyInventory {
    /**
     * Returns an unmodifiable view of the inventory.
     */
    ObservableList<Ingredient> getIngredientList();
}
