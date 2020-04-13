package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A list of ingredients that enforces compatibility in updating its elements and does not allow nulls.
 * An ingredient is considered compatible with another ingredient by comparing using
 * {@code Ingredient#isCompatibleWith(Ingredient)}. As such, adding and updating of ingredients uses
 * {@code Ingredient#isSameIngredient(Ingredient)} for compatibility so as to ensure that the ingredient is being added
 * updated from an existing element if a compatible element can be found in the CompatibleIngredientList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Ingredient#isCompatibleWith(Ingredient)
 */
public class CompatibleIngredientList extends UniqueIngredientList {

    /**
     * Returns true if the list contains an ingredient compatible with the given argument.
     */
    @Override
    public boolean contains(Ingredient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isCompatibleWith);
    }

    /**
     * Returns an ingredient compatible with given argument, if the list contains a compatible ingredient.
     */
    @Override
    public Ingredient find(Ingredient toFind) {
        checkArgument(contains(toFind));
        return internalList.stream().filter(toFind::isCompatibleWith).findFirst().get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompatibleIngredientList // instanceof handles nulls
                        && internalList.equals(((CompatibleIngredientList) other).internalList));
    }
}
