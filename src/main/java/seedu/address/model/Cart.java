package seedu.address.model;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the cart level
 */
public class Cart extends SortedIngredientList implements ReadOnlyCart {

    public Cart() {}

    public Cart(ReadOnlyCart toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code Cart} with {@code newData}.
     * Called in constructor.
     */
    public void resetData(ReadOnlyCart newData) {
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
                || (other instanceof Cart // instanceof handles nulls
                && this.getCompatibleIngredientList().equals(((Cart) other).getCompatibleIngredientList()));
    }

}
