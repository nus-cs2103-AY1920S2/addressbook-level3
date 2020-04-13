package seedu.address.model;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the inventory level
 */
public class Inventory extends SortedIngredientList implements ReadOnlyInventory {

    public Inventory() {}

    public Inventory(ReadOnlyInventory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newData}.
     * Called in constructor.
     */
    public void resetData(ReadOnlyInventory newData) {
        requireNonNull(newData);

        setIngredients(newData.getIngredientList());
    }

    @Override
    public String toString() {
        return getIngredientList().size() + " ingredients";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && this.getCompatibleIngredientList().equals(((Inventory) other).getCompatibleIngredientList()));
    }
}
